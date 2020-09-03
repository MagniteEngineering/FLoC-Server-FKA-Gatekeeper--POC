var SESSIONID_URL ='http://ec2-34-209-240-19.us-west-2.compute.amazonaws.com/gatekeeper/id';
//'http://sea-skocheri-mb.local/gatekeeper/id';
var GATEKEEPER_SYNC = 'http://ec2-34-209-240-19.us-west-2.compute.amazonaws.com/gatekeeper/sync';
//'http://sea-skocheri-mb.local/gatekeeper/sync';

const interval = setInterval(function() {
    console.log("The session id is reset ");
    storeId();
}, 18000000);

function storeId() {
    makeIdRequest(function (data) {
        console.log(" Setting session id");
        chrome.storage.sync.set({"GatekeeperId": data}, function () {
            console.log(" Session id: "+ data);
        });
    });

}

function makeIdRequest(callback) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', SESSIONID_URL);
    xhr.addEventListener('load', function (e) {
        var result = xhr.responseText;
        callback(result);
    });
    xhr.send();
}

function postRequest(uri, sid) {

    console.log(" uri " + uri);
    //var domain = extractHostname(uri);
    var  domain = uri.substring(0, uri.indexOf('?'));
    var sessionObj = JSON.parse(sid);
    console.log(" Posting Request to sync the domain "+ domain +" and session Id "+ sessionObj.sessionId);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", GATEKEEPER_SYNC, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify({"domain": domain, "sid": sessionObj.sessionId}));
    xhr.onreadystatechange = function () { // Call a function when the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log("Got response 200!");
        }
    }
}

function ValidateSessionAndPost(uri){
    if(!isValidURL(uri)){
        console.log("Invalid uri" + uri);
        return;
    }
    chrome.storage.sync.get('GatekeeperId', function (obj) {
        console.log('GatekeeperId', obj.GatekeeperId);
        if(obj.GatekeeperId == undefined){
            setSessionAndPost(uri)
        }else {
            postRequest(uri,obj.GatekeeperId);
        }


    });
}

function setSessionAndPost(uri) {
    makeIdRequest(function (data) {
        console.log(" Setting session id");
        chrome.storage.sync.set({"GatekeeperId": data}, function () {
            console.log(" Session id: "+ data);
            postRequest(uri, data);
        });
    });

}

chrome.tabs.onUpdated.addListener(function
        (tabId, changeInfo, tab) {
        if (changeInfo.url) {
            ValidateSessionAndPost(changeInfo.url);
        }
    }
);

/*chrome.tabs.onSelectionChanged.addListener(function (tabId, props) {
    console.log(" Event on tab Selected and is initialized "+ initialized);
    if (initialized == false) {
        storeId();
    }
});*/


chrome.tabs.onCreated.addListener(function (tabId) {
    chrome.tabs.getSelected(null, function (tab) {
        //get current tab without any selectors
        console.log(" Tab created with url "+ tab.url);
        ValidateSessionAndPost(tab.url);
    });
});

function isValidURL(string) {
    var res = string.match(/(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/g);
    return (res !== null)
};

function getDomain(uri) {
    var domain = uri.match(/^[\w-]+:\/{2,}\[?([\w\.:-]+)\]?(?::[0-9]*)?/)[1]; //get tab value 'url'
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
