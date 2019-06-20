package pe.gob.reniec.eaddress.sdk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pe.gob.reniec.eaddress.sdk.common.Acuse;
import pe.gob.reniec.eaddress.sdk.dto.NotificationResponse;
import pe.gob.reniec.eaddress.sdk.dto.PaginatorLotNotifications;
import pe.gob.reniec.eaddress.sdk.dto.SearchRequest;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class NotificationTest {

    private String tempDir = System.getProperty("java.io.tmpdir");
    private ReniecEAddressClient reniecEAddressClient;
    private String notificationId;
    private String lotId;

    @Before
    public void before() {
        String configFile = getClass().getClassLoader().getResource("reniec_eaddress.json").getFile();
        reniecEAddressClient = new ReniecEAddressClient(configFile);

//        notificationId = "";
//        lotId = "";
        notificationId = "5d09b726c7e35c1150664cc1";
        lotId = "5d09b6a3c7e35c49dc526d6b";
    }

    @Test
    public void fetchAllNotificationsTest() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setPage(1);
        searchRequest.setCount(5);
        searchRequest.setName("miguel");
        PaginatorLotNotifications notifications = reniecEAddressClient.fetchAllNotifications(searchRequest);

        System.out.println(notifications.getRecordsTotal());

        for (NotificationResponse notification : notifications.getNotifications()) {
            System.out.println(notification.toString());
        }

        Assert.assertTrue(notifications.getRecordsTotal() > 0);
    }

    @Test
    public void getNotificationTest() {
        NotificationResponse notification = reniecEAddressClient.getNotification(notificationId, lotId);
        System.out.println(notification.toString());

        Assert.assertNotNull(notification);
    }

    @Test
    public void downloadAcuseTest() throws IOException {
        byte[] metadata = reniecEAddressClient.downloadAcuse(notificationId, lotId, Acuse.RECEIVED);

        if (metadata != null) {
            try (FileOutputStream fos = new FileOutputStream(tempDir.concat("acuse.zip"))) {
                fos.write(metadata);
            }
        }

        Assert.assertNotNull(metadata);
    }
}
