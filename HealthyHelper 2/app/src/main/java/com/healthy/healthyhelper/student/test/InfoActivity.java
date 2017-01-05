package com.healthy.healthyhelper.student.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.Config;
import com.healthy.healthyhelper.login.LoginActivity;
import com.healthy.healthyhelper.model.FoodPlan;
import com.healthy.healthyhelper.model.TrainPlan;
import com.healthy.healthyhelper.student.test.adapter.InfoAdapter;
import com.healthy.healthyhelper.student.test.presenter.InfoPresenter;
import com.healthy.healthyhelper.student.test.presenter.InfoPresenterImpl;
import com.healthy.healthyhelper.student.test.view.InfoView;
import com.healthy.healthyhelper.ui.MyListView;
import com.healthy.healthyhelper.util.ToastUtil;
import com.jp.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Ming on 2016/4/3.
 */
public class InfoActivity extends AppCompatActivity implements InfoView {

    private TextView reportTxt;
    private TextView textView;
    private MyListView infoList;
    private InfoPresenter infoPresenter;
    private TextView userLogin;
    private TextView planNameTV;
    private String planName;
    private RadioGroup planGroup;
    private InfoAdapter infoAdapter;
    private List<String> days;
    private WheelView wheelView;
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        days = Arrays.asList(getResources().getStringArray(R.array.day));
        planName = getIntent().getStringExtra("report");
        View headView = LayoutInflater.from(InfoActivity.this).inflate(R.layout.test_info_head_layout, null);
        planNameTV = (TextView) headView.findViewById(R.id.planName);
        userLogin = (TextView) headView.findViewById(R.id.loginUser);
        planGroup = (RadioGroup) headView.findViewById(R.id.planGroup);
        wheelView = (WheelView) headView.findViewById(R.id.wheelView);
      //  progressDialog = new ProgressDialog(InfoActivity.this);
        infoPresenter = new InfoPresenterImpl(this);
        infoList = (MyListView) findViewById(R.id.infoList);
        infoList.addHeaderView(headView);
        infoList.setAdapter(null);
      //  progressDialog.show(InfoActivity.this,"get data ...","please wait ...");

        infoPresenter.loadData(planGroup.getCheckedRadioButtonId(), planName, "Tu");
        wheelView.setData(initDatas());
        planNameTV.setText("結果:" + planName);
        planGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        wheelView.setOnSelectListener(onSelectListener);
    }


    WheelView.OnSelectListener onSelectListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int id, final String text) {
          //  progressDialog.show(InfoActivity.this,"get data ...","please wait ...");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    infoPresenter.loadData(planGroup.getCheckedRadioButtonId(),planName,text);
                }
            });

        }

        @Override
        public void selecting(int id, String text) {

        }
    };

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            //progressDialog.show(InfoActivity.this,"get data ...","please wait ...");
            infoPresenter.loadData(checkedId, planName, wheelView.getSelectedText());
        }
    };

    private ArrayList<String> initDatas() {

        ArrayList<String> arrayList = new ArrayList<>();
        for (String day:days){
            arrayList.add(day);
        }
        return arrayList;


    }


    @Override
    public void loadFoodPlan(List<FoodPlan.ResultBean> result) {
      //  progressDialog.dismiss();
        if (infoAdapter == null) {
            infoAdapter = new InfoAdapter(getApplicationContext(), initLayouts(), result);
            infoList.setAdapter(infoAdapter);
        } else {
            infoAdapter.refreshDatas(result);
        }
    }

    @Override
    public void loadTrainPlan(List<TrainPlan.ResultBean> result) {
       // progressDialog.dismiss();
        if (infoAdapter == null) {
            infoAdapter = new InfoAdapter(getApplicationContext(), initLayouts(), result);
            infoList.setAdapter(infoAdapter);
        } else {
            infoAdapter.refreshDatas(result);
        }
    }

    @Override
    public void loadError(String msg) {
      //  progressDialog.dismiss();
        ToastUtil.show(getApplicationContext(), msg);
    }

    private SparseArray<Integer> initLayouts() {
        SparseArray<Integer> layout = new SparseArray<>();
        layout.put(0, R.layout.item_train_layout);
        layout.put(1, R.layout.item_food_layout);
        return layout;
    }

    //Logout function
    private void logout() {
        //Creating an alert dialog to confirm logout 
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //getting out shared preferences
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                //getting editor
                SharedPreferences.Editor editor = preferences.edit();

                //putting the value false for loggedin
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                //putting blank value to username
                editor.putString(Config.USERNAME_SHARED_PREF, "");

                //saving the shared preferences
                editor.commit();

                //starting login activity
                Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //do nothing
            }
        });

        //showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //adding menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //call logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

}
