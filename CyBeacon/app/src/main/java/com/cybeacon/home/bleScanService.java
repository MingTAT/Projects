package com.cybeacon.home;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.Context;
import android.support.annotation.Nullable;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cybeacon.home.AlarmReceiver;

import com.cybeacon.R;
import com.cybeacon.base.BaseMVPActivity;
import com.cybeacon.home.presenter.HomePresenter;
import com.cybeacon.home.view.HomeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * Created by Sam on 3/23/2017.
 */
public class bleScanService extends Service {

    public boolean ServiceRunning;
    public  Context con;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 3000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    int RequestCode;
    int UUIDindex;
    public ArrayList<String> listUUID;
    public ArrayList<String> listCourseNames;
    public String CourseName;
    private String strCyBeaconUUID_Course1 = "7B44B47B-52A1-5381-90C2-F09B6838C5D4-1";


    private EditText className;
    private EditText classNum;
    private EditText beaconID;
    private String Output_link;
    private Button buttonGet;
    private TextView textViewResult;

    private ProgressDialog loading;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // Code to execute when the service is first created
        ServiceRunning = false;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        if(!ServiceRunning) {
            ServiceRunning = true;

            con = getApplicationContext();

            Log.i("bleScanService", "Getting bluetooth service -sam");
            mHandler = new Handler();
            final BluetoothManager bluetoothManager =
                    (BluetoothManager) con.getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();

            mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
            settings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                    .build();
            filters = new ArrayList<ScanFilter>();

            Log.i("bleScanService", "Scanning for devices -sam");
            listUUID = new ArrayList<String>();
            listCourseNames = new ArrayList<String>();
            scanLeDevice(true);

        }
        return 0;
    }

    //
    //Bluetooth related functions
    //
    public void scanLeDevice(final boolean enable) {
        if (enable) {
            //UUID_list.clear();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLEScanner.stopScan(mScanCallback);
                    Log.i("bleScanService","Searching database using devices found");
                    UUIDindex = 0;
                    ServiceRunning = false;
                    Intent intent = new Intent();
                    intent.setAction("ACTION_SCAN_UPDATE");
                    intent.putExtra("UUIDlist", listUUID);
                    sendBroadcast(intent);
                    stopSelf();
                    //SearchDataBaseforCourses(listUUID);
                }
            }, SCAN_PERIOD);
            mLEScanner.startScan(filters, settings, mScanCallback);
        } else {
            mLEScanner.stopScan(mScanCallback);
        }
    }
    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.i("callbackType", String.valueOf(callbackType));
            Log.i("result", result.toString());
            BluetoothDevice btDevice = result.getDevice();
            //connectToDevice(btDevice);

            int startByte = 5;

            byte[] RecordBytes = result.getScanRecord().getBytes();
            byte[] uuidBytes = new byte[16];
            System.arraycopy(RecordBytes, startByte+4, uuidBytes, 0, 16);
            String hexString = bytesToHex(uuidBytes);

            //Here is your UUID
            String uuid =  hexString.substring(0,8) + "-" +
                    hexString.substring(8,12) + "-" +
                    hexString.substring(12,16) + "-" +
                    hexString.substring(16,20) + "-" +
                    hexString.substring(20,32);

            //Here is your Major value
            int major = (RecordBytes[startByte+20] & 0xff) * 0x100 + (RecordBytes[startByte+21] & 0xff);

            //Here is your Minor value
            int minor = (RecordBytes[startByte+22] & 0xff) * 0x100 + (RecordBytes[startByte+23] & 0xff);

            String UUID_Major = uuid + "-" + major;
            listUUID.add(UUID_Major);

            if(uuid.equals(strCyBeaconUUID_Course1)){
                AudioManager AppAudio = (AudioManager) con.getSystemService(Context.AUDIO_SERVICE);
                AppAudio.setRingerMode(1);
                AppAudio.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                AppAudio.setStreamMute(AudioManager.STREAM_ALARM, true);
                AppAudio.setStreamMute(AudioManager.STREAM_MUSIC, true);
                AppAudio.setStreamMute(AudioManager.STREAM_RING, true);
                AppAudio.setStreamMute(AudioManager.STREAM_SYSTEM, true);
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
                Log.i("ScanResult - Results", sr.toString());
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e("Scan Failed", "Error Code: " + errorCode);
        }
    };

    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private void SearchDataBaseforCourses(ArrayList<String> uuid_major){

        //search database for uuid at index i;
        if(uuid_major.size()>0) {
            Log.d("bleScanService","Creating database URL search string -sam");
            String url = Config.DATA_URL + uuid_major.get(UUIDindex);

            Log.d("bleScanService","Sending URL string request -sam");
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //loading.dismiss();
                    Log.d("bleScanService","Received response from database, parsing -sam");
                    if(!(response.equals("{\"result\":[]}"))) {
                        showJSON(response);
                    }
                    if(UUIDindex<listUUID.size()-1){
                        Log.d("bleScanService","Incrementing and researching database -sam");
                        UUIDindex++;
                        SearchDataBaseforCourses(listUUID);
                    }else{

                        if(listCourseNames.size()>0) {
                            AudioManager AppAudio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            AppAudio.setRingerMode(1);
                            AppAudio.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_ALARM, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_MUSIC, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_RING, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                        }
                        ServiceRunning = false;
                        Intent intent = new Intent();
                        intent.setAction("ACTION_SCAN_UPDATE");
                        intent.putExtra("CourseList", listCourseNames);
                        sendBroadcast(intent);
                        stopSelf();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("bleScanServie", error.getMessage().toString());
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

            //}
        }else {
            stopSelf();
        }
    }

    public void ParseDataBaseResponse(String ResponseString){
        //CourseName =
    }

    public ArrayList<String> getCourseNames()
    {
        return listCourseNames;
    }

    public ArrayList<String> getUUID()
    {
        return listUUID;
    }
    private void showJSON(String response){
        String b_id="";
        String className="";
        String classNum="";
        String link="";
        String output = "";

        Log.i("bleScanService","Found database match for uuid -sam");

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject linkData = result.getJSONObject(0);

            b_id = linkData.getString(Config.B_ID);
            className = linkData.getString(Config.CLASS_NAME);

            Log.d("bleScanService","Adding course '" + className + "' to list -sam");
            listCourseNames.add(className);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText(output);
    }

}
