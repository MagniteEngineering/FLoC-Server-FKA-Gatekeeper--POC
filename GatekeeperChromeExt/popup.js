var URL = 'http://sea-skocheri-mb.local/cohort/id';

function savetabs() {
    var content = document.getElementById('content');
    makeRequest(function (data) {
        content.innerHTML = data;
        chrome.storage.sync.set({"CohortId": data}, function () {
            console.log("Setting cohort id");
        });
    });


}

document.addEventListener('DOMContentLoaded', function () {
    savetabs();
});

function makeRequest(callback) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', URL);
    xhr.addEventListener('load', function (e) {
        var result = xhr.responseText;
        callback(result);
    });
    xhr.send();
}
