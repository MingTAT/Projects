package com.ttu.ttu.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ttu.ttu.Cis.CisWeb;
import com.ttu.ttu.R;
import com.ttu.ttu.menu.Menu;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends Activity {


    public static String BuildSERIAL = android.os.Build.SERIAL;

    private static final int SUCCESS = 1;
    private static final int ERROR_PARAMETER_ERROR = 2;
    private static final int ERROR_ACCOUNT_NOT_EXISTS = 3;
    private static final int ERROR_ACCOUNT_NOT_REGISTRATION = 4;
    private static final int ERROR_DEVICE_NOT_REGISTRATION = 4;
    private static final int ERROR_ACCOUNT_REGISTRATION_NOT_MATCH = 5;
    private static final int ERROR_PWD_DECRYPTION = 6;
    private static final int ERROR_PWD_WRONG = 7;
    private static final int ERROR_SESSION_KEY_EXPIRATION = 8;
    private static final int ERROR = 99;
    private static final int ERROR_NO_INTERNET = 100;
    private static final int ERROR_NO_USER_INFO = 101;
    private static final int ERROR_SERVER_FAIL = 102;
    private static final int ERROR_MCRYPTION_FAIL = 103;
    private Activity instance;
    private Handler handler;
    private String URL_GET_KEY ;
    private String URL_APP_LOGIN ;
    private String URL_REGISTATION ;
    private EditText editTextAccount;
    private EditText editTextPwd;
    private Button btnLogin;

    private String account;
    private String pwd;
    private String sessionkey;

    private String deviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        account = Helper.getAccount(instance);
        pwd = Helper.getPassword(instance);
        URL_GET_KEY = "http://ttucis.ttu.edu.tw/mobileauth/getkey.php?";
        URL_APP_LOGIN = "http://ttucis.ttu.edu.tw/mobileauth/applogin.php?";
        URL_REGISTATION = "http://ttucis.ttu.edu.tw/mobileauth/registration.php?";

        if(account.isEmpty() && pwd.isEmpty()){
            setContentView(R.layout.activity_main);
            findViews();
            setListener();
        }

        initHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(account.isEmpty() && pwd.isEmpty()){
            btnLogin.setEnabled(true);
            btnLogin.setText(R.string.login);
        }
        doLogin();
    }

    private void findViews() {
        editTextAccount = (EditText) findViewById(R.id.editTextAccount);
        editTextPwd = (EditText) findViewById(R.id.editTextPwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void setListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = editTextAccount.getText().toString(); //輸入框的帳號密碼
                pwd = editTextPwd.getText().toString();
                Helper.setAccount(instance, account);
                Helper.setPassword(instance, pwd);

                doLogin();
            }
        });
    }

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case HTTPQuery.GET_KEY_THREAD:
                        try {
                            JSONObject jsonObject = (JSONObject) msg.obj;
                            int status = jsonObject.getInt("Status");
                            switch(status) {
                                case ERROR_DEVICE_NOT_REGISTRATION:
                                    new HTTPQuery(URL_REGISTATION+ "UserID=" + account + "&RegistID=" + deviceid, HTTPQuery.REGISTATION_THREAD, handler).start();
                                    break;
                                case SUCCESS:
                                    String iv = jsonObject.getString("iv");
                                    int keyLoc = jsonObject.getInt("KeyLoc");
                                    String key = deviceid.substring(keyLoc, keyLoc + 16);
                                    byte[] plaintextBytes = Arrays.copyOf(pwd.getBytes(), (pwd.getBytes().length / 16 + 1) * 16);
                                    SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "AES/CBC/NoPadding");
                                    IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
                                    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                                    cipher.init(Cipher.ENCRYPT_MODE, spec, ivspec);

                                    byte[] encryptedByteArray = cipher.doFinal(plaintextBytes);
                                    byte[] base64EncryptedByteArray = Base64.encode(encryptedByteArray, Base64.URL_SAFE);

                                    String pCode = new String(base64EncryptedByteArray).replace("\n", "");
                                    new HTTPQuery(URL_APP_LOGIN + "ID=" + account + "&pCode=" + pCode, HTTPQuery.APP_LOGIN_THREAD, handler).start();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_SERVER_FAIL);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_MCRYPTION_FAIL);
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_MCRYPTION_FAIL);
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_MCRYPTION_FAIL);
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_MCRYPTION_FAIL);
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_MCRYPTION_FAIL);
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_MCRYPTION_FAIL);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if(Helper.isNumeric(e.getMessage())) {
                                errorHandle(Integer.parseInt(e.getMessage()));
                            }
                        }
                        break;


                    case HTTPQuery.APP_LOGIN_THREAD:
                        try {
                            JSONObject jsonObject = (JSONObject) msg.obj;
                            int status = jsonObject.getInt("Status");
                            switch(status) {
                                case SUCCESS:
                                    sessionkey = jsonObject.getString("SessionKey");
                                    Intent intent = new Intent(instance,CisWeb.class);
                                    Helper.setSessionkey(instance, sessionkey);
                                    System.out.println("helpersessionkey=" + Helper.getSessionkey(instance));
                                    intent.putExtra(Helper.EXTRA_SESSION_KEY, sessionkey);
                                    intent.putExtra(Helper.EXTRA_LOGIN_ID, account);
                                    Helper.setAccount(instance, account);
                                    Helper.setPassword(instance, pwd);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case ERROR_PARAMETER_ERROR:
                                    throw new Exception(String.valueOf(ERROR_PARAMETER_ERROR));
                                case ERROR_ACCOUNT_NOT_EXISTS:
                                    throw new Exception(String.valueOf(ERROR_ACCOUNT_NOT_EXISTS));
                                case ERROR_PWD_DECRYPTION:
                                    throw new Exception(String.valueOf(ERROR_PWD_DECRYPTION));
                                case ERROR_PWD_WRONG:
                                    throw new Exception(String.valueOf(ERROR_PWD_WRONG));
                                case ERROR_SESSION_KEY_EXPIRATION:
                                    throw new Exception(String.valueOf(ERROR_SESSION_KEY_EXPIRATION));
                                case ERROR :
                                    btnLogin.setEnabled(true);
                                    btnLogin.setText(R.string.login);

                            }
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_SERVER_FAIL);
                        }

                        catch (Exception e) {
                            e.printStackTrace();
                            if(Helper.isNumeric(e.getMessage())) {
                                errorHandle(Integer.parseInt(e.getMessage()));
                            }
                        }
                        break;
                    case HTTPQuery.ERROR_OCCURENCE:
                        errorHandle(ERROR_SERVER_FAIL);
                        break;

                    case HTTPQuery.REGISTATION_THREAD:
                        try {
                            JSONObject jsonObject = (JSONObject) msg.obj;
                            int status = jsonObject.getInt("Status");
                            switch (status){
                                case SUCCESS:
                                    String rid = deviceid.substring(deviceid.length()-6);
                                    new HTTPQuery(URL_GET_KEY + "UserID=" + account + "&rid=" + rid, HTTPQuery.GET_KEY_THREAD, handler).start();
                                    break;
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                            errorHandle(ERROR_SERVER_FAIL);
                        }

                }
                return false;
            }
        });
    }

    private void doLogin() {

        if(account.isEmpty() && pwd.isEmpty()){
            btnLogin.setEnabled(false);
            btnLogin.setText(R.string.login_processing);
            editTextAccount.setText(account);
            editTextPwd.setText(pwd);
        }

        deviceid = Helper.getDeviceid(instance);
        String rid = deviceid.substring(deviceid.length()-6);

        if(account.length() == 0 || pwd.length() == 0) {
            errorHandle(99);
            return;
        }
        if(!Helper.haveInternet(instance)) {
            errorHandle(ERROR_NO_INTERNET);
            return;
        }

        new HTTPQuery(URL_GET_KEY + "UserID=" + account + "&rid=" + rid, HTTPQuery.GET_KEY_THREAD, handler).start();
    }


    private void errorHandle(int errorType) {
        try{
            switch(errorType) {
                case ERROR_NO_INTERNET:
                    Toast.makeText(instance, getString(R.string.ERROR_NO_INTERNET), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_NO_USER_INFO:
                    Toast.makeText(instance, getString(R.string.ERROR_NO_USER_INFO), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_SERVER_FAIL:
                    Toast.makeText(instance, getString(R.string.ERROR_SERVER_FAIL), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_MCRYPTION_FAIL:
                    Toast.makeText(instance, getString(R.string.ERROR_MCRYPTION_FAIL), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_PARAMETER_ERROR:
                    Toast.makeText(instance, getString(R.string.ERROR_PARAMETER_ERROR), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_ACCOUNT_NOT_EXISTS:
                    Toast.makeText(instance, getString(R.string.ERROR_ACCOUNT_NOT_EXISTS), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_ACCOUNT_NOT_REGISTRATION:
                    Toast.makeText(instance, getString(R.string.ERROR_ACCOUNT_NOT_REGISTRATION), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_ACCOUNT_REGISTRATION_NOT_MATCH:
                    Toast.makeText(instance, getString(R.string.ERROR_ACCOUNT_REGISTRATION_NOT_MATCH), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_PWD_DECRYPTION:
                    Toast.makeText(instance, getString(R.string.ERROR_PWD_DECRYPTION), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_PWD_WRONG:
                    Toast.makeText(instance, getString(R.string.ERROR_PWD_WRONG), Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_SESSION_KEY_EXPIRATION:
                    Toast.makeText(instance, getString(R.string.ERROR_SESSION_KEY_EXPIRATION), Toast.LENGTH_SHORT).show();
                    break;
            }
        }catch (NullPointerException e){
            Toast.makeText(instance,"can go in there", Toast.LENGTH_SHORT).show();
        }

        Helper.setAccount(MainActivity.this, "");
        Helper.setPassword(MainActivity.this, "");
        btnLogin.setEnabled(true);
        btnLogin.setText(R.string.login);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, Menu.class);
        MainActivity.this.startActivity(intent);
        MainActivity.this.finish();
    }


    public void ConfirmExit(){

        AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this); //創建訊息方塊

        ad.setTitle("EXIT");

        ad.setMessage("確定要離開?");

        ad.setPositiveButton("是", new DialogInterface.OnClickListener() { //按"是",則退出應用程式

            public void onClick(DialogInterface dialog, int i) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }

        });

        ad.setNegativeButton("否", new DialogInterface.OnClickListener() { //按"否",則不執行任何操作

            public void onClick(DialogInterface dialog, int i) {

            }

        });

        ad.show();//顯示訊息視窗

    }
}