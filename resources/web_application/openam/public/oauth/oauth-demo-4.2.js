function invokeApiBtn_click(log) {

    var redirectUrl = authorizationUrlTemplate.supplant(clientAuthorisationRequest)

    log.info("Redirect to " + redirectUrl);
    window.location.href = redirectUrl;
}


function getParameters(location) {
    if (typeof location === 'undefined') {
        location = window.location;
    }
    var hashParams = new (function Params() {})();
    if (location.hash.length === 0) {
        return hashParams;
    };
    var hashArray = location.hash.substring(1).split('&');
    for (var i in hashArray) {
        var keyValPair = hashArray[i].split('=');
        hashParams[keyValPair[0]] = keyValPair[1];
    }
    return hashParams;
}

function processRedirectResponse(log) {
    log.info("Redirected back to: " + window.location);
    log.info("Hash fragment: " + window.location.hash);
    var hashParameters = getParameters(window.location);

    var accessToken = hashParameters['access_token'];
    if (accessToken) {
        log.info("Access token: " + accessToken);
        log.info("Token type: " + hashParameters['token_type']);
        log.info("Expires in: " + hashParameters['expires_in']);

        setAccessToken(hashParameters['access_token']);
    }
}


function initialiseApp() {
    var log = initialiseLogger("output");

    log.info("Demo of openam oauth flow 4.2 - Implicit Grant.");

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
    redirectUrl: encodeURIComponent("http://websecurity.tutorial.com/oauth/4-2-implicit-grant.html"),
    scope: "secrets%20cn%20mail%20uid%20roles"
};

var authorizationUrlTemplate = "http://loan.example.com:9009/openam/oauth2/authorize" +
    "?response_type=token" +
    "&client_id={clientId}" +
    "&client_secret={clientSecret}" +
    "&state=foo={state}" +
    "&redirect_uri={redirectUrl}" +
    "&scope={scope}";

registerInitialiser(initialiseApp);
window.onload = initialise;