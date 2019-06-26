package pe.gob.reniec.eaddress.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pe.gob.reniec.eaddress.sdk.common.Constants;
import pe.gob.reniec.eaddress.sdk.dto.ApiResponse;
import pe.gob.reniec.eaddress.sdk.dto.Attachment;
import pe.gob.reniec.eaddress.sdk.dto.ConfigAga;
import pe.gob.reniec.eaddress.sdk.dto.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class SendTest {

    private ReniecEAddressClient reniecEAddressClient;

    @Before
    public void before() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(getClass().getClassLoader().getResource("application.properties").getFile()));

        ConfigAga oConfigAga = new ConfigAga();
        oConfigAga.setAgaUri(properties.getProperty("aga.uri"));
        oConfigAga.setTimestamp(properties.getProperty("aga.timestamp"));
        oConfigAga.setCertificateId(properties.getProperty("aga.certificate.id"));
        oConfigAga.setSecretPassword(properties.getProperty("aga.password"));

        String configFile = getClass().getClassLoader().getResource("reniec_eaddress.json").getFile();
        reniecEAddressClient = new ReniecEAddressClient(configFile, oConfigAga);
    }

    @Test
    public void sendSingleTest() {
        List<Attachment> attachments = new ArrayList<>();
//        attachments.add(new Attachment("Archivo demo 1", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
//        attachments.add(new Attachment("Archivo demo 2", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
//        attachments.add(new Attachment("Archivo demo 3", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));

        Message oMessage = new Message();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonAttachments = objectMapper.writeValueAsString(attachments);

            oMessage.setAttachments(jsonAttachments);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
        }

        oMessage.setDocType(Constants.TYPE_DOC_DNI);
        oMessage.setDoc("70273865");
        oMessage.setSubject("mensaje de prueba individual 2");
        oMessage.setMessage("<p>Hola <b>Mundo</b></p>");
        oMessage.setTag("tag");

        ApiResponse result = reniecEAddressClient.sendSingleNotification(oMessage);

        System.out.println(result);

        Assert.assertEquals(true, result.getSuccess());
    }

    @Test
    public void sendMassiveTest() {
        String massiveCsv = getClass().getClassLoader().getResource("massive.csv").getFile();

        Message oMessage = new Message();
        oMessage.setSubject("mensaje de prueba masiva");
        oMessage.setMessage("<p>[[ruc]]</p>" +
                "<p>[[nombres]]</p>" +
                "<p>[[numero_orden]]</p>");
        oMessage.setTag("tag");

        ApiResponse result = reniecEAddressClient.sendMassiveNotification(oMessage, new File(massiveCsv));

        System.out.println(result);

        Assert.assertEquals(true, result.getSuccess());
    }
}
