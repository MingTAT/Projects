package com.ttu.ttu.board;

/**
 * Created by wind1209 on 2016/12/5.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.ttu.ttu.R;
import com.ttu.ttu.login.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

public class NotificationService extends Service{

    protected static final String TAG = "NotificationService";

    protected static final int TIME_FOR_RENEW_REQUEST = 30;

    HttpURLConnection connection = null;

    private Service instance;

    JSONObject jsonObject;
    JSONArray JsonArray;

    private long time_request;

    String json_boardno;
    String json_title;
    String json_abstract;
    int notifyID = -1;

    Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        time_request = TIME_FOR_RENEW_REQUEST*1000;
        instance = this;
        timedelay();

        return START_STICKY;
    }

    private void timedelay(){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            // TimerTask 是个抽象类,实现的是Runable类

            @Override
            public void run() {
                acquireServerRecord();
            }

        }, 0, time_request);
    }


    private void acquireServerRecord() {

        new AsyncTask<Void, Integer, JSONArray>() {

            JSONArray jsonArray;
            @Override
            protected JSONArray doInBackground(Void... voids) {
                try{
                    String urlBuilder = "http://140.129.6.189/i4010/board/notificationboard.php";
                    urlBuilder = urlBuilder + "?androidid="+ MainActivity.BuildSERIAL;

                    Log.i(TAG, "time for reload");

                    URL url = new URL(urlBuilder);
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    connection.connect();

                    if( connection.getResponseCode() == HttpsURLConnection.HTTP_OK ){

                        InputStream inputStream = connection.getInputStream();
                        BufferedReader  bufferedReader = new BufferedReader( new InputStreamReader(inputStream) );

                        String tempStr = null;
                        StringBuffer stringBuffer = new StringBuffer();

                        while ((tempStr = bufferedReader.readLine()) != null) {
                            stringBuffer.append(tempStr);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        String responseString = stringBuffer.toString();

                        jsonArray = new JSONArray(responseString);

                        while(jsonArray == null){
                            jsonArray = new JSONArray(responseString);
                        }

                        setJsonArray(jsonArray);

                    }
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if( connection != null ) {
                        connection.disconnect();
                    }
                    connection = null;
                }
                return jsonArray;
            }

            @Override
            protected void onPostExecute(JSONArray result) {
                super.onPostExecute(result);
                setJsonArray( result );

            }
        }.execute();
    }

    private void setJsonArray(JSONArray _jsonarray){
        JsonArray = _jsonarray;

        if (JsonArray != null)
        {
            for (int i = 0; i < JsonArray.length(); i++) {
                try {
                    jsonObject = (JSONObject) JsonArray.get(i);

                    json_boardno = jsonObject.getString("boardno");
                    json_title = jsonObject.getString("title");
                    json_abstract = jsonObject.getString("abstract");
                    notifyID = Integer.parseInt(json_boardno);

                    Notification();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }


    }

    public void Notification(){
        final boolean autoCancel = true;
        final int requestCode = notifyID;

        // 通知的識別號碼

        final Intent intent = new Intent(getApplicationContext(), BoardList.class); // 開啟另一個Activity的Intent
        final int flags = PendingIntent.FLAG_UPDATE_CURRENT; // ONE_SHOT：PendingIntent只使用一次；CANCEL_CURRENT：PendingIntent執行前會先結束掉之前的；NO_CREATE：沿用先前的PendingIntent，不建立新的PendingIntent；UPDATE_CURRENT：更新先前PendingIntent所帶的額外資料，並繼續沿用
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext()); // 建立TaskStackBuilder
        stackBuilder.addParentStack(BoardList.class); // 加入目前要啟動的Activity，這個方法會將這個Activity的所有上層的Activity(Parents)都加到堆疊中
        stackBuilder.addNextIntent(intent); // 加入啟動Activity的Intent
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(requestCode, flags); // 取得PendingIntent
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
        final Notification notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(json_title).setContentText(json_abstract).setContentIntent(pendingIntent).setAutoCancel(autoCancel).build(); // 建立通知
        notificationManager.notify(notifyID, notification); // 發送通知
    }
}
