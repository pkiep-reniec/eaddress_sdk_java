package pe.gob.reniec.eaddress.sdk.service;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pe.gob.reniec.eaddress.sdk.common.Acuse;
import pe.gob.reniec.eaddress.sdk.common.Utils;
import pe.gob.reniec.eaddress.sdk.dto.Config;
import pe.gob.reniec.eaddress.sdk.dto.NotificationResponse;
import pe.gob.reniec.eaddress.sdk.dto.PaginatorLotNotifications;
import pe.gob.reniec.eaddress.sdk.dto.SearchRequest;
import pe.gob.reniec.eaddress.sdk.utils.ConvertResponse;
import pe.gob.reniec.eaddress.sdk.utils.MySSLConnectionSocketFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class NotificationService {

    private static NotificationService __instance = null;

    private Config config;
    private SecurityService securityService;
    private Utils utils;

    private NotificationService(Config config) {
        this.config = config;
        this.securityService = SecurityService.getInstance(config);
        this.utils = Utils.getInstance();
    }

    public static NotificationService getInstance(Config config) {
        if (__instance == null) {
            __instance = new NotificationService(config);
        }

        return __instance;
    }

    public PaginatorLotNotifications fetchAll(SearchRequest searchRequest) {
        try {
            String token = this.securityService.getToken();

            if (token != null) {
                CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
                URIBuilder uriBuilder = utils.convertToUriBuilder(searchRequest, this.config.getEaddressServiceUri().concat("/api/v1.0/notification"));

                if (uriBuilder == null) {
                    return null;
                }

                HttpGet request = new HttpGet(uriBuilder.build());
                request.setHeader("Authorization", "Bearer ".concat(token));

                HttpResponse response = client.execute(request);
                Object object = ConvertResponse.getInstance().convert(response, PaginatorLotNotifications.class);

                if (!object.getClass().equals(String.class)) {
                    return (PaginatorLotNotifications) object;
                } else {
                    System.out.println(object);
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    public NotificationResponse getOne(String id, String lotId) {
        try {
            String token = this.securityService.getToken();

            if (token != null) {
                CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
                HttpGet request = new HttpGet(this.config.getEaddressServiceUri().concat("/api/v1.0/notification/").concat(id).concat("/").concat(lotId));
                request.setHeader("Authorization", "Bearer ".concat(token));

                HttpResponse response = client.execute(request);
                Object object = ConvertResponse.getInstance().convert(response, NotificationResponse.class);

                if (!object.getClass().equals(String.class)) {
                    return (NotificationResponse) object;
                } else {
                    System.out.println(object);
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    public byte[] downloadAcuse(String id, String lotId, Acuse acuse) {
        try {
            String token = this.securityService.getToken();

            if (token != null) {
                CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
                HttpGet request = new HttpGet(this.config.getEaddressServiceUri().concat("/api/v1.0/notification/").concat(id).concat("/")
                        .concat(lotId).concat("/").concat(acuse.getAlias()));
                request.setHeader("Authorization", "Bearer ".concat(token));

                HttpResponse response = client.execute(request);

                if (response.getStatusLine().getStatusCode() == 200) {
                    return IOUtils.toByteArray(response.getEntity().getContent());
                } else {
                    System.out.println(ConvertResponse.getInstance().convertToString(response));
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }
}
