package com.cybeacon.register.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.cybeacon.base.BasePresenter;
import com.cybeacon.register.view.RegisterView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Ming on 2017/4/15 0015.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {
    public RegisterPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleError(int errorCode, String message) {
        view.showErrorMessage(message);
    }

    public void register(String userName, String userPassword, String first, String last) {
        if (TextUtils.isEmpty(userName)||TextUtils.isEmpty(userPassword)
                ||TextUtils.isEmpty(first)||TextUtils.isEmpty(last)){
            view.showErrorMessage("Please fill in the complete information");
            return;
        }
        OkHttpUtils.post().url("http://may1714.sd.ece.iastate.edu/register.php")
                .addParams("username",userName)
                .addParams("password",userPassword)
                .addParams("first",first)
                .addParams("last",last)
                .addParams("status","1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        view.showErrorMessage("register failed");
                    }

                    @Override
                    public void onResponse(String response) {
                        if ("Could not register".equals(response)){
                            view.showErrorMessage(response);
                        }else {
                            view.registerSuccess(response);
                        }
                    }
                });

    }
}
