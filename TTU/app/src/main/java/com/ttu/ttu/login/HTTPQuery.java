package com.ttu.ttu.login;

/**
 * Created by wind1209 on 2016/12/5.
 */

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HTTPQuery extends Thread {
    public static final int REGISTATION_THREAD  = 1;
    public static final int GET_KEY_THREAD = 2;
    public static final int APP_LOGIN_THREAD = 3;
    public static final int ERROR_OCCURENCE = 99;

    private String USER_AGENT = "TTU";
    private int getType;
    private String urlBuilder;
    private Handler handler;

    HttpURLConnection connection = null;

    public HTTPQuery(String urlBuilder, int getType, Handler handler) {
        super();
        this.urlBuilder = urlBuilder;
        this.getType = getType;
        this.handler = handler;

    }

    @Override
    public void run() {
        try {

            URL url;

            switch(getType) {

                case GET_KEY_THREAD:
                    url = new URL(urlBuilder);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", USER_AGENT);
                    connection.setRequestMethod("GET");

                    if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK ) {

                        InputStream inputStream;
                        inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream) );

                        String tempStr;
                        StringBuffer stringBuffer = new StringBuffer();

                        while ((tempStr = bufferedReader.readLine()) != null) {
                            stringBuffer.append(tempStr);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        String responseString = stringBuffer.toString();

                        JSONObject jsonObject = new JSONObject(responseString);
                        Message msg = new Message();
                        msg.what = GET_KEY_THREAD;
                        msg.obj = jsonObject;
                        handler.sendMessage(msg);
                    }
                    break;
                case APP_LOGIN_THREAD:
                    url = new URL(urlBuilder);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    connection.setRequestProperty("User-Agent", USER_AGENT);

                    if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK ){
                        InputStream inputStream;
                        inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream) );

                        String tempStr;
                        StringBuffer stringBuffer = new StringBuffer();

                        while ((tempStr = bufferedReader.readLine()) != null) {
                            stringBuffer.append(tempStr);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        String responseString = stringBuffer.toString();

                        JSONObject jsonObject = new JSONObject(responseString);
                        Message msg = new Message();
                        msg.what = APP_LOGIN_THREAD;
                        msg.obj = jsonObject;
                        handler.sendMessage(msg);
                    }
                    break;
                case REGISTATION_THREAD:
                    url = new URL(urlBuilder);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", USER_AGENT);
                    connection.setRequestMethod("GET");

                    if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK ) {

                        InputStream inputStream;
                        inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream) );

                        String tempStr;
                        StringBuffer stringBuffer = new StringBuffer();

                        while ((tempStr = bufferedReader.readLine()) != null) {
                            stringBuffer.append(tempStr);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        String responseString = stringBuffer.toString();

                        JSONObject jsonObject = new JSONObject(responseString);
                        Message msg = new Message();
                        msg.what = REGISTATION_THREAD;
                        msg.obj = jsonObject;
                        handler.sendMessage(msg);
                    }
                    break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = ERROR_OCCURENCE;
            handler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = ERROR_OCCURENCE;
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = ERROR_OCCURENCE;
            handler.sendMessage(msg);
        }finally {
            if( connection != null ) {
                connection.disconnect();
            }
        }
    }
}