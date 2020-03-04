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

        lotId = "5e5e79a0c89b470437b5ad21";
    }

    @Test
    public void fetchAllLotsTest() {
        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.setPage(1);
//        searchRequest.setCount(20);
//        searchRequest.setDateBegin(1580515200L);
//        searchRequest.setDateEnd(1583020799L);

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
//        searchRequest.setPage(1);
//        searchRequest.setCount(20);
//        searchRequest.setStatus(null);
//        searchRequest.setDateBegin(1580515200L);
//        searchRequest.setDateEnd(1583020799L);

        ApiPaginatorLotNotifications paginatorLotNotifications = reniecEAddressClient.fetchLotNotifications(lotId, searchRequest);

        System.out.println(paginatorLotNotifications.getRecordsTotal());

        for (NotificationsResponse notificationResponse : paginatorLotNotifications.getData()) {
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