package pe.gob.reniec.eaddress.sdk.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.http.client.utils.URIBuilder;
import pe.gob.reniec.eaddress.sdk.dto.ConfigAga;
import pe.gob.reniec.eaddress.sdk.dto.Message;
import pe.gob.reniec.eaddress.sdk.dto.Metadata;
import pe.gob.reniec.eaddress.sdk.dto.SearchRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author Alexander Llacho
 */
public class Utils {

    private static Utils __instance = null;
    private static final String tempDir = System.getProperty("java.io.tmpdir").concat(File.separator).concat("temp_sign");
    public final String separator = "-";

    private Utils() {
    }

    public static Utils getInstance() {
        if (__instance == null) {
            __instance = new Utils();
        }

        return __instance;
    }

    public String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public byte[] hexToBytes(String data) {
        byte[] b = new byte[data.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(data.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public String createTempDir() {
        int i = 0;
        String tmp = null;

        do {
            i++;
            tmp = mkdirTmp();
            if (tmp != null) i = 10;
        } while (i < 10);

        return tmp;
    }

    private String mkdirTmp() {
        String tmp = null;
        File file = new File(tempDir);

        if (!file.exists()) {
            if (!file.mkdir()) {
                System.out.println("Directorio temporal principal no creado: ".concat(file.getPath()));
                return null;
            }
        }

        file = new File(file.getPath(), UUID.randomUUID().toString());

        if (!file.exists()) {
            if (!file.mkdir()) {
                System.out.println("Directorio temporal no creado: ".concat(file.getPath()));
                return null;
            }
        }

        tmp = file.getPath();

        return tmp;
    }

    public void generateTempFiles(String tempDir, ConfigAga configAga, Metadata metadata) throws Exception {
        //Generate JSON
        File jsonFile = new File(tempDir, Constants.JSON_METADATA);
        Writer fileWriterJSON = new FileWriter(jsonFile);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(tempDir, Constants.JSON_METADATA), metadata);
        } finally {
            fileWriterJSON.close();
        }

        //Generate param.properties
        File paramFile = new File(tempDir, Constants.PARAM_PROPERTIES);
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put("contentFile", Constants.JSON_METADATA);
        mapParam.put("timestamp", configAga.getTimestamp());
        mapParam.put("certificateId", configAga.getCertificateId());
        mapParam.put("secretPassword", configAga.getSecretPassword());

        Writer fileWriterParam = new FileWriter(paramFile);

        try {
            FileOutputStream fos = new FileOutputStream(paramFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                bw.write(entry.getKey() + "=" + entry.getValue());
                bw.newLine();
            }

            bw.close();
        } finally {
            fileWriterParam.close();
        }
        //Generate 7z
        create7z(tempDir, jsonFile, paramFile);
    }

    private void create7z(String tempDir, File jsonFile, File paramFile) throws Exception {
        List<File> filesTo7Z = new ArrayList<>();
        filesTo7Z.add(jsonFile);
        filesTo7Z.add(paramFile);

        SevenZOutputFile sevenZOutput = null;
        sevenZOutput = new SevenZOutputFile(new File(tempDir, Constants.FILE_ZIP));
        sevenZOutput.setContentCompression(SevenZMethod.LZMA2);
        //
        for (int i = 0; i < filesTo7Z.size(); i++) {
            SevenZArchiveEntry entry = sevenZOutput.createArchiveEntry(filesTo7Z.get(i), filesTo7Z.get(i).getName());
            sevenZOutput.putArchiveEntry(entry);
            FileInputStream in = new FileInputStream(filesTo7Z.get(i));
            byte[] b = new byte[1024];
            int count = 0;
            while ((count = in.read(b)) > 0) {
                sevenZOutput.write(b, 0, count);
            }
            sevenZOutput.closeArchiveEntry();
            in.close();
        }
        sevenZOutput.close();
    }

    public boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public URIBuilder convertToUriBuilder(SearchRequest searchRequest, String uri) {
        try {
            URIBuilder builder = new URIBuilder(uri);
            builder = searchRequest.getPage() != null ? builder.setParameter("page", String.valueOf(searchRequest.getPage())) : builder;
            builder = searchRequest.getCount() != null ? builder.setParameter("count", String.valueOf(searchRequest.getCount())) : builder;
            builder = !isEmpty(searchRequest.getSort()) ? builder.setParameter("sort", String.valueOf(searchRequest.getSort())) : builder;
            builder = !isEmpty(searchRequest.getName()) ? builder.setParameter("name", String.valueOf(searchRequest.getName())) : builder;
            builder = !isEmpty(searchRequest.getDoc()) ? builder.setParameter("doc", String.valueOf(searchRequest.getDoc())) : builder;
            builder = !isEmpty(searchRequest.getSubject()) ? builder.setParameter("subject", String.valueOf(searchRequest.getSubject())) : builder;
            builder = !isEmpty(searchRequest.getTag()) ? builder.setParameter("tag", String.valueOf(searchRequest.getTag())) : builder;
            builder = searchRequest.getStatus() != null ? builder.setParameter("status", String.valueOf(searchRequest.getStatus())) : builder;
            builder = searchRequest.getDateBegin() != null ? builder.setParameter("dateBegin", String.valueOf(searchRequest.getDateBegin())) : builder;
            builder = searchRequest.getDateEnd() != null ? builder.setParameter("dateEnd", String.valueOf(searchRequest.getDateEnd())) : builder;
            builder = searchRequest.getFailed() != null ? builder.setParameter("failed", String.valueOf(searchRequest.getFailed())) : builder;

            return builder;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    public boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public String messageConcatHash(Message message) {
        String hashMessage = message.getSubject().concat(separator).concat(message.getMessage());

        if (message.getAttachments() != null) {
            hashMessage.concat(separator).concat(message.getAttachments());
        }

        return hashMessage;
    }
}
