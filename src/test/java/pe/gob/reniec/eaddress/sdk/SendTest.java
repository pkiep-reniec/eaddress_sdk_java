package pe.gob.reniec.eaddress.sdk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pe.gob.reniec.eaddress.sdk.common.Constants;
import pe.gob.reniec.eaddress.sdk.dto.ApiResponse;
import pe.gob.reniec.eaddress.sdk.dto.Attachment;
import pe.gob.reniec.eaddress.sdk.dto.ConfigAga;
import pe.gob.reniec.eaddress.sdk.dto.Message;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

        String configFile = getClass().getClassLoader().getResource("reniec_eaddress_bk.json").getFile();
        reniecEAddressClient = new ReniecEAddressClient(configFile, oConfigAga);
    }

    @Test
    public void sendSingleTest() {
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(new Attachment("Archivo demo 1", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
        attachments.add(new Attachment("Archivo demo 2", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));
        attachments.add(new Attachment("Archivo demo 3", "https://www.gob.pe/859-plataforma-de-autenticacion-id-peru"));

        Message oMessage = new Message();
//        oMessage.setDocType(Constants.TYPE_DOC_RUC);
//        oMessage.setDoc("20100055237");
        oMessage.setDocType(Constants.TYPE_DOC_DNI);
        oMessage.setDoc("46256479");

        oMessage.setSubject("test service");
        oMessage.setMessage("<p>Hola <b>Mundo</b></p>");
        oMessage.setTag("test");

        ApiResponse result = reniecEAddressClient.sendSingleNotification(oMessage, attachments);

        System.out.println(result);

        Assert.assertEquals(true, result.getSuccess());
    }

    @Test
    public void sendMassiveTest() {
        String massiveCsv = getClass().getClassLoader().getResource("massive_pn.csv").getFile();

        Message oMessage = new Message();
        oMessage.setSubject("mensaje de prueba masiva para [[nombres]]");
        oMessage.setMessage("<p></p>" +
                "<p>[[nombres]]</p>" +
                "<p>uno [[numero_orden]]</p>" +
                "<p>dos [[numero_orden]]</p>");
        oMessage.setTag("test");

        ApiResponse result = reniecEAddressClient.sendMassiveNotification(oMessage, new File(massiveCsv));

        System.out.println(result);

        Assert.assertEquals(true, result.getSuccess());
    }
}
