package com.ez.sendem.function;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.ez.sendem.R;

public class SoundFunction {
	public static void playNotif(Context context, NotificationCompat.Builder mBuilder)
	{
		mBuilder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notif));
		long[] pattern = {0, 250};
		mBuilder.setVibrate(pattern);
	}
}
