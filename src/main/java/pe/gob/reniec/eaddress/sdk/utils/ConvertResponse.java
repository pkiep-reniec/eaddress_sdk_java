package pe.gob.reniec.eaddress.sdk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Miguel Pazo (http://miguelpazo.com)
 */
public class ConvertResponse {

    private static ConvertResponse __instance = null;

    private ConvertResponse() {
    }

    public static ConvertResponse getInstance() {
        if (__instance == null) {
            __instance = new ConvertResponse();
        }

        return __instance;
    }

    public Object convert(HttpResponse response, Class valueType) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer tokenEndpointBuffer = new StringBuffer();
        String line = "";

        while ((line = reader.readLine()) != null) {
            tokenEndpointBuffer.append(line);
        }

        reader.close();

        String tokenEndpoint = tokenEndpointBuffer.toString();

        if (tokenEndpoint != null && !tokenEndpoint.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(tokenEndpoint, valueType);
        } else {
            return null;
        }
    }
}
