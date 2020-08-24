var URL = 'http://ec2-34-209-240-19.us-west-2.compute.amazonaws.com/cohort/id';

function savetabs() {
    chrome.storage.sync.get('GatekeeperId', function (obj) {
        console.log('GatekeeperId', obj.GatekeeperId);
        var json = JSON.parse(obj.GatekeeperId);
        postRequest(json.sessionId);
    });
}

document.addEventListener('DOMContentLoaded', function () {
    savetabs();
});

function postRequest(sessionId) {
    if(sessionId == -1){
        console.log("Invalid sessionId");
        return;
    }
    var content = document.getElementById('content');
    console.log(" sessionId " + sessionId);
    console.log(" Posting Request to get cohort for session id  "+ sessionId );
    var xhr = new XMLHttpRequest();

    xhr.open("POST", URL, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify({"sid": sessionId}));
    xhr.onreadystatechange = function () { // Call a function when the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log("Got response 200!");
            var json = JSON.parse(xhr.responseText);
            console.log("Cohort "+ json.cohortId);
            content.innerHTML = json.cohortId;
        }
    }
}
