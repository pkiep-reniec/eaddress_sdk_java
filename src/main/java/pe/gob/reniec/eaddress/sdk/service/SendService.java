package pe.gob.reniec.eaddress.sdk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pe.gob.reniec.eaddress.sdk.common.Constants;
import pe.gob.reniec.eaddress.sdk.common.Messages;
import pe.gob.reniec.eaddress.sdk.common.Utils;
import pe.gob.reniec.eaddress.sdk.dto.*;
import pe.gob.reniec.eaddress.sdk.utils.ConvertResponse;
import pe.gob.reniec.eaddress.sdk.utils.MySSLConnectionSocketFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class SendService {

    private static SendService __instance = null;

    private Config config;
    private ConfigAga configAga;
    private SecurityService securityService;
    private Utils utils;

    private SendService(Config config, ConfigAga configAga) {
        this.config = config;
        this.configAga = configAga;
        this.securityService = SecurityService.getInstance(config);
        this.utils = Utils.getInstance();
    }

    public static SendService getInstance(Config config, ConfigAga configAga) {
        if (__instance == null) {
            __instance = new SendService(config, configAga);
        }

        return __instance;
    }

    public ApiResponse procSingleNotification(Message oMessage) {
        try {
            String pathDir = utils.createTempDir();

            if (pathDir == null) {
                return new ApiResponse(Messages.ERROR_CREATING_TEMP_DIR);
            }

            Metadata metadata = createMetadata(oMessage, null, true);

            if (metadata == null) {
                return new ApiResponse(Messages.ERROR_SIGNING_METADATA);
            }

            File fileSignMetadata = signMetadata(metadata, pathDir);

            if (fileSignMetadata != null) {
                String token = this.securityService.getToken();

                if (token != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(metadata);
                    oMessage.setMetadata(jsonString);

                    ApiResponse result = sendSingle(fileSignMetadata, oMessage, token);

                    if (result != null) {
                        utils.deleteDirectory(new File(pathDir));

                        return result;
                    }
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return new ApiResponse(Messages.UNEXPECTED_ERROR);
    }

    public ApiResponse procMassiveNotification(Message oMessage, File file) {
        if (this.configAga == null) {
            return new ApiResponse(Messages.NO_CONFIG_AGA);
        }

        try {
            String pathDir = utils.createTempDir();

            if (pathDir == null) {
                return new ApiResponse(Messages.ERROR_CREATING_TEMP_DIR);
            }

            Metadata metadata = createMetadata(oMessage, file, false);

            if (metadata == null) {
                return new ApiResponse(Messages.ERROR_SIGNING_METADATA);
            }

            File fileSign = signMetadata(metadata, pathDir);

            if (fileSign != null) {
                String token = this.securityService.getToken();

                if (token != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(metadata);
                    oMessage.setMetadata(jsonString);

                    ApiResponse result = sendMassive(fileSign, file, oMessage, token);

                    if (result != null) {
                        utils.deleteDirectory(new File(pathDir));

                        return result;
                    }
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return new ApiResponse(Messages.UNEXPECTED_ERROR);
    }

    private ApiResponse sendSingle(File file, Message oMessage, String accessToken) {
        try {
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addTextBody("doc", oMessage.getDoc())
                    .addTextBody("docType", oMessage.getDocType())
                    .addTextBody("subject", oMessage.getSubject())
                    .addTextBody("message", oMessage.getMessage())
                    .addTextBody("rep", oMessage.getRep() == null ? "" : oMessage.getRep())
                    .addTextBody("tag", oMessage.getTag() == null ? "" : oMessage.getTag())
                    .addTextBody("callback", oMessage.getCallback() == null ? "" : oMessage.getCallback())
                    .addTextBody("attachments", oMessage.getAttachments() == null ? "" : oMessage.getAttachments())
                    .addTextBody("metadata", oMessage.getMetadata())
                    .addBinaryBody("fileSign", file, ContentType.create("application/octet-stream"), file.getName())
                    .build();

            HttpPost request = new HttpPost(this.config.getEaddressUri().concat("/api/v1.0/send/single"));
            request.setHeader("Authorization", "Bearer ".concat(accessToken));
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            return convertResponse(response);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    private ApiResponse sendMassive(File file, File fileCSV, Message oMessage, String accessToken) {
        try {
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addTextBody("subject", oMessage.getSubject())
                    .addTextBody("message", oMessage.getMessage())
                    .addTextBody("tag", oMessage.getTag() == null ? "" : oMessage.getTag())
                    .addTextBody("callback", oMessage.getCallback() == null ? "" : oMessage.getCallback())
                    .addTextBody("metadata", oMessage.getMetadata())
                    .addBinaryBody("fileCSV", fileCSV, ContentType.create("application/octet-stream"), fileCSV.getName())
                    .addBinaryBody("fileSign", file, ContentType.create("application/octet-stream"), file.getName())
                    .build();

            HttpPost request = new HttpPost(this.config.getEaddressUri().concat("/api/v1.0/send/massive"));
            request.setHeader("Authorization", "Bearer ".concat(accessToken));
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            return convertResponse(response);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    private Metadata createMetadata(Message message, File fileCsv, Boolean single) throws Exception {
        if (!single && fileCsv == null) {
            return null;
        }

        Date date = new Date();

        DataPerson oPerson = new DataPerson();
        oPerson.setDocType(this.config.getDocType());
        oPerson.setDoc(this.config.getDoc());
        oPerson.setName(this.config.getName());

        DataApp oApp = new DataApp();
        oApp.setName(this.config.getAppName());
        oApp.setClientId(this.config.getClientId());

        Metadata metadata = new Metadata();
        metadata.setIssuer(oPerson);
        metadata.setApplication(oApp);
        metadata.setSubject(message.getSubject());
        metadata.setTag(message.getTag());

        if (single) {
            String hashMessage = oPerson.getName().concat(oApp.getName()).concat(message.getSubject()).concat(String.valueOf(date.getTime())).concat(message.getMessage());
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(hashMessage.getBytes(StandardCharsets.UTF_8));
            String sha256hex = utils.bytesToHex(hash);

            metadata.setChecksum(sha256hex);
            metadata.setQuantity(1);
        } else {
            Metadata metadataChecksum = getChecksum(fileCsv);

            if (metadataChecksum == null) {
                return null;
            }

            metadata.setChecksum(metadataChecksum.getChecksum());
            metadata.setQuantity(metadataChecksum.getQuantity());
        }

        return metadata;
    }

    private Metadata getChecksum(File fileCsv) throws IOException, NoSuchAlgorithmException {
        String line = "";
        String content = "";
        Integer count = -1;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileCsv), StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                content += line.trim();

                if (!line.equals("")) {
                    count++;
                }
            }
        }

        Metadata metadata = new Metadata();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));

        metadata.setChecksum(utils.bytesToHex(hash));
        metadata.setQuantity(count);

        return metadata;
    }

    private File signMetadata(Metadata metadata, String pathDir) {
        File targetFile = new File(pathDir, Constants.FILE_SIGN);

        try {
            utils.generateTempFiles(pathDir, this.configAga, metadata);

            File fileZip = new File(pathDir, Constants.FILE_ZIP);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("uploadFile", fileZip, ContentType.create("application/octet-stream"), fileZip.getName())
                    .build();

            HttpPost request = new HttpPost(this.configAga.getAgaUri());
            request.setEntity(entity);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    fos.write(IOUtils.toByteArray(response.getEntity().getContent()));
                }

                return targetFile;
            } else {
                System.out.println(ConvertResponse.getInstance().convertToString(response));
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    private ApiResponse convertResponse(HttpResponse response) throws IOException {
        try {
            Object object = ConvertResponse.getInstance().convert(response, ApiResponse.class);

            if (!object.getClass().equals(String.class)) {
                return (ApiResponse) object;
            } else {
                System.out.println(object);
            }
        } catch (Exception ex) {
            System.out.println(ConvertResponse.getInstance().convertToString(response));
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }
}
