package jmdbtutorial.websecurity.platform.openam;

import jmdbtutorial.websecurity.platform.auth.User;
import jmdbtutorial.websecurity.platform.http.Http;
import jmdbtutorial.websecurity.platform.http.HttpResponse;
import jmdbtutorial.websecurity.platform.http.UserDetailsResponseHandler;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;

import java.util.Arrays;
import java.util.HashSet;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * See https://backstage.forgerock.com/#!/docs/openam/12.0.0/dev-guide
 */
public class OpenAmClient {


    private final String rootUrl = "http://loan.example.com:9009";
    private final Http http;

    public OpenAmClient(Http http) {
        this.http = http;
        this.http.init();
    }


    public User validateToken(String tokenId) {
        HttpPost request = postRequest(tokenId, url("/openam/identity/attributes?subjectid=%s", tokenId));

        HttpResponse response = http.execute(request, new UserDetailsResponseHandler());

        if (SC_OK != response.statusCode()) {
            return null;
        }

        String[] roles = response.stringArrayValue("roles");

        return new User(response.stringArrayValue("uid")[0], new HashSet<>(asList(roles)));
    }


    private String url(String path, Object... params) {
        return format("%s/%s", rootUrl, format(path, params));
    }

    private static HttpPost postRequest(String tokenId, String url) {
        HttpPost request = new HttpPost(url);
        request.addHeader("iPlanetDirectoryPro".toLowerCase(), tokenId);
        request.addHeader("Content-Type", "application/json");
        return request;
    }


}
