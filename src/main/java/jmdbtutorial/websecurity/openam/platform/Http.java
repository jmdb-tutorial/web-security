package jmdbtutorial.websecurity.openam.platform;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Collections.emptyMap;

/**
 * Created by jmdb on 21/08/2015.
 */
public class Http {
    private CloseableHttpClient http;

    public Http() {
    }

    public static void addBasicAuthzHeader(HttpUriRequest request, String username, String password) {
        String credentials = new String(Base64.getEncoder().encode((username + ":" + password).getBytes()));
        request.addHeader("Authorization", "Basic " + credentials);
    }


    public void init() {
        http = HttpClientBuilder.create()
                .disableContentCompression()
                .disableConnectionState()
                .setKeepAliveStrategy(null)

                .build();

    }

    public HttpResponse execute(HttpUriRequest request) {
        out.println(request);

        List<Header> headers = Arrays.asList(request.getAllHeaders());
        for (Header h : headers) {
            out.println(h);
        }
        out.println("");

        try {
            CloseableHttpResponse response = http.execute(request);

            try {
                out.println(response.getStatusLine());
                headers = Arrays.asList(response.getAllHeaders());
                for (Header h : headers) {
                    out.println(h);
                }

                HttpEntity entity = response.getEntity();


                if (entity != null) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
                    StringBuffer responseBody = new StringBuffer();
                    String inputLine;
                    try {
                        while ((inputLine = in.readLine()) != null) {
                            responseBody.append(inputLine);
                        }
                    } finally {
                        in.close();
                    }

                    out.println("\n" + responseBody + "\n");
                    Map responseMap = processResponseBody(response, responseBody);

                    return new HttpResponse(response.getStatusLine(), responseMap);
                }
            } finally {
                response.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not execute request " + request + " (see stack trace)", ex);

        }
        return null;
    }

    private Map processResponseBody(CloseableHttpResponse response, StringBuffer responseBody) throws IOException {

        if (isZeroContentLength(response)) {
            return emptyMap();
        }

        if (isJson(response)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseBody.toString(), Map.class);
        } else if (isText(response)) {
            Properties p = new Properties();
            p.load(new StringReader(responseBody.toString()));
            return new HashMap<Object, Object>(p);
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

    public void destroy() throws Exception {
        http.close();
    }


}
