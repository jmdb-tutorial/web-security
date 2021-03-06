function valueFrom(id) {
    return document.getElementById(id).value
}

function handleLoginResponse(xhttp) {
    console.log("200 ok HTTP/1.0\n" + xhttp.responseText);
    var responseData = JSON.parse(xhttp.responseText);
    localStorage.setItem("tokenId", responseData['tokenId']);
    console.log("Stored token in local storage.");
}

function getCurrentToken() {
    return localStorage.getItem("tokenId");
}

function logoutBtn_click() {
    localStorage.removeItem("tokenId");
    console.log("Removed token from local storage.");
}

function loginBtn_click() {
    var username = valueFrom("username");
    var password = valueFrom("password");

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            handleLoginResponse(xhttp);
        }
    }

    console.log("HTTP/1.0 POST http://loan.example.com:9009/openam/json/authenticate");
    console.log("{}");
    xhttp.open("POST", "http://loan.example.com:9009/openam/json/authenticate", true);
    xhttp.setRequestHeader("X-OpenAM-Username", username);
    xhttp.setRequestHeader("X-OpenAM-Password", password);
    xhttp.setRequestHeader("Content-Type", "application/json");


    xhttp.send("{}");


}

function setResultText(text) {
    var resultsDiv = document.getElementById("call-results");
    resultsDiv.innerHTML = "<code>" + text + "</code>";
}
function handleSecretResponse_A(xhttp) {
    console.log("200 ok HTTP/1.0\n" + xhttp.responseText);
    var responseData = JSON.parse(xhttp.responseText);

    setResultText(JSON.stringify(responseData));
}

function getSecretABtn_click() {
    setResultText("");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                handleSecretResponse_A(xhttp);
            }  else if (xhttp.status == 401) {
                setResultText("Not authorised! Login first")
            } else {
                setResultText("Bad http response: " + xhttp.responseText);
            }
        }
    }

    console.log("HTTP/1.0 GET http://a.resource.server.com:9010/application/api/protecteda");
    console.log("Authorization: Bearer " + getCurrentToken());

    xhttp.open("GET", "http://a.resource.server.com:9010/application/api/protecteda", true);
    xhttp.setRequestHeader("Authorization", "Bearer " + getCurrentToken());

    try {
        xhttp.send();
    } catch (e) {
        alert(e);
    }
}




function initialiseApp() {
    console.log("Initialising web application javascript...");

    document.getElementById("loginBtn").addEventListener("click", function () {
        loginBtn_click();
    });

    document.getElementById("logoutBtn").addEventListener("click", function () {
        logoutBtn_click();
    });


    document.getElementById("getSecretABtn").addEventListener("click", function () {
        getSecretABtn_click();
    });
    console.log("Ok. Ready for business.");
}

window.onload = initialiseApp;