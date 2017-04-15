package com.cybeacon.home;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.content.pm.PackageManager;
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
import com.cybeacon.home.bleScanService;

import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {

    public Context con;

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


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver","Alarm went off -sam");
        con = context;

        //Log.i("AlarmReceiver","Getting bluetooth service -sam");


        Log.i("AlarmReceiver","Starting ble scan service -sam");
        //Intent ScanServiceIntent = new Intent(con, bleScanService.class);

        //con.startService(new Intent(con, bleScanService.class));
    }


}
