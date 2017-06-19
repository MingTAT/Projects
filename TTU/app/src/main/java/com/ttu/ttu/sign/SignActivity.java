package com.ttu.ttu.sign;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.Region;
import com.aprilbrother.aprilbrothersdk.utils.AprilL;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.ttu.ttu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * 签到页面
 *
 * @author Ming
 */
public class SignActivity extends Activity {

    private final String RESULT_CODE = "resultCode";
    private final String RESULT_INFO = "resultInfo";
    private final int errCode = -1;
    private final int successCode = 0;
    private final String BUSINESS_CODE = "businessCode";
    private final int BUSINESS_CODE_SCAN = 1;
    private final int BUSINESS_CODE_STATUS = 2;
    private final int BUSINESS_CODE_START_SERVICE = 3;

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final String TAG = "BeaconList";
    private BeaconManager beaconManager;
    private ArrayList<Beacon> myBeacons = new ArrayList<>();

    private BridgeWebView bridgeWebView;

    /**
     * beacon 列表
     */
    //private RecyclerView beaconList;
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

    //  private BeaconListAdapter beaconListAdapter;
    /**
     * beacon 扫描服务是否开启
     */
    private boolean isBeaconScanServiceReady = false;


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
        // beaconList = ((RecyclerView) findViewById(R.id.beaconList));
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        bridgeWebView = (BridgeWebView) findViewById(R.id.webView);
    }

    /**
     * 绑定事件
     */
    private void bindEvent() {
        //   beaconList.setLayoutManager(new LinearLayoutManager(SignActivity.this, LinearLayoutManager.HORIZONTAL, false));
        //   beaconListAdapter = new BeaconListAdapter(R.layout.item_beacon_list_layout);
        //   beaconList.setAdapter(beaconListAdapter);
        stopButton.setOnClickListener(onClickListener);
        startButton.setOnClickListener(onClickListener);

        AprilL.enableDebugLogging(true);
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setRangingListener(rangingListener);
        beaconManager.setMonitoringListener(monitoringListener);
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        //加载测试网页
        bridgeWebView.loadUrl("file:///android_asset/demo.html");

        //注册beacon服务，供网页js调用
        bridgeWebView.registerHandler("beaconService", new BridgeHandler() {

            @Override
            public void handler(String param, CallBackFunction callBackFunction) {
                handleJsCall(param, callBackFunction);
            }

        });

    }

    /**
     * 处理js调用
     *
     * @param param            参数
     * @param callBackFunction 回调函数
     */
    private void handleJsCall(String param, final CallBackFunction callBackFunction) {
        Log.i(TAG, "接收到网页的参数 ： " + param);
        JSONObject returnObj = new JSONObject();
        try {
            JSONObject paramObj = new JSONObject(param);
            if (!paramObj.has("businessCode")) {
                callBackFunction.onCallBack(getReturnData(errCode, "未找到businessCode", null));
                return;
            }
            if (Integer.parseInt(paramObj.getString(BUSINESS_CODE)) == BUSINESS_CODE_SCAN) {
                //开始扫描
                beaconManager.startRanging(ALL_BEACONS_REGION);
                callBackFunction.onCallBack(getReturnData(successCode, "开启扫描成功", null));
                return;
            }
            if (Integer.parseInt(paramObj.getString(BUSINESS_CODE)) == BUSINESS_CODE_STATUS) {
                //获取扫描服务状态 是否开启
                JSONObject status = new JSONObject();
                status.put("isBeaconScanServiceReady", isBeaconScanServiceReady);
                callBackFunction.onCallBack(getReturnData(successCode, "获取成功", status));
                return;
            }
            if (Integer.parseInt(paramObj.getString(BUSINESS_CODE)) == BUSINESS_CODE_START_SERVICE) {
                //开启扫描服务
                if (isBeaconScanServiceReady) {
                    JSONObject startResult = new JSONObject();
                    startResult.put("isBeaconScanServiceReady", isBeaconScanServiceReady);
                    callBackFunction.onCallBack(getReturnData(successCode, "开启成功", startResult));
                } else {
                    //开启beacon扫描服务
                    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                        @Override
                        public void onServiceReady() {
                            isBeaconScanServiceReady = true;
                            JSONObject startResult = new JSONObject();
                            try {
                                startResult.put("isBeaconScanServiceReady", isBeaconScanServiceReady);
                                callBackFunction.onCallBack(getReturnData(successCode, "开启成功", startResult));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        } catch (Exception e) {
            try {
                returnObj.put("resultCode", errCode);
                returnObj.put("resultInfo", e == null ? "what?空指针.." : e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            callBackFunction.onCallBack(returnObj.toString());
        }
        //callBackFunction.onCallBack("我是native，接收到网页的消息啦：" + param);
    }

    private String getReturnData(int resultCode, String resultInfo, JSONObject data) throws JSONException {
        JSONObject returnObj = new JSONObject();
        returnObj.put(RESULT_CODE, resultCode);
        returnObj.put(RESULT_INFO, resultInfo);
        if (data != null) {
            returnObj.put("resultData", data);
        }
        return returnObj.toString();
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
                        if (!isBeaconScanServiceReady) {
                            Toast.makeText(SignActivity.this, "beacon service not connected,please try again!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        beaconManager.startRanging(ALL_BEACONS_REGION);
                        break;
                    case R.id.stopButton:
                        if (!isBeaconScanServiceReady) {
                            Toast.makeText(SignActivity.this, "beacon service not connected,please try again!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        beaconManager.stopRanging(ALL_BEACONS_REGION);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 最多扫描几次
     */
    private final int maxScan = 5;

    private int scanCount = 0;

    /**
     * beacon 范围变化监听回调函数
     */
    BeaconManager.RangingListener rangingListener = new BeaconManager.RangingListener() {
        @Override
        public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
            Log.i(TAG, "onBeaconsDiscovered: ");
            try {
                //判断最多扫描次数是否达到
                if (scanCount > maxScan) {
                    beaconManager.stopRanging(ALL_BEACONS_REGION);
                    scanCount = 0;
                    return;
                }
                Log.i(TAG, "------------------------------beacons.size = " + beacons.size());
                beaconTipText.setText("查找到的beacon数量: " + beacons.size());
                if (beacons.size() > 0) {
                    bridgeWebView.callHandler("beaconScanCallback", getBeaconList(beacons), new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            Log.i(TAG, "js 返回 函数" + data);
                            if (data.equals("success")) {
                                Log.i(TAG, "收到html页面的成功通知...停止扫描");
                                try {
                                    //停止扫描
                                    beaconManager.stopRanging(ALL_BEACONS_REGION);
                                    scanCount = 0;
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            scanCount++;
        }
    };


    /**
     * 获取beacon列表结果集
     * @param beacons beacon List
     * @return beacon列表结果集
     * @throws JSONException
     */
    private String getBeaconList(List<Beacon> beacons) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Beacon beacon : beacons) {
            JSONObject obj = new JSONObject();
            obj.put("mac",beacon.getMacAddress());
            obj.put("distance",beacon.getDistance());
            obj.put("major",beacon.getMajor());
            obj.put("name",beacon.getName());
            obj.put("uuid",beacon.getProximityUUID());
            obj.put("minor",beacon.getMinor());
            obj.put("rssi",beacon.getRssi());
            obj.put("power",beacon.getPower());
            jsonArray.put(obj);
        }
        return jsonArray.toString();
    }

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
                isBeaconScanServiceReady = true;
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
