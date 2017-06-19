package com.ttu.ttu.sign;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.Region;
import com.aprilbrother.aprilbrothersdk.utils.AprilL;
import com.ttu.ttu.R;
import com.ttu.ttu.sign.adapter.BeaconListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 签到页面
 *
 * @author Ming
 */
public class SignActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final String TAG = "BeaconList";

    private BeaconManager beaconManager;
    private ArrayList<Beacon> myBeacons = new ArrayList<>();
    /**
     * beacon 列表
     */
    private RecyclerView beaconList;
    /**
     * 开始扫描
     */
    private Button startButton;

    /**
     * 停止扫描
     */
    private Button stopButton;

    /**
     * 顶部提示文字
     */
    private TextView beaconTipText;

    private BeaconListAdapter beaconListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_layout);
        bindView();
        bindEvent();

    }

    /**
     * 绑定view组件
     */
    private void bindView() {
        beaconTipText = ((TextView) findViewById(R.id.beaconTipText));
        beaconList = ((RecyclerView) findViewById(R.id.beaconList));
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
    }

    /**
     * 绑定事件
     */
    private void bindEvent() {
        beaconList.setLayoutManager(new LinearLayoutManager(SignActivity.this, LinearLayoutManager.VERTICAL, false));
        beaconListAdapter = new BeaconListAdapter(R.layout.item_beacon_list_layout);
        beaconList.setAdapter(beaconListAdapter);
        stopButton.setOnClickListener(onClickListener);
        startButton.setOnClickListener(onClickListener);

        AprilL.enableDebugLogging(true);
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setRangingListener(rangingListener);
        beaconManager.setMonitoringListener(monitoringListener);
    }

    private static final Region ALL_BEACONS_REGION = new Region(
            "com.ttu.ttu", null, null, null);

    /**
     * 点击事件监听
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.startButton:
                        beaconManager.startRanging(ALL_BEACONS_REGION);
                        break;
                    case R.id.stopButton:
                        beaconManager.stopRanging(ALL_BEACONS_REGION);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * beacon 范围变化监听回调函数
     */
    BeaconManager.RangingListener rangingListener = new BeaconManager.RangingListener() {
        @Override
        public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
            Log.i(TAG, "onBeaconsDiscovered: ");
            for (Beacon beacon : beacons) {
                Log.i(TAG, "beacon:"+beacon.getProximityUUID());
                /*if (beacon.getRssi() > 0) {
                    Log.i(TAG, "rssi = " + beacon.getRssi());
                    Log.i(TAG, "mac = " + beacon.getMacAddress());
                }*/
            }

            Log.i(TAG, "------------------------------beacons.size = " + beacons.size());

            myBeacons.clear();
            myBeacons.addAll(beacons);
            beaconTipText.setText("查找到的beacon数量: " + beacons.size());
            //排序
            Collections.sort(myBeacons, beaconComparator);
            beaconListAdapter.addData(myBeacons);
        }
    };

    BeaconManager.MonitoringListener monitoringListener = new BeaconManager.MonitoringListener() {
        @Override
        public void onEnteredRegion(Region region, List<Beacon> list) {
            Toast.makeText(SignActivity.this, "Notify in", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onExitedRegion(Region region) {
            Toast.makeText(SignActivity.this, "Notify out", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * beacon 比较器
     */
    Comparator<Beacon> beaconComparator = new Comparator<Beacon>() {
        @Override
        public int compare(Beacon lhs, Beacon rhs) {
            if (lhs.getRssi() == rhs.getRssi()) {
                int flag = lhs.getProximityUUID().compareTo(rhs.getProximityUUID());
                if (flag == 0) {
                    if (lhs.getMajor() == rhs.getMajor()) {
                        if (lhs.getMinor() == rhs.getMinor()) {
                            return 0;
                        } else {
                            return lhs.getMinor() - rhs.getMinor();
                        }
                    } else {
                        return lhs.getMajor() - rhs.getMajor();
                    }
                } else {
                    return flag;
                }
            } else {
                return rhs.getRssi() - lhs.getRssi();
            }
        }
    };

    /**
     * 连接服务 开始搜索beacon connect service start scan beacons
     */
    private void connectToService() {
        Log.i(TAG, "connectToService ...");
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    Log.i(TAG, "connectToService");
                    beaconManager.startRanging(ALL_BEACONS_REGION);
                    // beaconManager.startMonitoring(ALL_BEACONS_REGION);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        connectToService();
    }

    @Override
    protected void onStop() {
        try {
            myBeacons.clear();
            beaconManager.stopRanging(ALL_BEACONS_REGION);
            beaconManager.disconnect();
        } catch (RemoteException e) {
            Log.d(TAG, "Error while stopping ranging", e);
        }
        super.onStop();
    }

}
