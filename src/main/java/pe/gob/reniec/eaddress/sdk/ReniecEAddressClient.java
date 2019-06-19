package pe.gob.reniec.eaddress.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import pe.gob.reniec.eaddress.sdk.common.Constants;
import pe.gob.reniec.eaddress.sdk.common.Utils;
import pe.gob.reniec.eaddress.sdk.dto.*;
import pe.gob.reniec.eaddress.sdk.utils.ConvertResponse;
import pe.gob.reniec.eaddress.sdk.utils.MySSLConnectionSocketFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Llacho
 */
public class ReniecEAddressClient {

    private Config config;
    private ConfigAga configAga;
    private String pathDir;

    public ReniecEAddressClient(String configFile, ConfigAga oConfigAga) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.config = mapper.readValue(new File(configFile), Config.class);
        this.configAga = oConfigAga;
    }

    public ApiResponse sendSingleNotification(Message oMessage) {
        try {
            this.pathDir = Utils.CreateTempDir();

            Metadata metadata = createMetadata(oMessage, null, true);
            File fileSignMetadata = signMetadata(metadata);

            if (fileSignMetadata != null) {
                TokenResponse token = getToken();

                if (token != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(metadata);
                    oMessage.setMetadata(jsonString);

                    ApiResponse result = sendSingle(fileSignMetadata, oMessage, token.getAccessToken());

                    if (result != null) {
                        Utils.deleteDirectory(new File(pathDir));

                        return result;
                    }
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    public ApiResponse sendMassiveNotification(Message oMessage, File file) {
        ApiResponse result = null;

        try {
            this.pathDir = Utils.CreateTempDir();

            Metadata metadata = createMetadata(oMessage, file, false);
            File fileSign = signMetadata(metadata);

            if (fileSign != null) {
                //security service
                TokenResponse token = getToken();

                if (token != null) {
                    //send
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(metadata);
                    oMessage.setMetadata(jsonString);

                    result = sendMassive(fileSign, file, oMessage, token.getAccessToken());

                    if (result.getSuccess()) {
                        Utils.deleteDirectory(new File(pathDir));
                    }
                }
            }

            return result;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return result;
    }

    private Metadata createMetadata(Message message, File fileCSV, Boolean single) throws Exception {
        String line = "";
        Integer count = single ? 1 : -1;

        if (!single && fileCSV != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileCSV), StandardCharsets.UTF_8));

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.equals("")) {
                    count++;
                }
            }
        }

        if (count < 1) {
            throw new Exception("Not File CSV: ");
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
        metadata.setQuantity(count);

        String hashMessage = oPerson.getName().concat(oApp.getName()).concat(message.getSubject()).concat(String.valueOf(date.getTime())).concat(message.getMessage());
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(hashMessage.getBytes(StandardCharsets.UTF_8));
        String sha256hex = Utils.bytesToHex(hash);
        metadata.setContentHash(sha256hex);

        return metadata;
    }

    private File signMetadata(Metadata metadata) {
        File targetFile = new File(this.pathDir, Constants.FILE_SIGN);

        try {
            Utils.generateTempFiles(this.pathDir, this.configAga, metadata);

            File fileZip = new File(this.pathDir, Constants.FILE_ZIP);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("uploadFile", fileZip, ContentType.create("application/octet-stream"), fileZip.getName())
                    .build();

            HttpPost post = new HttpPost(this.configAga.getAgaUri());
            post.setEntity(entity);

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    fos.write(IOUtils.toByteArray(response.getEntity().getContent()));
                }

                return targetFile;
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    private TokenResponse getToken() {
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
        HttpPost post = new HttpPost(this.config.getSecurityUri());

        try {
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
            urlParameters.add(new BasicNameValuePair("scope", "eaddress"));
            urlParameters.add(new BasicNameValuePair("client_id", this.config.getClientId()));
            urlParameters.add(new BasicNameValuePair("client_secret", this.config.getClientSecret()));

            post.setEntity(new UrlEncodedFormEntity(urlParameters, StandardCharsets.UTF_8));

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 201) {
                Object object = ConvertResponse.getInstance().convert(response, TokenResponse.class);

                if (object != null) {
                    TokenResponse tokenResponse = (TokenResponse) object;

                    return tokenResponse;
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
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
                    .addBinaryBody("fileSign", file, ContentType.create("application/octet-stream"), "filename")
                    .build();

            HttpPost post = new HttpPost(this.config.getEaddressServiceUri().concat("/api/v1.0/send/single"));
            post.setHeader("Authorization", "Bearer ".concat(accessToken));
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            Object object = ConvertResponse.getInstance().convert(response, ApiResponse.class);

            if (object != null) {
                return (ApiResponse) object;
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    private ApiResponse sendMassive(File file, File fileCSV, Message oMessage, String accessToken) {
        ApiResponse apiResponse = null;

        try {
//            ClientConfig config = new DefaultClientConfig();
//            Client client = Client.create(config);
//            WebResource resource = client.resource(this.config.getEaddressServiceUri().concat("/api/v1.0/send/masive"));
//
//            FileDataBodyPart fileDataSign = new FileDataBodyPart("fileSign", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
//            FileDataBodyPart fileDataCSV = new FileDataBodyPart("fileCSV", fileCSV, MediaType.APPLICATION_OCTET_STREAM_TYPE);
//
//            MultiPart multiPart = new FormDataMultiPart()
//                    .field("subject", oMessage.getSubject(), MediaType.TEXT_PLAIN_TYPE)
//                    .field("message", oMessage.getMessage(), MediaType.TEXT_PLAIN_TYPE)
//                    .field("tag", oMessage.getTag(), MediaType.TEXT_PLAIN_TYPE)
//                    .field("callback", oMessage.getCallback(), MediaType.TEXT_PLAIN_TYPE)
//                    .field("metadata", oMessage.getMetadata(), MediaType.TEXT_PLAIN_TYPE)
//                    .bodyPart(fileDataSign)
//                    .bodyPart(fileDataCSV);
//
//            ClientResponse response = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE)
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessToken))
//                    .post(ClientResponse.class, multiPart);
//
//            String strResponse = response.getEntity(String.class);
//            ObjectMapper mapper = new ObjectMapper();
//            apiResponse = mapper.readValue(strResponse, ApiResponse.class);
//            client.destroy();

            return apiResponse;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return apiResponse;
    }

    public Config getConfig() {
        return config;
    }

    public ConfigAga getConfigAga() {
        return configAga;
    }


}
