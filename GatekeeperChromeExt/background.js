var initialized = false;
var URL = 'http://sea-skocheri-mb.local/gatekeeper/id';
var GATEKEEPER_SYNC = 'http://sea-skocheri-mb.local/gatekeeper/sync';
var sid = "-1";

const interval = setInterval(function() {
    console.log("The session id is reset ");
    storeId();
}, 86400000);

function storeId() {
    makeIdRequest(function (data) {
        chrome.storage.sync.set({"GatekeeperId": data}, function () {
            sid = data;
            console.log(" Session id: "+ data);
        });
    });
    initialized = true;
}

function makeIdRequest(callback) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', URL);
    xhr.addEventListener('load', function (e) {
        var result = xhr.responseText;
        callback(result);
    });
    xhr.send();
}

function postRequest(uri) {
    if(!isValidURL(uri)){
        console.log("Invalid uri" + uri);
        return;
    }
    console.log(" uri " + uri);
    var domain = extractHostname(uri);
    console.log(" Posting Request to sync the domain "+ domain +" and session Id "+ sid);
    var xhr = new XMLHttpRequest();
    var sessionObj = JSON.parse(sid);
    xhr.open("POST", GATEKEEPER_SYNC, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify({"domain": domain, "sid": sessionObj.sessionId}));
    xhr.onreadystatechange = function () { // Call a function when the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log("Got response 200!");
        }
    }
}

chrome.tabs.onUpdated.addListener(function
        (tabId, changeInfo, tab) {
    console.log(" Event on tab onUpdated ");
        if (!initialized) {
            storeId();
        }
        if (changeInfo.url) {
            postRequest(changeInfo.url);
        }
    }
);

chrome.tabs.onSelectionChanged.addListener(function (tabId, props) {
    console.log(" Event on tab Selected ");
    if (!initialized) {
        storeId();
    }
});


chrome.tabs.onCreated.addListener(function (tabId) {
    console.log(" Event on tab created ");
    if (!initialized) {
        storeId();
    }

    chrome.tabs.getSelected(null, function (tab) {
        //get current tab without any selectors
        console.log(" Tab created with url "+ tab.url);
        postRequest(tab.url)
    });
});

function isValidURL(string) {
    var res = string.match(/(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/g);
    return (res !== null)
};

function getDomain(uri) {
    var domain = uri.match(/^[\w-]+:\/{2,}\[?([\w\.:-]+)\]?(?::[0-9]*)?/)[1]; //get tab value 'url'
    alert(domain);

    return domain;
}

function extractHostname(url) {
    var hostname;
    //find & remove protocol (http, ftp, etc.) and get hostname

    if (url.indexOf("//") > -1) {
        hostname = url.split('/')[2];
    }
    else {
        hostname = url.split('/')[0];
    }

    //find & remove port number
    hostname = hostname.split(':')[0];
    //find & remove "?"
    hostname = hostname.split('?')[0];

    return hostname;
}
