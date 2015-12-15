function initialiseLogger(outputElementId) {
    var outputElement = document.getElementById(outputElementId);
    console.log(outputElement);
    return  {
        "info" : function(text) {
            var para = document.createElement("p");
            var textnode = document.createTextNode("[info] " + text);
            para.appendChild(textnode);


            outputElement.appendChild(para);
        }
    }
}


function invokeApiBtn_click(log) {
    log.info("Redirecting to openam oauth");

    window.location.href="http://loan.example.com:9009/openam/oauth2/authorize?response_type=code&client_id=confidentialWebClient&client_secret=oauthclient&state=foo&redirect_uri=http%3A%2F%2Fwebsecurity.tutorial.com%2Foauth%2Fauthorization-grant.html&scope=secrets";
}

function initialiseApp() {
    var log = initialiseLogger("output");

    log.info("Demo of openam ouath request");

    document.getElementById("invokeApiBtn").addEventListener("click", function () {
        invokeApiBtn_click(log);
    });

}


window.onload = initialiseApp;