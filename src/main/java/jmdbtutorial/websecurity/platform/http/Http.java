package jmdbtutorial.websecurity.platform.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

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
        return execute(request, new JsonResponseHandler());
    }

    public HttpResponse execute(HttpUriRequest request, ResponseHandler responseHandler) {
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
                    Map responseMap = responseHandler.processResponseBody(response, responseBody);

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


    public void destroy() throws Exception {
        http.close();
    }


}
