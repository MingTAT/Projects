package com.healthy.healthyhelper.student.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.BaseFragment;
import com.healthy.healthyhelper.base.Config;
import com.healthy.healthyhelper.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ming on 2016/4/3.
 */
public class PhysicalExaminationFragment extends BaseFragment implements View.OnClickListener {

    private View contentView;

    private EditText heightEdit, weightEdit, waistEidt, sexEdit, ageEdit;
    private Button testBtn;
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_WAIST = "waist";
    public static final String KEY_SEX = "sex";
    public static final String KEY_AGE = "age";
    public static final String KEY_NAME = "username";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.examination_layout, null);
        findViewById();
        return contentView;
    }

    @Override
    protected void findViewById() {
        heightEdit = (EditText) contentView.findViewById(R.id.height_edit);
        weightEdit = (EditText) contentView.findViewById(R.id.weight_edit);
        waistEidt = (EditText) contentView.findViewById(R.id.waist_edit);
        sexEdit = (EditText) contentView.findViewById(R.id.sex_edit);
        ageEdit = (EditText) contentView.findViewById(R.id.age_edit);
        testBtn = (Button) contentView.findViewById(R.id.test_btn);
        testBtn.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }


    @Override
    public void onClick(View v) {
        String report = "This is your body exam result";
        if (v.getId() == R.id.test_btn) {
            if (!isNotEmpty()) {
                ToastUtil.show(getActivity(), "Please input completely!", Toast.LENGTH_LONG);
                //Toast.makeText(this, username, Toast.LENGTH_LONG).show();
                return;
            } else {
                if (sexEdit.getText().toString().equals("F")) {
                    double referenceAF = Float.parseFloat(waistEidt.getText().toString()) * 0.74;
                    double referenceBF = Float.parseFloat(weightEdit.getText().toString()) * 0.082 + 34.89;
                    double referenceWF = referenceAF - referenceBF;
                    double BFR_Woman = (referenceWF / Float.parseFloat(weightEdit.getText().toString()));

                    if (BFR_Woman > 0.3) {
                        report = "C";
                    }
                    if (Integer.valueOf(ageEdit.getText().toString()) <= 30) {
                        if (BFR_Woman <= 0.17) {
                            report = "A";
                        }
                        if (BFR_Woman >= 0.24) {
                            report = "C";
                        }
                        if ((BFR_Woman > 0.17) && (BFR_Woman < 0.24)) {
                            report = "B";
                        }
                    } else {
                        if (BFR_Woman <= 0.2) {
                            report = "A";
                        }
                        if (BFR_Woman >= 0.27) {
                            report = "C";
                        }
                        if ((BFR_Woman > 0.2) && (BFR_Woman < 0.27)) {
                            report = "B";
                        }
                    }
                } else if (sexEdit.getText().toString().equals("M")) {
                    double referenceAM = Float.parseFloat(waistEidt.getText().toString()) * 0.74;
                    double referenceBM = Float.parseFloat(weightEdit.getText().toString()) * 0.082 + 44.74;
                    double referenceWM = referenceAM - referenceBM;
                    double BFR_Man = (referenceWM / Float.parseFloat(weightEdit.getText().toString())) * 100;

                    if (BFR_Man > 25) {
                        report = "C";
                    }
                    if (Integer.valueOf(ageEdit.getText().toString()) <= 30) {
                        if (BFR_Man <= 14) {
                            report = "A";
                        }
                        if (BFR_Man >= 20) {
                            report = "C";
                        }
                        if ((BFR_Man > 14) && (BFR_Man < 20)) {
                            report = "B";
                        }
                    } else {
                        if (BFR_Man <= 17) {
                            report = "A";
                        }
                        if (BFR_Man >= 23) {
                            report = "C";
                        }
                        if ((BFR_Man > 17) && (BFR_Man < 23)) {
                            report = "B";
                        }
                    }
                }
                updateInfo();
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra("report", report);
                startActivity(intent);
            }
        }
    }

    private void updateInfo(){
        //嗯，这一行明显传错了~
        //final String username = LoginActivity.KEY_USERNAME.trim();
        //final String password = LoginActivity.KEY_PASSWORD.trim();
        final String height = heightEdit.getText().toString().trim();
        final String weight = weightEdit.getText().toString().trim();
        final String waist = waistEidt.getText().toString().trim();
        final String age = ageEdit.getText().toString().trim();
        final String sex = sexEdit.getText().toString().trim();

        //获取传递数据
//        Intent intent = getIntent();
//
//        //通过intent对象取得bundle对象，通过key-value格式取得数据
//        Bundle bundle = intent.getExtras();
//        //设置名称
//        username = bundle.getString("username");



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.TEST_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        ToastUtil.show(getActivity(),response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        ToastUtil.show(getActivity(),error==null?"":error.toString());
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, "Not Available");

                Map<String,String> params = new HashMap<String,String>();

                params.put(KEY_NAME,username);
                params.put(KEY_AGE,age);
                params.put(KEY_HEIGHT,height);
                params.put(KEY_SEX,sex);
                params.put(KEY_WAIST,waist);
                params.put(KEY_WEIGHT,weight);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private boolean isNotEmpty() {
        if (!TextUtils.isEmpty(heightEdit.getText().toString())
                && !TextUtils.isEmpty(weightEdit.getText().toString())
                && !TextUtils.isEmpty(waistEidt.getText().toString())
                && !TextUtils.isEmpty(sexEdit.getText().toString())
                && !TextUtils.isEmpty(ageEdit.getText().toString())
                ) {
            return true;
        }else
            return false;
    }
}
