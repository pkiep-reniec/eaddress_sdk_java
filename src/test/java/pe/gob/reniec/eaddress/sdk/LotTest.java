package pe.gob.reniec.eaddress.sdk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pe.gob.reniec.eaddress.sdk.dto.*;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class LotTest {

    private String tempDir = System.getProperty("java.io.tmpdir");
    private ReniecEAddressClient reniecEAddressClient;
    private String lotId;

    @Before
    public void before() {
        String configFile = getClass().getClassLoader().getResource("reniec_eaddress.json").getFile();
        reniecEAddressClient = new ReniecEAddressClient(configFile);

//        lotId = "";
        lotId = "5d09b6a3c7e35c49dc526d6b";
    }

    @Test
    public void fetchAllLotsTest() {
        SearchRequest searchRequest = new SearchRequest();
        PaginatorLot paginatorLot = reniecEAddressClient.fetchAllLots(searchRequest);

        System.out.println(paginatorLot.getRecordsTotal());

        for (LotData lotData : paginatorLot.getData()) {
            System.out.println(lotData.toString());
        }

        Assert.assertTrue(paginatorLot.getRecordsTotal() > 0);
    }

    @Test
    public void fetchLotNotificationsTest() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setPage(1);
        searchRequest.setCount(20);
        PaginatorLotNotifications paginatorLotNotifications = reniecEAddressClient.fetchLotNotifications(lotId, searchRequest);

        System.out.println(paginatorLotNotifications.getRecordsTotal());

        for (NotificationResponse notificationResponse : paginatorLotNotifications.getNotifications()) {
            System.out.println(notificationResponse.toString());
        }

        Assert.assertTrue(paginatorLotNotifications.getRecordsTotal() > 0);
    }

    @Test
    public void downloadMetadataTest() throws IOException {
        byte[] metadata = reniecEAddressClient.downloadMetadata(lotId);

        if (metadata != null) {
            try (FileOutputStream fos = new FileOutputStream(tempDir.concat("metadata.zip"))) {
                fos.write(metadata);
            }
        }

        Assert.assertNotNull(metadata);
    }
}