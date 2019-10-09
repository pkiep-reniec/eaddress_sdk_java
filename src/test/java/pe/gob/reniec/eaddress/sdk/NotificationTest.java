package pe.gob.reniec.eaddress.sdk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pe.gob.reniec.eaddress.sdk.common.Acuse;
import pe.gob.reniec.eaddress.sdk.dto.ApiNotificationResponse;
import pe.gob.reniec.eaddress.sdk.dto.ApiPaginatorLotNotifications;
import pe.gob.reniec.eaddress.sdk.dto.NotificationsResponse;
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

        notificationId = "5d8d2a39c89b471284d34e36";
        lotId = "5d8d2a36c89b4711655603e5";
    }

    @Test
    public void fetchAllNotificationsTest() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setPage(1);
        searchRequest.setCount(5);
//        searchRequest.setName("miguel");
        ApiPaginatorLotNotifications notifications = reniecEAddressClient.fetchAllNotifications(searchRequest);

        System.out.println(notifications.getRecordsTotal());

        for (NotificationsResponse notification : notifications.getNotifications()) {
            System.out.println(notification.toString());
        }

        Assert.assertTrue(notifications.getRecordsTotal() > 0);
    }

    @Test
    public void getNotificationTest() {
        ApiNotificationResponse notification = reniecEAddressClient.getNotification(notificationId, lotId);
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
