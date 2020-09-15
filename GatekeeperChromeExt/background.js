var SESSIONID_URL = 'http://ec2-34-209-240-19.us-west-2.compute.amazonaws.com/gatekeeper/id';

var GATEKEEPER_SYNC = 'http://ec2-34-209-240-19.us-west-2.compute.amazonaws.com/gatekeeper/sync';

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
    if(uri.includes("?")){
        uri = uri.substring(0, uri.indexOf('?'));
    }
    console.log("Posted uri" + uri);
    if(uri.length > 0) {
        var sessionObj = JSON.parse(sid);
        console.log(" Posting Request to sync the domain " + uri + " and session Id " + sessionObj.sessionId);
        var xhr = new XMLHttpRequest();
        xhr.open("POST", GATEKEEPER_SYNC, true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.send(JSON.stringify({"domain": uri, "sid": sessionObj.sessionId}));
        xhr.onreadystatechange = function () { // Call a function when the state changes.
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                console.log("Got response 200!");
            }
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

chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
    console.log('Tab Updated')
    if (changeInfo.url) {
        ValidateSessionAndPost(changeInfo.url);
    }

    if (changeInfo.status === 'loading') {
        console.log('starting content script injection')
        injectContentScript(tab);
    }
});

chrome.tabs.onCreated.addListener(function (tabId) {

    chrome.tabs.getSelected(null, function (tab) {
        //get current tab without any selectors
        console.log(" Tab created with url "+ tab.url);
        ValidateSessionAndPost(tab.url);
    });

});

chrome.tabs.onActivated.addListener(function(info) {
    var tab = chrome.tabs.get(info.tabId, function(tab) {
        //get current tab without any selectors
        console.log(" Tab activated with url "+ tab.url);
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

function injectContentScript(tab) {
    console.log('Injecting script');
    chrome.storage.sync.get(['Cohort'], function(result) {
        const cohortData = JSON.parse(result.Cohort);
        console.log(cohortData);
        chrome.tabs.executeScript(tab.id, {
            runAt: 'document_start',
            code: `
                ;(function() {
                    function inject() {
                        console.log('${cohortData.cohortId}');
                        const script = document.createElement('script')
                        script.text="window.rp_visitor={cohort:'${cohortData.cohortId}'};";
                        document.head.appendChild(script);
                        console.log(window.rp_visitor);
                    }
                    inject();
                })();
            `,
        });
    })


}

