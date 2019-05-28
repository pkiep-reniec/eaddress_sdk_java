package pe.gob.reniec.eaddress.sdk.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import pe.gob.reniec.eaddress.sdk.dto.ConfigAga;
import pe.gob.reniec.eaddress.sdk.dto.Metadata;

import java.io.*;
import java.util.*;

/**
 * @author Alexander Llacho
 */
public class Utils {

    private static final String tempDir = System.getProperty("java.io.tmpdir").concat("\\temp_sign");

    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String CreateTempDir() throws Exception {
        int i = 0;
        String tmp = null;
        do {
            i++;
            tmp = mkdirTmp();
            if (tmp != null) i = 10;
        } while (i < 10);
        return tmp;
    }

    private static String mkdirTmp() throws Exception {
        String tmp = null;
        File file = new File(tempDir);
        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new Exception("Directorio temporal principal no creado: " + file.getPath());

            }
        }
        file = new File(file.getPath(), UUID.randomUUID().toString());
        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new Exception("Directorio temporal no creado: " + file.getPath());
            }
        }
        tmp = file.getPath();
        return tmp;
    }

    public static void generateTempFiles(String tempDir, ConfigAga configAga, Metadata metadata) throws Exception {
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

    private static void create7z(String tempDir, File jsonFile, File paramFile) throws Exception {
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

    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

}
