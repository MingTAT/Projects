package com.cybeacon.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cybeacon.R;
import com.cybeacon.base.BaseMVPActivity;
import com.cybeacon.constants.IntentExtras;
import com.cybeacon.course.CourseListActivity;
import com.cybeacon.home.HomeActivity;
import com.cybeacon.login.presenter.LoginPresenter;
import com.cybeacon.login.view.LoginView;
import com.cybeacon.register.RegisterActivity;

/**
 * @author Ming
 */

public class LoginActivity extends BaseMVPActivity<LoginPresenter,LoginView> implements LoginView{
    private Button loginButton;

    private EditText userNameEdit;
    private EditText userPasswordEdit;

    private TextView registerText;

    @Override
    protected void businessCode() {

    }

    @Override
    protected void bindUI(View rootView) {
        loginButton = ((Button) findViewById(R.id.loginButton));
        userNameEdit = (EditText) findViewById(R.id.userNameEdit);
        userPasswordEdit = (EditText) findViewById(R.id.userPasswordEdit);
        registerText = (TextView) findViewById(R.id.registerText);
    }

    @Override
    protected void bindEvent() {
        loginButton.setOnClickListener(onClickListener);
        registerText.setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loginButton:
                    presenter.doLogin(userNameEdit.getText().toString(),userPasswordEdit.getText().toString());
                    break;
                case R.id.registerText:
                    Intent intent = new Intent(mContext,RegisterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected LoginPresenter attachPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    protected LoginView attachView() {
        return this;
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(mContext,errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(mContext, CourseListActivity.class);
        intent.putExtra(IntentExtras.UserType, HomeActivity.UserType.PROFESSER);
        startActivity(intent);
        finish();
    }
}
