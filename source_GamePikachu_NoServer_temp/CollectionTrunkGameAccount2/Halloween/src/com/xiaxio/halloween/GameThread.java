package com.xiaxio.halloween;

import android.util.Log;

public class GameThread implements Runnable {
	public static int FRAME_RATE = 20;
	public static long timeCurrent;
	public static long timePrev = System.currentTimeMillis();
	public static long timePrev2 = System.currentTimeMillis();

	public void run() {
		while (true) {
			while (HalloweenActivity.running) {

				timeCurrent = System.currentTimeMillis();

				if ((timeCurrent - timePrev) > 1000 / FRAME_RATE) {
					// Log.d("ccc :", " " + (timeCurrent - timePrev));
					timePrev2= timePrev;
					timePrev = timeCurrent;
					

					GameLib.frameCountCurrentState++;
					HalloweenActivity.SendMessage(HalloweenActivity.mCurrentState, HalloweenActivity.MESSAGE_UPDATE);
					// MainActivity.SendMessage(MainActivity.mCurrentState,MainActivity.MESSAGE_PAINT);
					// hame ve se duoc chuyen xuong ben duoi

					HalloweenActivity.mainView.postInvalidate();
					HalloweenActivity.UpdateKey();
				//	Log.d("aaa", "aaaa");
					HalloweenActivity.UpdateTouch();
				} else {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
