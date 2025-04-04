var exec = cordova.require("cordova/exec");

/**
 * Constructor.
 *
 * @returns {CordovaPluginSimpleHttpsRequest}
 */
function CordovaPluginSimpleHttpsRequest() {
}

// pOptions = {"method": "GET, "params": {}, "headers": {}, "data": "", "isHttpRequest": false, "isResDataBinary", false, "ignoreSSL": false}
CordovaPluginSimpleHttpsRequest.prototype.request = function (pUrl, pOptions, pCallback) {
      let tOptions = pOptions || {};
      let tMethod = (tOptions.method) ? tOptions.method.trim().toUpperCase() : "GET";
      let tParams = tOptions.params || {};
      let tHeaders = tOptions.headers || {};
      let tData = (tOptions.data && typeof tOptions.data === 'object') ? JSON.stringify(tOptions.data) : (tOptions.data || "");
      let tReqProtocol = (tOptions.isHttpRequest || false) ? "httpRequest" : "httpsRequest";
      let tIsResDataBinary = tOptions.isResDataBinary || false;
      let tIgnoreCert = tOptions.ignoreSSL || false;
      exec((pRet)=>{pCallback(JSON.parse(pRet));}, null, 'SimpleHttpsRequest', tReqProtocol, [pUrl, tMethod, tParams, tHeaders, tData, tIsResDataBinary, tIgnoreCert]);
};
CordovaPluginSimpleHttpsRequest.prototype.get = function (pUrl, pOptions, pCallback) {
      let tOptions = pOptions || {};
      tOptions.method = "GET";
      CordovaPluginSimpleHttpsRequest.request(pUrl, tOptions, pCallback);
};
CordovaPluginSimpleHttpsRequest.prototype.post = function (pUrl, pOptions, pCallback) {
      let tOptions = pOptions || {};
      tOptions.method = "POST";
      CordovaPluginSimpleHttpsRequest.request(pUrl, tOptions, pCallback);
};
CordovaPluginSimpleHttpsRequest.prototype.put = function (pUrl, pOptions, pCallback) {
      let tOptions = pOptions || {};
      tOptions.method = "PUT";
      CordovaPluginSimpleHttpsRequest.request(pUrl, tOptions, pCallback);
};
CordovaPluginSimpleHttpsRequest.prototype.delete = function (pUrl, pOptions, pCallback) {
      let tOptions = pOptions || {};
      tOptions.method = "DELETE";
      CordovaPluginSimpleHttpsRequest.request(pUrl, tOptions, pCallback);
};
CordovaPluginSimpleHttpsRequest.prototype.patch = function (pUrl, pOptions, pCallback) {
      let tOptions = pOptions || {};
      tOptions.method = "PATCH";
      CordovaPluginSimpleHttpsRequest.request(pUrl, tOptions, pCallback);
};

var CordovaPluginSimpleHttpsRequest = new CordovaPluginSimpleHttpsRequest();
module.exports = CordovaPluginSimpleHttpsRequest;
