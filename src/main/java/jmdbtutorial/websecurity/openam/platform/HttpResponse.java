package jmdbtutorial.websecurity.openam.platform;

import org.apache.http.StatusLine;

import java.util.Map;

public class HttpResponse {

    private final StatusLine responseStatusLine;
    private final Map responseData;

    public HttpResponse(StatusLine responseStatusLine, Map responseData) {
        this.responseData = responseData;
        this.responseStatusLine = responseStatusLine;
    }

    public <T> T responseValue(String key) {
        return (T)responseData.get(key);
    }

    public int statusCode() {
        return responseStatusLine.getStatusCode();
    }

    public String statusText() {return responseStatusLine.getReasonPhrase(); }

    public String stringValue(String key) {
        return responseValue(key);
    }

    public boolean is(int statusCode) {
        return statusCode == statusCode();
    }

    public boolean booleanValue(String key) {
        return responseValue(key);
    }
}
