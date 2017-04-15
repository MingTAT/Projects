package com.cybeacon.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cybeacon.R;
import com.cybeacon.base.BaseMVPActivity;
import com.cybeacon.register.presenter.RegisterPresenter;
import com.cybeacon.register.view.RegisterView;

/**
 * Created by Ming on 2017/4/15 0015.
 */

public class RegisterActivity extends BaseMVPActivity<RegisterPresenter,RegisterView> implements RegisterView {
    private EditText userNameEdit;
    private EditText userPasswordEdit;
    private EditText firstEdit;
    private EditText lastEdit;
    private Button registerButton;
    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(mContext,errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void businessCode() {

    }

    @Override
    protected void bindUI(View rootView) {
        userNameEdit = (EditText) findViewById(R.id.userNameEdit);
        userPasswordEdit = (EditText) findViewById(R.id.userPasswordEdit);
        firstEdit = (EditText) findViewById(R.id.firstEdit);
        lastEdit = (EditText) findViewById(R.id.lastEdit);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

    @Override
    protected void bindEvent() {
        registerButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.registerButton:
                    presenter.register(userNameEdit.getText().toString(),userPasswordEdit.getText().toString(),firstEdit.getText().toString(),lastEdit.getText().toString());
                    break;
            }
        }
    };


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register_layout;
    }

    @Override
    protected RegisterPresenter attachPresenter() {
        return new RegisterPresenter(mContext);
    }

    @Override
    protected RegisterView attachView() {
        return this;
    }

    @Override
    public void registerSuccess(String response) {
        Toast.makeText(mContext,"register success !",Toast.LENGTH_SHORT).show();
        finish();
    }
}
