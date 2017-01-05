package com.healthy.healthyhelper.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Locale;
import java.util.Stack;
/**
 * author Ming
 */
public class AppManager {
	private static Stack<Activity> mActivityStack;
	private static AppManager mAppManager;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getInstance() {
		if (mAppManager == null) {
			mAppManager = new AppManager();
		}
		return mAppManager;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * 获取栈顶Activity（堆栈中最后一个压入的）
	 */
	public Activity getTopActivity() {
		Activity activity = mActivityStack.lastElement();
		return activity;
	}

	/**
	 * 结束栈顶Activity（堆栈中最后一个压入的）
	 */
	public void killTopActivity() {
		Activity activity = mActivityStack.lastElement();
		killActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public boolean killActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
			return true;
		}
		return false;
	}

	public void restartActivity(Class<?> cls,Context context){
		if (cls != null){
			killActivity(cls);
			Intent intent = new Intent(context,cls);
			context.startActivity(intent);
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void killActivity(Class<?> cls) {
		boolean result = false;
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				if(activity!=null){
					activity.finish();
					activity = null;
					result = true;
				}
			}
		}
		if (result){
			mActivityStack.remove(cls);
		}

	}

	/**
	 * 结束所有Activity
	 */
	public void killAllActivity(Context context) {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	/**
	 *
	 * 获取渠道
	 *
	 * @param context
	 * @return
	 */
	public static String getAppChannel(Context context){
		ApplicationInfo info = null;
		try {
			info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		String msg =info.metaData.getString("UMENG_CHANNEL");
		return msg;
	}


	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		int versioncode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 返回当前程序版本名
	 */
	public static int getAppVersionCode(Context context) {
		int versioncode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versioncode = pi.versionCode;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versioncode;
	}


	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			killAllActivity(context);
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}

	/*獲得系統版本*/

	public String getClientOs() {
		return android.os.Build.ID;
	}

	/*獲得系統版本號*/
	public String getClientOsVer() {
		return android.os.Build.VERSION.RELEASE;
	}

	//獲得系統語言包
	public String getLanguage() {
		return Locale.getDefault().getLanguage();
	}

	public String getCountry() {

		return Locale.getDefault().getCountry();
	}
}
