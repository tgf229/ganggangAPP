package com.ganggang.ganggangapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @Description: 通过BroadcastReceiver重启当前页面，实现语言切换
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-7-27下午6:05:50
 * @Google+ http://gplus.to/hanleytowne 
 */
public class SwitchReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Intent it = new Intent(context,MainActivity.class);
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这个必须加
		context.startActivity(it);
	}

}
