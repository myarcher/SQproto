package com.nimi.sqprotos.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.nimi.sqprotos.base.SActivity;

import java.util.Stack;

/**
 * 自定义的activity的管理类
  */

public class SActivityManager {
 
	private static Stack<SActivity> activityStack;//使用栈的方式管理activity
	private static SActivityManager instance;

    /**
     * 确保单例
     */
	private SActivityManager() {
		if(activityStack == null) {
			activityStack = new Stack<SActivity>();
		}
	}

	public static SActivityManager getInstance() {
		if(instance == null) {
			instance = new SActivityManager();
		}
		return instance;
	}

    /**
     * 添加activity
     * @param activity 要添加的activity
     */
	public void addActivity(SActivity activity) {
		if(activityStack == null) {
			activityStack = new Stack<SActivity>();
		}
		activityStack.add(activity);
	}

    /**
     * 获取栈顶部的activity
     * @return  顶部activity
     */
	public SActivity currentActivity() {
        SActivity activity = activityStack.lastElement();
		return activity;
	}

    /**
     * 销毁栈顶的activity
     */
	public void finishActivity() {
        SActivity activity = activityStack.lastElement();
		if(activity != null) {
			activity.finish();
			activity = null;
		}
	}

    /**
     * 移除栈顶的activity
     */
	public void finishActivity(SActivity activity) {
		if(activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

    /**
     * 移除制定的activity
     */
	public void finishActivity(Class<?> cls) {
		for (SActivity activity : activityStack) {
			if(activity.getClass().equals(cls)) {
				this.finishActivity(activity);
			}
		}
	}


    /**
     * 移除所有的activity
     */
	public void finishAllActivity() {
		int size = activityStack.size();
		for (int i=0; i<size; i++) {
            if (null != activityStack.get(i)) {
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}


    /**
     * 退出应用（注意这个在6.0版本上，是需要动态权限的）
     * @param context 上下环境
     */
	@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void AppExit(Context context) {
		try {
			this.finishAllActivity();
			ActivityManager activityMgr = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
		//	System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    /**
     * 退出应用的提示框
     * @param context 上下文环境
     */
    public void showExitDialog(final Activity context){
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("你确定退出应用吗?");
		builder.setNegativeButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
			public void onClick(DialogInterface dialog, int which) {
				SActivityManager.this.AppExit(context);
				context.finish();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
	
	
}
