
package com.healthy.healthyhelper.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;


/**
 * author Ming
 */

public abstract class BaseFragment extends Fragment {

	public static final String TAG = BaseFragment.class.getSimpleName();
	protected Handler mHandler = null;
	ProgressDialog progressDialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this.getActivity());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}


	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	/**
	 * 绑定控件id
	 */
	protected abstract void findViewById();

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this.getActivity(), pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}


/*	*//**加载进度条*//*
	public void showProgressDialog() {


		if(progressDialog!=null){
			progressDialog.cancel();
		}
		progressDialog=new ProgressDialog(getActivity());
		Drawable drawable=getResources().getDrawable(R.drawable.loading_animation);
		progressDialog.setIndeterminateDrawable(drawable);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("请稍候，正在努力加载。。");
		progressDialog.show();
	}*/
	/*public void closeProgressDialog(){
		if (progressDialog!=null){
			progressDialog.dismiss();
		}
	}*/
	
	
	public void DisplayToast(String str) {
		Toast.makeText(this.getActivity(), str, Toast.LENGTH_SHORT).show();
	}

	
	//获得当前程序版本信息
	protected String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = this.getActivity().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(this.getActivity().getPackageName(), 0);
		return packInfo.versionName;
	}

	/*獲得系統版本*/
	
	protected String getClientOs() {
		return android.os.Build.ID;
	}
	
	/*獲得系統版本號*/
	protected String getClientOsVer() {
		return android.os.Build.VERSION.RELEASE;
	}
	
	//獲得系統語言包
	protected String getLanguage() {
		return Locale.getDefault().getLanguage();
	}
	
 protected String getCountry() {
		
		return Locale.getDefault().getCountry();
	}

}
