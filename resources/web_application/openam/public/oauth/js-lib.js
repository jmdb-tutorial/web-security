
function p(text) {
    var para = document.createElement("p");
    var textnode = document.createTextNode(text);
    para.appendChild(textnode);
    return para;
}

function pre(text) {

    var pre = document.createElement("pre");
    var textnode = document.createTextNode(text);
    pre.appendChild(textnode);

    return pre;

}

function logObjectAsJson(outputElement, obj) {
    var response = "{\n";

    var keys = Object.keys(obj);
    keys.forEach(function (key, index) {
        var valueText = obj[key];
        if (typeof(valueText) === "string") {
            valueText = "\"" + valueText + "\"";
        }
        response = response + "  \"" + key +   "\": " + valueText;

        if (index < keys.length-1) {
            response = response + ",\n";
        }
    });

    response = response +"\n}";

    outputElement.appendChild(pre(response));
}

function initialiseLogger(outputElementId) {
    if (!outputElementId) {
        return {
            info: function (text) {
                console.log(text);
            }
        }
    }
    var outputElement = document.getElementById(outputElementId);
    return {
        info: function (text) {
            if (text.length === 0) {
                outputElement.appendChild(document.createElement("br"));
            } else if (typeof(text) === "object") {
                logObjectAsJson(outputElement, text);
            } else {
                var para = document.createElement("p");
                var textnode = document.createTextNode(text);
                para.appendChild(textnode);
                outputElement.appendChild(para);
            }


        }
    }
}

function getAccessToken() {
    return localStorage.getItem("oauth-access-token");
}

function setAccessToken(token) {
    localStorage.setItem("oauth-access-token", token);
}

function attachClickHandler(id, fn, log) {
    var element = document.getElementById(id);

    if (element === null) {
        throw "Could not locate element with id '" + id + "' in document!";
    }
    element.addEventListener("click", function () {
        fn(log);
    });
}

function getTextBoxValue(id) {
    var element = document.getElementById(id);
    return element.value;
}

var initialisers = [];

function registerInitialiser(fn) {
    console.log("Registering initialiser: " + fn.name + ", " + typeof(fn));
    initialisers.push(fn);
}


function formatUrlParameters(urlParameters) {
    if (urlParameters.length === 0) {
        return "";
    }

    var result = "?";

    var keys = Object.keys(urlParameters);

    keys.forEach(function (key, index) {
        result = result + key + "=" + urlParameters[key];
        if (index < (keys.length - 1)) {
            result = result + "&";
        }
    });

    return result;
}

function populateRequestHeaders(ajax, requestHeaders) {

    Object.keys(requestHeaders).forEach(function (key, index) {
        ajax.setRequestHeader(key, requestHeaders[key]);
    });

}



function logRequestHeaders(log, requestHeaders) {
    Object.keys(requestHeaders).forEach(function (key, index) {
        log.info(key + ": " + requestHeaders[key]);
    });

}

function logResponse(log, ajax) {
    log.info("");
    log.info("HTTP/1.1 " + ajax.status + " " + ajax.statusText);
    log.info(ajax.getAllResponseHeaders());

    if (ajax.getResponseHeader("Content-Type").startsWith("application/json")) {
        var data = JSON.parse(ajax.responseText);
        log.info(data);
    } else {
        log.info(ajax.responseText);
    }

    log.info("");
}

function mergeProperties(a, b) {
    var result = {};
    Object.keys(a).forEach(function (key, index) {
        result[key] = a[key];
    });
    Object.keys(b).forEach(function (key, index) {
        result[key] = b[key];
    });
    return result;
}

var http = {};
http.GET = {};


http.GET.json = function (log, url, urlParameters) {
    var xhttp = new XMLHttpRequest();
    var responseData = {};

    var urlWithParams = url + formatUrlParameters(urlParameters);

    log.info("");
    log.info("GET " + urlWithParams + " HTTP/1.1");

    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4) {
            logResponse(log, xhttp);
            if (xhttp.status == 200) {
                responseData = JSON.parse(xhttp.responseText);
            } else if (xhttp.status == 401) {
                log.info("Not authorised! Login first");
            } else {
                log.info("Bad http response: " + xhttp.status);
                log.info("Response: " + xhttp.responseText);
            }
        }
    }


    xhttp.open("GET", urlWithParams, true);

    var requestHeaders = {};

    requestHeaders = mergeProperties(requestHeaders, {Accept: "application/json"});

    populateRequestHeaders(xhttp, requestHeaders);

    logRequestHeaders(log, requestHeaders);


    try {
        xhttp.send();
    } catch (e) {
        alert(e);
    }


}


function initialise() {

    for (i in initialisers) {
        var fn = initialisers[i];
        console.log("Invoking: " + fn.name);
        fn();
    }
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


