package jmdbtutorial.websecurity.platform.http;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.emptyMap;

public class JsonResponseHandler implements ResponseHandler {

    public Map processResponseBody(CloseableHttpResponse response, StringBuffer responseBody) throws IOException {

        if (isZeroContentLength(response)) {
            return emptyMap();
        }

        if (isJson(response)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseBody.toString(), Map.class);
        }

        throw new RuntimeException(format("Could not understand response body [%s]", responseBody));
    }


    private boolean isZeroContentLength(CloseableHttpResponse response) {
        Header contentLengthHeader = response.getFirstHeader("Content-Length");
        if (contentLengthHeader == null) {
            return false;
        }
        return 0 == Integer.parseInt(contentLengthHeader.getValue());

    }

    private boolean isJson(CloseableHttpResponse response) {
        return isContentType(response, "application/json");
    }


    private boolean isText(CloseableHttpResponse response) {
        return isContentType(response, "text/plain");
    }

    private boolean isContentType(CloseableHttpResponse response, String contentType) {
        return response.getFirstHeader("Content-Type").getValue().contains(contentType);
    }

}
