package com.nimi.sqprotos.until;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时处理
 */
public class MyCounttITime extends CountDownTimer {
 private TextView clickBt;
   
    //因为这个项目只涉及验证码的倒计时，所以要设置下面两个字符串，只需要在start前进行设置
 private String btStr1="获取验证码";
 private String btStr2="重新获取(";
private Context context;
private int cor1;
private int cor2;

    /**
     *  
     * @param millisInFuture 总时间
     * @param countDownInterval 每次倒数时间
     * @param clickBt 倒计时操作的 控件
     * @param context 上下文 环境
     * @param cor1 倒计时过程中显示的背景
     * @param cor2 倒计时完成的显示背景
     */
	public MyCounttITime(long millisInFuture, long countDownInterval,TextView clickBt,Context context,int cor1,int cor2) {
		super(millisInFuture, countDownInterval);
		this.clickBt=clickBt;
		this.context=context;
		this.cor1=cor1;
		this.cor2=cor2;
	}

    /**
     * 
     * @param mill 剩余的总倒计时时间
     */
	@Override
	public void onTick(long mill) {
		clickBt.setTextColor(context.getResources().getColor(cor1));
		clickBt.setEnabled(false);
		clickBt.setText(btStr2+(mill/ 1000)+")秒");//倒计时过程中控件显示的文本
	}

	@Override
	public void onFinish() {
		clickBt.setTextColor(context.getResources().getColor(cor2));
       	clickBt.setText(btStr1);//倒计时完成控件显示的文本
	 clickBt.setEnabled(true);
	 
	}

}
