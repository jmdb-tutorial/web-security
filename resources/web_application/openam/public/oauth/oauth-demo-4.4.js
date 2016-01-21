
function handleAccessTokenResponse(log, xhttp) {
    var responseData = JSON.parse(xhttp.responseText);

    log.info(JSON.stringify(responseData));

    setAccessToken(responseData['access_token']);
}


function invokeApiBtn_click(log) {
    log.info("Going to POST to the url...");

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                handleAccessTokenResponse(log, xhttp);
            } else if (xhttp.status == 401) {
                log.info("Invalid client credentials")
            } else {
                log.info("Bad http response: " + xhttp.status);
                log.info("Response: " + xhttp.responseText);
            }
        }
    }

    var clientUserName = clientAuthorisationRequest['clientId'];
    var clientSecret = clientAuthorisationRequest['clientSecret'];

    xhttp.open("POST", accessTokenUrl, true);
    xhttp.setRequestHeader("Authorization", "Basic " + btoa(clientUserName + ":" + clientSecret));
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    var accessTokenRequest = {
        grant_type: "client_credentials",
        scope: clientAuthorisationRequest.scope
    }
    var accessTokenBody = authorizationBodyTemplate.supplant(accessTokenRequest);

    log.info("curl --request POST --user \"" + clientUserName + ":" + clientSecret +  "\"" +
        " --data \"" + accessTokenBody + "\" " +
        accessTokenUrl);

    try {
        xhttp.send(accessTokenBody);
    } catch (e) {
        alert(e);
    }

}


function initialiseApp() {
    var log = initialiseLogger("output");

    log.info("Demo of openam oauth flow 4.4 Client credentials grant.");

    log.info("Authorisation URI: " + authorizationUrlTemplate);

    document.getElementById("invokeApiBtn").addEventListener("click", function () {
        invokeApiBtn_click(log);
    });


}

var clientAuthorisationRequest = {
    clientId: "confidentialWebClient",
    clientSecret: "oauthclient",
    scope: "secrets%20cn%20mail"
};

var authorizationUrlTemplate = "http://loan.example.com:9009/openam/oauth2/token";

var authorizationBodyTemplate = "grant_type={grant_type}" +
    "&scope={scope}";

var accessTokenUrl = "http://loan.example.com:9009/openam/oauth2/access_token";

registerInitialiser(initialiseApp);
window.onload = initialise;