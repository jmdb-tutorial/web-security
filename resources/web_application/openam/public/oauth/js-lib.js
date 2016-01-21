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


