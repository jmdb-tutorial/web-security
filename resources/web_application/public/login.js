function valueFrom(id) {
    return document.getElementById(id).value
}




function loginBtn_click() {
    var username = valueFrom("username");
    var password = valueFrom("password");

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log("Got a 200 response - " + xhttp.responseText);
        }
    }

    xhttp.open("POST", "http://auth.websecurity.tutorial.com:9096/api/authenticate", true);
    xhttp.setRequestHeader("X-Auth-Username", username);
    xhttp.setRequestHeader("X-Auth-Password", password);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.send("{}");

    localStorage.setItem("token", "foo");
}



function initialiseApp() {
    console.log("Initialising web application javascript...");

    document.getElementById("loginBtn").addEventListener("click", function () {
        loginBtn_click();
    });

    console.log("Ok. Ready for business.");
}

window.onload = initialiseApp;