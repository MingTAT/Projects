package com.healthy.healthyhelper.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.healthy.healthyhelper.R;


/**
 * Created by Ming on 2016/4/3.
 */

public class ToastUtil {
    private static Toast mToast;
    private static TextView tv;

    public static void show(Context paramContext, String paramString) {
        show(paramContext, paramString, 0);
    }

    public static void show(Context paramContext, String paramString, int paramInt) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        View contentView = LayoutInflater.from(paramContext).inflate(R.layout.toast_layout, null);
        tv = (TextView) contentView.findViewById(R.id.contentTV);
        mToast = new Toast(paramContext);
        tv.setGravity(Gravity.CENTER);
        mToast.setView(contentView);
        mToast.setGravity(Gravity.CENTER, 0, 200);
        mToast.setDuration(paramInt);
        tv.setText(paramString);
        mToast.show();
    }
}
