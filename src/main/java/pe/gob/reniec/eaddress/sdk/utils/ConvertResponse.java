package pe.gob.reniec.eaddress.sdk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;

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

    public String convertToString(HttpResponse response) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String line = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        }

        return stringBuffer.toString();
    }

    public Object convert(HttpResponse response, Class valueType) throws IOException {
        String dataString = this.convertToString(response);

        try {
            if (dataString != null && !dataString.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(dataString, valueType);
            }
        } catch (Exception ex) {
            return dataString;
        }

        return null;
    }
}
