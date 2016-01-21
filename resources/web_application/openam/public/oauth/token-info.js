function getTokenInfo(log) {
    log.info("Going to get the token and user info");

    var accessToken = getAccessToken();

    if (!accessToken) {
        log.info("No access token set, authorise client first");
    }

    http.GET.json(log, tokenInfoUrl, {access_token: accessToken})
}


function connectHandlers() {
    var log = initialiseLogger("output");

    log.info("token-info initialising.");

    attachClickHandler("getTokenInfoBtn", getTokenInfo, log);


}


registerInitialiser(connectHandlers);

var tokenInfoUrl = "http://loan.example.com:9009/openam/oauth2/tokeninfo";