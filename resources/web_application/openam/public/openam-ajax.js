
function setResultText(text) {
    var resultsDiv = document.getElementById("call-results");
    resultsDiv.innerHTML = "<code>" + text + "</code>";
}

function handleSecretResponse_A(xhttp) {
    console.log("200 ok HTTP/1.0\n" + xhttp.responseText);
    var responseData = xhttp.responseText;

    console.log(responseData);
    setResultText(responseData);
}

function openAmAjaxBtn_click() {
    setResultText("");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                handleSecretResponse_A(xhttp);
            }  else if (xhttp.status == 401) {
                setResultText("Not authorised! Login first")
            } else {
                setResultText("Bad http response: " + xhttp.status);
            }
        }
    }

    console.log("HTTP/1.0 GET http://websecurity.tutorial.com/user-details.php");

    xhttp.open("GET", "http://websecurity.tutorial.com/user-details.php", true);

    try {
        xhttp.send();
    } catch (e) {
        alert(e);
    }


}

function initialiseApp() {
    console.log("Demo of openam ajax request");

    document.getElementById("openAmAjaxBtn").addEventListener("click", function () {
        openAmAjaxBtn_click();
    });

}


window.onload = initialiseApp;