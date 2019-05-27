package pe.gob.reniec.eaddress.sdk.webservices;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import pe.gob.reniec.eaddress.sdk.dto.ConfigAga;
import pe.gob.reniec.eaddress.sdk.dto.TokenResponse;
import pe.gob.reniec.eaddress.sdk.utils.ConvertResponse;
import pe.gob.reniec.eaddress.sdk.utils.MySSLConnectionSocketFactory;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Llacho
 */
public class SignWebService {

    public Boolean signFile(File file7z, ConfigAga configAga) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("...");

        // This attaches the file to the POST:
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();
        HttpPost post = new HttpPost(configAga.getAgaUri());
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try {
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("uploadFile", "client_credentials"));

            post.setEntity(new UrlEncodedFormEntity(urlParameters, StandardCharsets.UTF_8));

            HttpResponse response = client.execute(post);
            Object object = ConvertResponse.getInstance().convert(response, TokenResponse.class);

            if (object != null) {
                String res = (String) object;

                return true;
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

}
