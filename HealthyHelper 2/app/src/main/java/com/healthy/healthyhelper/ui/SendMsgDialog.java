package com.healthy.healthyhelper.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.util.StringUtil;

/**
 * Created by Ming on 2016/4/3.
 */
public class SendMsgDialog {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 原生弹框
     */
    private AlertDialog alertDialog;

    /**
     * 弹框消失回调监听
     */
    private DialogDismissListener dialogDismissListener;

    private int animaitonDuration = 2*1000;

    /**
     * 内容图片
     */
    private ImageView centerImage;

    /**
     * 第一个按钮
     */
    private Button btn1;

    /**
     * 第二个按钮
     */
    private Button btn2;

    /**
     * 内容消息
     */
    private EditText messageView;

    /**
     * bt1 的父容器
     */
    private View btn1_parent;

    /**
     * bt2 的父容器
     */
    private View btn2_parent;

    /**
     * title 的整个布局
     */
    private View titleView;

    /**
     * 点击回调
     */
    private DialogClickListener dialogClickListener;

    /**
     * 关闭按钮
     */
    private ImageView closeBT;

    private View contentView;
    /**
     * 普通类型
     */
    public static final int NORMAL_TYPE = 0;

    public SendMsgDialog(Context context) {
        mContext = context;
        initNormalType();
    }
    /**
     *
     * 弹框初始化
     *
     */
    public void show() {
        if (alertDialog!=null && !alertDialog.isShowing()){
            alertDialog.show();
        }else {
            alertDialog = new AlertDialog.Builder(mContext).create();
            alertDialog.show();
        }
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogDismissListener != null) {
                    dialogDismissListener.onDismiss(dialog);
                }
            }
        });

        Window window = alertDialog.getWindow();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        alertDialog.getWindow().setLayout(mContext.getResources().getDimensionPixelSize(R.dimen.dialog_width    ), LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(contentView);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }


    /*public MsgDialog animation(int animId){
        if (contentView!=null){
            contentView.sta
        }
    }*/

    /**
     * 设置title
     *
     * @param title
     * @return MsgDialog
     */
    public SendMsgDialog title(String title) {
        //如果设置了title
        if (!StringUtil.isEmpty(title) && titleView != null) {
            titleView.setVisibility(View.VISIBLE);
            TextView titleTV = (TextView) titleView.findViewById(R.id.titleTV);
            titleTV.setText(title);
        }
        return this;
    }

    /**
     * 中间的图片
     *
     * @param url
     * @return
     */
    public SendMsgDialog centerImage(String url) {
        if (!StringUtil.isEmpty(url) && centerImage != null) {
            centerImage.setVisibility(View.VISIBLE);

        }

        return this;
    }

    /**
     * 点击事件回调
     *
     * @param dialogClickListener 回调函数
     * @return
     */
    public SendMsgDialog onClicklistener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    /**
     *
     * 消失回调
     *
     * @param dialogDismissListener
     * @return
     */
    public SendMsgDialog onDismisslistener(DialogDismissListener dialogDismissListener){
        this.dialogDismissListener =dialogDismissListener;
        return this;
    }

    /**
     * 左边按钮文字
     *
     * @param text
     * @return
     */
    public SendMsgDialog leftText(String text) {
        if (!StringUtil.isEmpty(text)) {
            btn1.setText(text);
        } else {
            if (btn1_parent!=null){
                btn1_parent.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 右边按钮文字
     *
     * @param text
     * @return
     */
    public SendMsgDialog rightText(String text) {
        if (!StringUtil.isEmpty(text)) {
            btn2.setText(text);
        } else {
            btn2_parent.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 弹框消失
     */
    public void dismissDialog() {
        if (null != alertDialog) {
            alertDialog.dismiss();
        }
    }

    public void dismissWithAnim(int animId){
        if(contentView!=null){
            dismissDialog();
        }
    }

    /**
     * 点击事件监听回调
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeBT:
                    dismissDialog();
                    break;
            }
        }
    };

    /**
     * 弹框消失回调事件
     */
    public interface DialogDismissListener {
        /**
         * 当调用此函数时 弹框已经触发消失事件
         *
         * @param dialogInterface
         */
        void onDismiss(DialogInterface dialogInterface);
    }

    public interface DialogClickListener {

        /**
         * 左按钮被点击时的回调事件
         */
        void leftButtonClick();

        /**
         * 右按钮被点击时的回调事件
         */
        void rightButtonClick(String content);
    }

    /**
     * 初始化普通类型
     */
    private void initNormalType(){
        contentView = LayoutInflater.from(mContext).inflate(R.layout.send_msg_dialog_layout,null);
        centerImage = (ImageView) contentView.findViewById(R.id.contentImage);
        btn1 = (Button) contentView.findViewById(R.id.dialog_btn1);
        btn2 = (Button) contentView.findViewById(R.id.dialog_btn2);
        messageView = (EditText) contentView.findViewById(R.id.message);//
        btn1_parent = contentView.findViewById(R.id.dialog_btn1_parent);
        btn2_parent = contentView.findViewById(R.id.dialog_btn2_parent);
        titleView = contentView.findViewById(R.id.titleRL);
        closeBT = (ImageView) contentView.findViewById(R.id.closeBT);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 第一个按钮事件
                if (dialogClickListener != null) {
                    dialogClickListener.leftButtonClick();
                }
                dismissDialog();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 第二个按钮事件
                if (dialogClickListener != null && !StringUtil.isEmpty(messageView.getText().toString())) {
                    dialogClickListener.rightButtonClick(messageView.getText().toString());
                }
                dismissDialog();
            }
        });
        //关闭按钮的点击事件
        closeBT.setOnClickListener(onClickListener);
    }

    public int getAnimaitonDuration() {
        return animaitonDuration;
    }

    public void setAnimaitonDuration(int animaitonDuration) {
        this.animaitonDuration = animaitonDuration;
    }
}
