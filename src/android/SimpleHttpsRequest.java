package com.shineum.net;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.net.ssl.HttpsURLConnection;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;


public class SimpleHttpsRequest extends CordovaPlugin {
    // private static final String RES_TEMPLATE = "{\"status\": %d, \"data\": \"%s\", \"error\": \"%s\"}";

    private void callbackMsg(CallbackContext callbackContext, String msg) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callbackContext != null) {
                    if (msg != null) {
                        callbackContext.success(msg);
                    }
                }
            }
        });
    }

    private String parseResponseStr(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }

    private String parseResponsebinary(InputStream is) {
        try {
            byte[] readBuffer = new byte[4096];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int bytesRead = is.read(readBuffer);
            while (bytesRead != -1) {
                baos.write(readBuffer, 0, bytesRead);
                bytesRead = is.read(readBuffer);
            }

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
        }
        return null;
    }

    private String updateUrl(String pUrl, Map<String, String> pQueryStrings) {
        StringBuilder sb = new StringBuilder();
        sb.append(pUrl);
        try {
            if (pQueryStrings != null) {
                sb.append("?");
                for (Map.Entry<String, String> entry : pQueryStrings.entrySet()) {
                    sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    sb.append("=");
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }

    public String httpsRequest(String pUrl, String pMethod, Map<String, String> pQueryStrings, Map<String, String> pHeaders, String pBody, boolean isResBinary, boolean isIgnoreCert) {
        JSONObject ret = new JSONObject();
        int resCode = 500;
        JSONObject resHeaders = new JSONObject();
        String resData = "";
        String resError = "";

        HttpsURLConnection urlConnection = null;

        if (isIgnoreCert) {
            HttpsTrustManager.allowAllSSL();
        }
        try {
            URL url = new URL(updateUrl(pUrl, pQueryStrings));
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod(pMethod);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            if (pHeaders != null) {
                for (Map.Entry<String, String> entry : pHeaders.entrySet()) {
                    urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (pBody != null && pBody.length() > 0) {
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                OutputStream os = urlConnection.getOutputStream();
                os.write(pBody.getBytes("UTF-8"));
                os.flush();
            } else {
                urlConnection.setDoOutput(false);
                urlConnection.connect();
            }

            resCode = urlConnection.getResponseCode();
            Map<String, List<String>> map = urlConnection.getHeaderFields();
            for(Map.Entry<String, List<String>> entry: map.entrySet()) {
                if (entry.getKey() != null) {
                    if (entry.getValue().size() > 1) {
                        resHeaders.put(entry.getKey(), entry.getValue());
                    } else {
                        resHeaders.put(entry.getKey(), entry.getValue().get(0));
                    }
                }
            }
            try {
                if (isResBinary) {
                    resData = parseResponsebinary(urlConnection.getInputStream());
                } else {
                    resData = parseResponseStr(urlConnection.getInputStream());
                }
            } catch (Exception ee) {
            } finally {
            }
            try {
                resError = parseResponseStr(urlConnection.getErrorStream());
            } catch (Exception ee) {
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            resError = e.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        try {
            ret.put("status", resCode);
            ret.put("headers", resHeaders);
            ret.put("data", resData);
            ret.put("error", resError);
        } catch (Exception e) {
        }

        // return String.format(RES_TEMPLATE, responseCode, "{}", "");
        return ret.toString();
    }

    public String httpRequest(String pUrl, String pMethod, Map<String, String> pQueryStrings, Map<String, String> pHeaders, String pBody, boolean isResBinary, boolean isIgnoreCert) {
        JSONObject ret = new JSONObject();
        int resCode = 500;
        JSONObject resHeaders = new JSONObject();
        String resData = "";
        String resError = "";

        HttpURLConnection urlConnection = null;

        if (isIgnoreCert) {
            HttpsTrustManager.allowAllSSL();
        }
        try {
            URL url = new URL(updateUrl(pUrl, pQueryStrings));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(pMethod);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            if (pHeaders != null) {
                for (Map.Entry<String, String> entry : pHeaders.entrySet()) {
                    urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (pBody != null && pBody.length() > 0) {
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                OutputStream os = urlConnection.getOutputStream();
                os.write(pBody.getBytes("UTF-8"));
                os.flush();
            } else {
                urlConnection.setDoOutput(false);
                urlConnection.connect();
            }

            resCode = urlConnection.getResponseCode();
            Map<String, List<String>> map = urlConnection.getHeaderFields();
            for(Map.Entry<String, List<String>> entry: map.entrySet()) {
                if (entry.getKey() != null) {
                    if (entry.getValue().size() > 1) {
                        resHeaders.put(entry.getKey(), entry.getValue());
                    } else {
                        resHeaders.put(entry.getKey(), entry.getValue().get(0));
                    }
                }
            }
            try {
                if (isResBinary) {
                    resData = parseResponsebinary(urlConnection.getInputStream());
                } else {
                    resData = parseResponseStr(urlConnection.getInputStream());
                }
            } catch (Exception ee) {
            } finally {
            }
            try {
                resError = parseResponseStr(urlConnection.getErrorStream());
            } catch (Exception ee) {
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            resError = e.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        try {
            ret.put("status", resCode);
            ret.put("headers", resHeaders);
            ret.put("data", resData);
            ret.put("error", resError);
        } catch (Exception e) {
        }

        // return String.format(RES_TEMPLATE, responseCode, "{}", "");
        return ret.toString();
    }

    public Map<String, String> parseParamObject(JSONArray args, int idx) {
        Map<String, String> ret = new HashMap<>();
        try {
            JSONObject jo = args.getJSONObject(idx);
            Iterator<String> keys = jo.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = jo.getString(key);
                ret.put(key, value);
            }
        } catch (Exception e) {
        }
        return ret;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("httpsRequest".equals(action)) {
            callbackMsg(callbackContext, httpsRequest(args.optString(0), args.optString(1), parseParamObject(args, 2), parseParamObject(args, 3), args.optString(4), args.optBoolean(5), args.optBoolean(6)));
            return true;
        }
        else if ("httpRequest".equals(action)) {
            callbackMsg(callbackContext, httpRequest(args.optString(0), args.optString(1), parseParamObject(args, 2), parseParamObject(args, 3), args.optString(4), args.optBoolean(5), args.optBoolean(6)));
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    // @Override
    // public void onPause(boolean multitasking) {
	// 	super.onPause(multitasking);
    // }

    // @Override
    // public void onResume(boolean multitasking) {
	// 	super.onResume(multitasking);
    // }

    // @Override
    // public void onStart() {
    // }

    // @Override
    // public void onStop() {
    // }
}