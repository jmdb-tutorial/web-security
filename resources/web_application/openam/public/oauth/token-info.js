function getTokenInfo(log) {
    log.info("Going to get the token and user info");
}


function connectHandlers() {
    var log = initialiseLogger("output");

    log.info("token-info initialising.");

    attachClickHandler("getTokenInfoBtn", getTokenInfo, log);




}


registerInitialiser(connectHandlers);
