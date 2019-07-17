package pe.gob.reniec.eaddress.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import pe.gob.reniec.eaddress.sdk.dto.ApiResponse;
import pe.gob.reniec.eaddress.sdk.dto.Config;
import pe.gob.reniec.eaddress.sdk.dto.TokenResponse;
import pe.gob.reniec.eaddress.sdk.utils.ConvertResponse;
import pe.gob.reniec.eaddress.sdk.utils.MySSLConnectionSocketFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class SecurityService {

    private static SecurityService __instance = null;

    private File tempFile;
    private Config config;

    private SecurityService(Config config) {
        this.config = config;
        this.tempFile = new File(System.getProperty("java.io.tmpdir").concat(File.separator).concat("reniec_eaddress_token.txt"));
    }

    public static SecurityService getInstance(Config config) {
        if (__instance == null) {
            __instance = new SecurityService(config);
        }

        return __instance;
    }

    public String getToken() {
        String token = readToken();

        if (token != null) {
            if (validateToken(token)) {
                return token;
            }
        }

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

                if (!object.getClass().equals(String.class)) {
                    TokenResponse tokenResponse = (TokenResponse) object;
                    saveToken(tokenResponse.getAccessToken());

                    return tokenResponse.getAccessToken();
                } else {
                    System.out.println(object);
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

    private void saveToken(String token) {
        try {
            try (PrintStream out = new PrintStream(new FileOutputStream(tempFile))) {
                out.print(token);
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }
    }

    private String readToken() {
        try {
            if (!tempFile.exists()) {
                return null;
            }

            byte[] encoded = Files.readAllBytes(Paths.get(tempFile.getPath()));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    private Boolean validateToken(String token) {
        String[] parts = token.split("\\.");

        try {
            if (parts.length == 3) {
                String decoded = new String(Base64.getDecoder().decode(parts[1].getBytes()), StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(decoded);

                if (json.has("exp")) {
                    Long exp = json.get("exp").asLong() * 1000;
                    Long current = new Date().getTime();

                    return exp > current;
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return false;
    }
}
