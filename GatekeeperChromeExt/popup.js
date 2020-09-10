var URL = 'http://ec2-34-209-240-19.us-west-2.compute.amazonaws.com/cohort/id';
var UPDATE_INTEREST_URL= 'http://ec2-34-209-240-19.us-west-2.compute.amazonaws.com/gatekeeper/interests';

function getCohortId() {
    chrome.storage.sync.get('GatekeeperId', function (obj) {
        console.log('GatekeeperId', obj.GatekeeperId);
        var json = JSON.parse(obj.GatekeeperId);
        postRequest(json.sessionId);
    });
}

document.addEventListener('DOMContentLoaded', function () {
    getCohortId();
    documentEvents();
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


function updateSelectedInterest() {
    var selected = new Array();
  var tblInterests = document.getElementById("tblInterest");
    var chks = tblInterests.getElementsByTagName("INPUT");
    for (var i = 0; i < chks.length; i++) {
        if (chks[i].checked) {
            selected.push(chks[i].value);
        }
    }
    if (selected.length > 0) {
        updateInterest(selected);
    }
}



function documentEvents() {
    document.getElementById('updateInterest_btn').addEventListener('click',
        function() { updateSelectedInterest();
        });

}

function updateInterest(interests) {
    chrome.storage.sync.get('GatekeeperId', function (obj) {
        console.log('GatekeeperId', obj.GatekeeperId);
        var json = JSON.parse(obj.GatekeeperId);
        postInterestRequest(json.sessionId, interests);
    });
}

function postInterestRequest(sessionId, interests) {
    if(sessionId == -1){
        console.log("Invalid sessionId");
        return;
    }
    var content = document.getElementById('content');``
    console.log(" sessionId " + sessionId);
    console.log(" Posting Request to get cohort for session id  "+ sessionId );
    var xhr = new XMLHttpRequest();

    xhr.open("POST", UPDATE_INTEREST_URL, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify({"sid": sessionId, "interests": interests}));
    xhr.onreadystatechange = function () { // Call a function when the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log("Got response 200!");
            var json = JSON.parse(xhr.responseText);
            console.log("Cohort "+ json.cohortId);
            content.innerHTML = json.cohortId;
        }
    }
}
