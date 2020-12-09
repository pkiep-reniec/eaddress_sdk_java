package pe.gob.reniec.eaddress.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import pe.gob.reniec.eaddress.sdk.common.Utils;
import pe.gob.reniec.eaddress.sdk.dto.Attachment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexander Llacho
 */
public class ChecksumTest {

    //test para generar el checksum (hash) para envío de notificación individual
    private static final String separator = "-";

    @Test
    public void generateHashSingleTest() throws Exception {
        String subject = "testing";
        String message = "Hola Mundo";
        List<Attachment> lstAttachment = new ArrayList<>();
        lstAttachment.add(new Attachment("Archivo demo 1", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));

        String hashMessage = subject.concat(separator).concat(message);

        if (lstAttachment.size() > 0) {
            for (Attachment attachment : lstAttachment) {
                String attach = attachment.getName().concat("|").concat(attachment.getUrl());
                hashMessage = hashMessage.concat(separator).concat(attach);
            }
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(hashMessage.getBytes(StandardCharsets.UTF_8));
        String sha256hex = Utils.getInstance().bytesToHex(hash);

        System.out.println(sha256hex);

        Assert.assertNotNull(sha256hex);
    }

    //test para generar el checksum (hash) para envío de notificación masiva
    @Test
    public void generateHashMassiveTest() throws Exception {
        String subject = "testing";
        String message = "Hola Mundo";
        String massiveCsv = Objects.requireNonNull(getClass().getClassLoader().getResource("massive_pn.csv")).getFile();

        String line = "";
        String content = "";
        int count = -1;

        content = subject.concat(separator).concat(message).concat(separator);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(massiveCsv)), StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                content += line.trim();

                if (!line.equals("")) {
                    count++;
                }
            }
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
        String sha256hex = Utils.getInstance().bytesToHex(hash);

        System.out.println(sha256hex);

        Assert.assertNotNull(sha256hex);
    }

    //test para convertir en (string) la lista de adjuntos
    @Test
    public void addAttachmentTest() throws Exception {
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(new Attachment("Archivo demo 1", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
        attachments.add(new Attachment("Archivo demo 2", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
        attachments.add(new Attachment("Archivo demo 3", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAttachments = objectMapper.writeValueAsString(attachments);

        System.out.println(jsonAttachments);

        Assert.assertNotNull(jsonAttachments);
    }
}
