package com.cybeacon.login.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cybeacon.base.BasePresenter;
import com.cybeacon.base.IPresenter;
import com.cybeacon.login.view.LoginView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @author Ming
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleError(int errorCode, String message) {

    }

    /**
     *
     *  登录
     *
     * @param userName
     * @param userPassword
     */
    public void doLogin(String userName, String userPassword) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)){
            Toast.makeText(mContext,"userName and userPassword can not be null",Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.post().url("http://may1714.sd.ece.iastate.edu/login.php")
                .addParams("username",userName)
                .addParams("password",userPassword)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        view.showErrorMessage("login failed");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("response-------------",response);
                        if ("failure".equals(response)){
                            view.showErrorMessage("login failed");
                        }else {
                            view.loginSuccess();
                        }
                    }
                });
    }
}
