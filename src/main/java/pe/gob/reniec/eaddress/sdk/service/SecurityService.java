package pe.gob.reniec.eaddress.sdk.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import pe.gob.reniec.eaddress.sdk.dto.Config;
import pe.gob.reniec.eaddress.sdk.dto.TokenResponse;
import pe.gob.reniec.eaddress.sdk.utils.ConvertResponse;
import pe.gob.reniec.eaddress.sdk.utils.MySSLConnectionSocketFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class SecurityService {

    private static SecurityService __instance = null;

    private Config config;

    private SecurityService(Config config) {
        this.config = config;
    }

    public static SecurityService getInstance(Config config) {
        if (__instance == null) {
            __instance = new SecurityService(config);
        }

        return __instance;
    }

    public TokenResponse getToken() {
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
        HttpPost request = new HttpPost(this.config.getSecurityUri());

        try {
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
            urlParameters.add(new BasicNameValuePair("scope", "eaddress"));
            urlParameters.add(new BasicNameValuePair("client_id", this.config.getClientId()));
            urlParameters.add(new BasicNameValuePair("client_secret", this.config.getClientSecret()));

            request.setEntity(new UrlEncodedFormEntity(urlParameters, StandardCharsets.UTF_8));

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 201) {
                Object object = ConvertResponse.getInstance().convert(response, TokenResponse.class);

                if (object != null) {
                    TokenResponse tokenResponse = (TokenResponse) object;

                    return tokenResponse;
                }
            } else {
                System.out.println(ConvertResponse.getInstance().convertToString(response));
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }
}
