package com.healthy.healthyhelper.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.healthy.healthyhelper.base.BaseActivity;
import com.healthy.healthyhelper.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Ming on 2016/4/3.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener{

    public static final String REGISTER_URL = "http://proj-309-05.cs.iastate.edu/volleyRegister.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";


    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button registBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        findViewById();
    }

    @Override
    protected void findViewById() {
        editTextUsername = (EditText)findViewById(R.id.name_edit);
        editTextPassword = (EditText)findViewById(R.id.pass_edit);
        registBtn=(Button)findViewById(R.id.user_regist_btn);
        registBtn.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    private void registerUser()// throws JSONException
    {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(RegistActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(RegistActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put(KEY_USERNAME,username);
                params.put(KEY_PASSWORD,password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.user_regist_btn){
            Toast.makeText(this,"Regist sucess! now, Jump to login",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,LoginActivity.class);
            this.startActivity(intent);
        }

        if(v == registBtn)
        {
            registerUser();
        }
    }


}
