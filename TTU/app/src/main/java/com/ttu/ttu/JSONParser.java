package com.ttu.ttu;

/**
 * Created by wind1209 on 2016/12/5.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JSONParser {

    HttpURLConnection connection = null;
    JSONObject jsonObject = null;
    static String json = "";

    // constructor
    public JSONParser() {
    }
    public JSONObject getJSONFromUrl(String urlBuilder) {

        try {
            URL url = new URL(urlBuilder);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            int statusCode = connection.getResponseCode();
            String w = Integer.toString(statusCode);
            Log.e("Json", w);

            if( statusCode == HttpsURLConnection.HTTP_OK ) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();

                String tmp = "{\"android\":" + sb.toString() + "}";
                json = tmp;
                Log.e("JSON Parser", "getJSONFromUrl " + json.toString());
            }

            // https redirect
            if (statusCode == HttpURLConnection.HTTP_MOVED_TEMP || statusCode == HttpURLConnection.HTTP_MOVED_PERM || statusCode == HttpURLConnection.HTTP_SEE_OTHER){
                String newUrl = connection.getHeaderField("Location");
                String cookies = connection.getHeaderField("Set-Cookie");
                connection = (HttpURLConnection) new URL(newUrl).openConnection();
                connection.setRequestProperty("Cookie", cookies);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
                String tmp = "{\"android\":" + sb.toString() + "}";
                json = tmp;
                Log.e("JSON Parser", "getJSONFromUrl " + json.toString());
            }

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "getJSONFromUrl Error parsing data " + e.toString());
        }
        return jsonObject;
    }

}
