package com.healthy.healthyhelper.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.BaseActivity;
import com.healthy.healthyhelper.base.Config;
import com.healthy.healthyhelper.register.RegistActivity;
import com.healthy.healthyhelper.student.StudentActivity;
import com.healthy.healthyhelper.teacher.TeacherActivity;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Ming on 2016/4/3.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private RadioGroup radioGroup;
    private Button loginBtn,registbtn;
    private final int TEACHER=1;
    private final int STUDENT=0;
    private int type=0;
    private EditText nameEdit;
    private EditText passEdit;
    private boolean loggedIn = false;

//    public static final String LOGIN_URL = "http://proj-309-05.cs.iastate.edu/login.php";
//
//    //Keys for username & password as defined in $_POST['key'] in login.php
//    public static final String KEY_USERNAME = "username";
//    public static final String KEY_PASSWORD = "password";
//
//    //If server response is equal to this taht means login is successful
//    public static final String LOGIN_SUCCESS = "success";
//
//    //Name of shared preferences
//    public static final String SHARED_PREF_NAME = "myloginapp";
//
//    //To store the username of current logged in user
//    public static final String USERNAME_SHARED_PREF = "username";
//
//    //To store the boolean in shared preference to track user is logged in or not
//    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final int TIME_OUT = 20000;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        initView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //In onResume fetching value from shared preference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value from shared preference
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        Log.i("login", String.valueOf(loggedIn));
        //if true
        //loggedIn = true;
        if(loggedIn)
        {
            //Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            //startActivity(intent);
            Intent intent =null;
            if (type==TEACHER){
                intent = new Intent(LoginActivity.this,TeacherActivity.class);

            }else{
                intent = new Intent(LoginActivity.this,StudentActivity.class);
                intent.putExtra("TraineeUsername","TraineeUsername");
            }
            startActivity(intent);
        }
    }

    @Override
    protected void findViewById() {
        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        loginBtn=(Button)findViewById(R.id.login_btn);
        registbtn=(Button)findViewById(R.id.regist_btn);
        nameEdit=(EditText)findViewById(R.id.name_edit);
        passEdit=(EditText)findViewById(R.id.pass_edit);
    }

    @Override
    protected void initView() {
        loginBtn.setOnClickListener(this);
        registbtn.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.radioButton){
                    type=TEACHER;
                }else if (checkedId==R.id.radioButton2){
                    type=STUDENT;
                }
            }
        });
    }

    private void login()
    {

        if (true){
            Intent intent;
            if (type==TEACHER){
                intent = new Intent(LoginActivity.this,TeacherActivity.class);

            }else{
                intent = new Intent(LoginActivity.this,StudentActivity.class);
                intent.putExtra("TraineeUsername","TraineeUsername");
            }
            startActivity(intent);
            return;
        }
        //getting values from edit texts
        final String username = nameEdit.getText().toString().trim();
        final String password = passEdit.getText().toString().trim();

        //creating a string request
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //if getting success from server
                        if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS))
                        {
                            //creating a shared preference
                            SharedPreferences sharedPreferences =
                                    LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                            //creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.USERNAME_SHARED_PREF, username);

                            //saving values to editor
                            editor.commit();

                            //starting profile activity
                            //Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            //startActivity(intent);
                            Intent intent;
                            if (type==TEACHER){
                                intent = new Intent(LoginActivity.this,TeacherActivity.class);

                            }else{
                                intent = new Intent(LoginActivity.this,StudentActivity.class);
                                intent.putExtra("TraineeUsername","TraineeUsername");
                            }
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        //handle error
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME,username);
                params.put(Config.KEY_PASSWORD,password);

                //returning parameter
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public boolean isNotEmpty(){
        if (!TextUtils.isEmpty(nameEdit.getText().toString())&&!TextUtils.isEmpty(passEdit.getText().toString())){
            return  true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View v) {
//        final String username = nameEdit.getText().toString().trim();
//        final String password = passEdit.getText().toString().trim();
        switch (v.getId()){
            case R.id.login_btn:{
                // if (!isNotEmpty()){
                // Toast.makeText(this, "Pelase input completely!", Toast.LENGTH_LONG).show();
                // return;
                //}
                //if (type==TEACHER){
                //    Intent intent = new Intent(this,TeacherMainActivity.class);
                //    startActivity(intent);

                //}else{
                //     Intent intent = new Intent(this,TraineeMainActivity.class);
                //    intent.putExtra("TraineeUsername","TraineeUsername");
                //    startActivity(intent);
                login();
                //}
            }
            break;
            case R.id.regist_btn:{
                Intent intent=new Intent(this,RegistActivity.class);
                startActivity(intent);
            }
            break;
            default:
                break;
        }
    }
}
