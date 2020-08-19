# Gatekeeprid-endpoint and Chrome Extension

The Gatekeeper proposal can be found in https://github.com/MagniteEngineering/Gatekeeper

## Setting up Gatekeeprid-endpoint

### Build and run gatekeeper-endpoint locally

```
cd gatekeeper-endpoint
mvn clean install
./start.sh
```

## Setting up Gatekeeprid ChromeExt

### Update background.js 

```
cd GatekeeperChromeExt
edit background.js to local gatekeeper endpoint 
var URL = 'http://<local>/gatekeeper/id';
var GATEKEEPER_SYNC = 'http://<local>/gatekeeper/sync';

Example:
var URL = 'http://sea-skocheri-mb.local/gatekeeper/id';
var GATEKEEPER_SYNC = 'http://sea-skocheri-mb.local/gatekeeper/sync';
```

### Setting up extension in Browser
1. Open the Extension Management page by navigating to chrome://extensions.
	The Extension Management page can also be opened by clicking on the Chrome menu, hovering over More Tools then selecting Extensions.
2. Enable Developer Mode by clicking the toggle switch next to Developer mode.
3. Click the LOAD UNPACKED button and select the extension directory.
4. Additionally, the Chrome DevTools panel can be opened for the background script by selecting the blue link next to Inspect views. 

