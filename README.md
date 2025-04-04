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
const tCallback = (res) => { alert(res.status + "\n" + res.data); };
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

### 3.1 method (optional)
GET, POST, PUT, DELETE, PATCH
<br>
default value is GET

### 3.2 params (optional)
This will be converted into QueryString

For usage case, url will be rendered as
<br>
https://services.odata.org/V3/OData/OData.svc/?$format=json

### 3.3 headers (optional)
Request headers

### 3.4 data (optional)
request body

### 3.5 isHttpRequest (optional)
boolean (true or false)
<br>
When URL scheme is "http", use this option
<br>
default value is false

### 3.6 ignoreSSL (optional)
boolean (true or false)
<br>
When it is required to ignore SSL cretificate validation, use this option
<br>
Note. if this option is used, connection is unsecure.
<br>
default value is false

### 3.7 isResDataBinary (optional)
boolean (true or false)
<br>
When it is needed to get response as binary data, use this option
<br>
Response data will be delivered as base64 encoded format


# history

### 0.0.3
- Update options

### 0.0.2
- Update callback

### 0.0.1
- Initial Release


# License

MIT