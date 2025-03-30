# cordova-plugin-simple-https-request
Cordova Simple Https Request

# Install

```javascript

cordova plugin add cordova-plugin-simple-https-request

```


# 1. Usage

```javascript

const tUrl = "https://services.odata.org/V3/OData/OData.svc/";
const tOptions = {
    "method": "GET",
    "params": {"$format": "json"},
    "headers": {"Content-Type": "application/json"},
    "data": "",
    "isHttpRequest": false,
    "ignoreSSL": false,
    "isResDataBinary": false
};
const tCallback = (ret) => { console.log(ret); };
cordova.plugins.simple.https.request.request(tUrl, tOptions, tCallback);

```


# 2. Response

```
{
    "status": "<response code: 200, 404 ..>",
    "headers": {}, 
    "data": "<response data>", 
    "error": "<error message>"
}
```


# 3. Options

### 3.1 method
GET, POST, PUT, DELETE, PATCH
<br>
default value is GET

### 3.2 params
This will be converted into QueryString

For usage, url will be updated as 
https://services.odata.org/V3/OData/OData.svc/?$format=json

### 3.3 headers
Request headers

### 3.4 data (optional)
request body

### 3.5 isHttpRequest (optional)
boolean (true or false)
When URL scheme is "http", use this option
default value is false

### 3.6 ignoreSSL (optional)
boolean (true or false)
When it is required to ignore SSL cretificate validation, use this option
If this option is used, connection is unsecure.
default value is false

### 3.7 isResDataBinary (optional)
boolean (true or false)
When it is needed to get response as binary data, use this option
Response data will be delivered as base64 encoded format
