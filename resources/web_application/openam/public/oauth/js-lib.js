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

function getTextBoxValue(id) {
    var element = document.getElementById(id);
    return element.value;
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


