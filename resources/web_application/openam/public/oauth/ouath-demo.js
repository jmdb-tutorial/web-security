function initialiseLogger(outputElementId) {
    var outputElement = document.getElementById(outputElementId);
    console.log(outputElement);
    return  {
        info: function(text) {
            var para = document.createElement("p");
            var textnode = document.createTextNode("[info] " + text);
            para.appendChild(textnode);


            outputElement.appendChild(para);
        }
    }
}


function invokeApiBtn_click(log) {
    var clientAuthorisationRequest = {
        clientId: "confidentialWebClient",
        clientSecret: "oauthclient",
        state: "foo",
        redirectUrl: encodeURIComponent("http://websecurity.tutorial.com/oauth/authorization-grant.html"),
        scope: "secrets"
    };

    var redirectUrl = authorizationUrlTemplate.supplant(clientAuthorisationRequest)

    log.info("Redirect to " + redirectUrl);
    window.location.href=redirectUrl;
}

function initialiseApp() {
    var log = initialiseLogger("output");

    log.info("Demo of openam ouath request");

    document.getElementById("invokeApiBtn").addEventListener("click", function () {
        invokeApiBtn_click(log);
    });

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

var authorizationUrlTemplate = "http://loan.example.com:9009/openam/oauth2/authorize" +
                                "?response_type=code" +
                                "&client_id={clientId}" +
                                "&client_secret={clientSecret}" +
                                "&state=foo={state}" +
                                "&redirect_uri={redirectUrl}" +
                                "&scope={scope}";
window.onload = initialiseApp;