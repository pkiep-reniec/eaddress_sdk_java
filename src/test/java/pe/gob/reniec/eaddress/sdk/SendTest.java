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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class SendTest {

    private ReniecEAddressClient reniecEAddressClient;

    @Before
    public void before() {
        ConfigAga oConfigAga = new ConfigAga();
        oConfigAga.setAgaUri("http://127.0.0.1:8080/refirma-aga/rest/service/sign-file");
        oConfigAga.setTimestamp("false");
        oConfigAga.setCertificateId("certdm");
        oConfigAga.setSecretPassword("NH7PERU$$$");

        String configFile = getClass().getClassLoader().getResource("reniec_eaddress.json").getFile();
        reniecEAddressClient = new ReniecEAddressClient(configFile, oConfigAga);
    }

    @Test
    public void sendSingleTest() {
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(new Attachment("Archivo demo 1", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
        attachments.add(new Attachment("Archivo demo 2", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
        attachments.add(new Attachment("Archivo demo 3", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));

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
        oMessage.setDocType(Constants.TYPE_DOC_DNI);
        oMessage.setDoc("70273865");
        oMessage.setSubject("mensaje de prueba masiva");
        oMessage.setMessage("<p>Hola <b>Mundo</b></p>");
        oMessage.setTag("tag");

        ApiResponse result = reniecEAddressClient.sendMassiveNotification(oMessage, new File(massiveCsv));

        System.out.println(result);

        Assert.assertEquals(true, result.getSuccess());
    }
}
