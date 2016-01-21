function initialiseLogger(outputElementId) {
    var outputElement = document.getElementById(outputElementId);
    return {
        info: function (text) {
            var para = document.createElement("p");
            var textnode = document.createTextNode("[info] " + text);
            para.appendChild(textnode);


            outputElement.appendChild(para);
        }
    }
}


function invokeApiBtn_click(log) {

    var redirectUrl = authorizationUrlTemplate.supplant(clientAuthorisationRequest)

    log.info("Redirect to " + redirectUrl);
    window.location.href = redirectUrl;
}

function handleAccessTokenResponse(log, xhttp) {
    var responseData = JSON.parse(xhttp.responseText);

    log.info(JSON.stringify(responseData));
}

function getAccessToken(log, authorizationCode) {

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                handleAccessTokenResponse(log, xhttp);
            } else if (xhttp.status == 401) {
                log.info("Not authorised! Login first")
            } else {
                log.info("Bad http response: " + xhttp.status);
                log.info("Response: " + xhttp.responseText);
            }
        }
    }


    var userName = clientAuthorisationRequest['clientId'];
    var password = clientAuthorisationRequest['clientSecret'];


    //xhttp.withCredentials = true;
    xhttp.open("POST", accessTokenUrl, true);
    xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName + ":" + password));
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    var accessTokenRequest = {
        authorizationCode: authorizationCode,
        redirectUrl: clientAuthorisationRequest['redirectUrl'],
        clientId: clientAuthorisationRequest['clientId']
    }
    var accessTokenBody = accessTokenBodyTemplate.supplant(accessTokenRequest);

    log.info("curl --request POST --user \"" + userName + ":" + password +  "\"" +
        " --data \"" + accessTokenBody + "\" " +
        accessTokenUrl);


    try {
        xhttp.send(accessTokenBody);
    } catch (e) {
        alert(e);
    }

}

function processRedirectResponse(log) {
    var currentUrl = urlObject({'url': window.location});

    var authorisationCode = currentUrl.parameters['code'];
    if (authorisationCode) {
        log.info("Authorisaztion code: " + authorisationCode);
        getAccessToken(log, authorisationCode);
    }
}


function initialiseApp() {
    var log = initialiseLogger("output");

    log.info("Demo of openam oauth code grant flow.");

    log.info("Authorisation Request Parameters:")
    for (var key in clientAuthorisationRequest) {
        log.info(key + ": " + clientAuthorisationRequest[key]);
    }
    log.info("Authorisation URI: " + authorizationUrlTemplate.supplant(clientAuthorisationRequest));

    document.getElementById("invokeApiBtn").addEventListener("click", function () {
        invokeApiBtn_click(log);
    });

    processRedirectResponse(log);


}

/**
 * Thanks Crockford!
 * http://javascript.crockford.com/remedial.html
 */
String.prototype.supplant = function (o) {
    return this.replace(/{([^{}]*)}/g,
        function (a, b) {
            var r = o[b];
            return typeof r === 'string' || typeof r === 'number' ? r : a;
        }
    );
};

var clientAuthorisationRequest = {
    clientId: "confidentialWebClient",
    clientSecret: "oauthclient",
    state: "foo",
    redirectUrl: encodeURIComponent("http://websecurity.tutorial.com/oauth/4-1-authorization-code-grant.html"),
    scope: "secrets"
};

var authorizationUrlTemplate = "http://loan.example.com:9009/openam/oauth2/authorize" +
    "?response_type=code" +
    "&client_id={clientId}" +
    "&client_secret={clientSecret}" +
    "&state=foo={state}" +
    "&redirect_uri={redirectUrl}" +
    "&scope={scope}";

var accessTokenUrl = "http://loan.example.com:9009/openam/oauth2/access_token";
var accessTokenBodyTemplate = "grant_type=authorization_code" +
    "&code={authorizationCode}" +
    "&redirect_uri={redirectUrl}" +
    "&clientId={clientId}";

window.onload = initialiseApp;