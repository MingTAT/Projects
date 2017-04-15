package com.cybeacon.home;

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
import android.widget.TextView;
import android.widget.Toast;
import com.cybeacon.home.AlarmReceiver;

import com.cybeacon.R;
import com.cybeacon.base.BaseMVPActivity;
import com.cybeacon.home.presenter.HomePresenter;
import com.cybeacon.home.view.HomeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Ming
 */

public class HomeActivity extends BaseMVPActivity<HomePresenter,HomeView> implements HomeView {

    Button studentButton;
    private Button professorButton;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 5000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    int RequestCode;
    public List UUID_list;
    private String strCyBeaconUUID_Course1 = "7B44B47B-52A1-5381-90C2-F09B6838C5D4-1";
    private String strCyBeaconUUID_Course2 = "7B44B47B-52A1-5381-90C2-F09B6838C5D4-2";
    private String strCyBeaconUUID_Course3 = "7B44B47B-52A1-5381-90C2-F09B6838C5D4-3";
    private Activity currentActivity;

    @Override
    protected void businessCode() {

    }

    @Override
    protected void bindUI(View rootView) {
        studentButton = (Button) findViewById(R.id.studentButton);
        professorButton = (Button) findViewById(R.id.professorButton);
    }

    @Override
    protected void bindEvent() {
        studentButton.setOnClickListener(onClickListener);
        professorButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.studentButton:
                    presenter.forwardCourseListView();
                    break;
                case R.id.professorButton:
                    presenter.forwardLoginView();
                    break;
            }
        }
    };

    public static class UserType{
        public static String STUDENT = "student";
        public static String PROFESSER = "professor";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home_layout;
    }

    @Override
    protected HomePresenter attachPresenter() {
        return new HomePresenter(mContext);
    }

    @Override
    protected HomeView attachView() {
        return this;
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    //
    //Bluetooth related functions
    //
    //public void scanLeDevice(final boolean enable) {
    //    if (enable) {
    //        //UUID_list.clear();
    //        mHandler.postDelayed(new Runnable() {
     //           @Override
    //            public void run() {
    //                mLEScanner.stopScan(mScanCallback);
    //            }
    //        }, SCAN_PERIOD);
    //        mLEScanner.startScan(filters, settings, mScanCallback);
    //    } else {
    //        mLEScanner.stopScan(mScanCallback);
    //    }
   // }
   // private ScanCallback mScanCallback = new ScanCallback() {
   //     @Override
   //     public void onScanResult(int callbackType, ScanResult result) {
   //         Log.i("callbackType", String.valueOf(callbackType));
   //         Log.i("result", result.toString());
   //         BluetoothDevice btDevice = result.getDevice();
   //         //connectToDevice(btDevice);
//
  //          int startByte = 5;
//
  //          byte[] RecordBytes = result.getScanRecord().getBytes();
    //        byte[] uuidBytes = new byte[16];
   //         System.arraycopy(RecordBytes, startByte+4, uuidBytes, 0, 16);
   //         String hexString = bytesToHex(uuidBytes;
//
  ///          //Here is your UUID
   //         String uuid =  hexString.substring(0,8) + "-" +
   //                 hexString.substring(8,12) + "-" +
   //                 hexString.substring(12,16) + "-" +
   //                 hexString.substring(16,20) + "-" +
   //                 hexString.substring(20,32);
//
  //          //Here is your Major value
   //         int major = (RecordBytes[startByte+20] & 0xff) * 0x100 + (RecordBytes[startByte+21] & 0xff);

            //Here is your Minor value
  //          int minor = (RecordBytes[startByte+22] & 0xff) * 0x100 + (RecordBytes[startByte+23] & 0xff);

    //        if(uuid.equals(strCyBeaconUUID_Course1)){
      //          AudioManager AppAudio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
      //          AppAudio.setRingerMode(1);
      //          AppAudio.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
      ///          AppAudio.setStreamMute(AudioManager.STREAM_ALARM, true);
      //          AppAudio.setStreamMute(AudioManager.STREAM_MUSIC, true);
      //          AppAudio.setStreamMute(AudioManager.STREAM_RING, true);
      //          AppAudio.setStreamMute(AudioManager.STREAM_SYSTEM, true);
      //      }
      //  }

//        @Override
  //      public void onBatchScanResults(List<ScanResult> results) {
    //        for (ScanResult sr : results) {
    //            Log.i("ScanResult - Results", sr.toString());
    //        }
    //    }

    //    @Override
    //    public void onScanFailed(int errorCode) {
    ///        Log.e("Scan Failed", "Error Code: " + errorCode);
    //    }
    //};

    //private BluetoothAdapter.LeScanCallback mLeScanCallback =
     //       new BluetoothAdapter.LeScanCallback() {
      //          @Override
       //         public void onLeScan(final BluetoothDevice device, int rssi,
        //                             byte[] scanRecord) {
         ///           runOnUiThread(new Runnable() {
           //             @Override
            //            public void run() {
           //                 Log.i("onLeScan", device.toString());
                            //connectToDevice(device);
            //            }
              //      });
               // }

            //};

   // public void connectToDevice(BluetoothDevice device) {
    //    if (mGatt == null) {
     //       mGatt = device.connectGatt(this, false, gattCallback);
      //      scanLeDevice(false);// will stop after first device detection
       // }
    //}

    //private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
      //  @Override
       // public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        //    Log.i("onConnectionStateChange", "Status: " + status);
         //   switch (newState) {
          //      case BluetoothProfile.STATE_CONNECTED:
           //         Log.i("gattCallback", "STATE_CONNECTED");
            //        gatt.discoverServices();
             //       break;
              //  case BluetoothProfile.STATE_DISCONNECTED:
               //     Log.e("gattCallback", "STATE_DISCONNECTED");
                //    break;
                //default:
                 //   Log.e("gattCallback", "STATE_OTHER");
            //}

        //}

        //@Override
        //public void onServicesDiscovered(BluetoothGatt gatt, int status) {
          //  List<BluetoothGattService> services = gatt.getServices();
            //Log.i("onServicesDiscovered", services.toString());
            //gatt.readCharacteristic(services.get(1).getCharacteristics().get
             //       (0));
        //}

//        @Override
  //      public void onCharacteristicRead(BluetoothGatt gatt,
    //                                     BluetoothGattCharacteristic
      //                                           characteristic, int status) {
        //    Log.i("onCharacteristicRead", characteristic.toString());
          //  gatt.disconnect();
        //}
    //};

   // static final char[] hexArray = "0123456789ABCDEF".toCharArray();
   // private static String bytesToHex(byte[] bytes) {
   //     char[] hexChars = new char[bytes.length * 2];
   //     for ( int j = 0; j < bytes.length; j++ ) {
   //         int v = bytes[j] & 0xFF;
   //         hexChars[j * 2] = hexArray[v >>> 4];
   //         hexChars[j * 2 + 1] = hexArray[v & 0x0F];
   //     }
   //     return new String(hexChars);
   // }

    //
    //CODE RELATED TO ALARM SYSTEM
    //

    public void StartAlarm(){
        AlarmManager AlarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Intent ReceiverIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, ReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent ReceiverIntent = new Intent("ALARM");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, ReceiverIntent, 0);

        long interval = 2000;
        AlarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+interval, interval, pendingIntent);

        Log.i("ALARM SET","ALARM SET");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Retrieve a PendingIntent that will perform a broadcast */
        //Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        super.onCreate(savedInstanceState);
        currentActivity = this;
        ActivityCompat.requestPermissions(currentActivity, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.SET_ALARM },
                RequestCode);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        mHandler = new Handler();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
          //  finish();
        }
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //registerReceiver(broadcastReceiver, new IntentFilter("ALARM_TRIGGERED"));
        //registerReceiver(BootBroadcastReceiver, new IntentFilter("DEVICE_BOOT"));

        StartAlarm();

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // internet lost alert dialog method call from here...

            Log.i("TRIGGER DETECTED","TRIGGER DETECTED");
            //scanLeDevice(true);
        }
    };

    //BroadcastReceiver BootBroadcastReceiver = new BroadcastReceiver() {
    //  @Override
    //    public void onReceive(Context context, Intent intent) {
//
    //        Log.i("BootBroadcastReciever","Device has booted, setting alarm now -sam");
    //        StartAlarm();
    //    }
    //};


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                        .build();
                filters = new ArrayList<ScanFilter>();
            }
            //scanLeDevice(true);
        }
    }

}

