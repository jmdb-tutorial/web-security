

function handleAccessTokenResponse(log, xhttp) {
    var responseData = JSON.parse(xhttp.responseText);

    log.info(JSON.stringify(responseData));

    setAccessToken(responseData['access_token']);
}


function invokeApiBtn_click(log) {
    log.info("Going to POST to the url...");

    var loginDetails = {
        username: getTextBoxValue("usernameTxt"),
        password: getTextBoxValue("passwordTxt")
    }

    log.info("details: " + loginDetails);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                handleAccessTokenResponse(log, xhttp);
            } else if (xhttp.status == 401) {
                log.info("Invalid client credentials")
            } else {
                log.info("Ba" +
                    "d http response: " + xhttp.status);
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
        grant_type: "password",
        username: loginDetails.username,
        password: loginDetails.password,
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

    log.info("Demo of openam oauth flow 4.3 - Resource owner credentials grant.");

    log.info("Authorisation URI: " + authorizationUrlTemplate);

    document.getElementById("invokeApiBtn").addEventListener("click", function () {
        invokeApiBtn_click(log);
    });


}

var clientAuthorisationRequest = {
    clientId: "confidentialWebClient",
    clientSecret: "oauthclient",
    scope: "cn%20mail%20uid%20roles%20"
};

var authorizationUrlTemplate = "http://loan.example.com:9009/openam/oauth2/token";

var authorizationBodyTemplate = "grant_type=password" +
    "&username={username}" +
    "&password={password}" +
    "&scope={scope}";

var accessTokenUrl = "http://loan.example.com:9009/openam/oauth2/access_token";

registerInitialiser(initialiseApp);
window.onload = initialise;