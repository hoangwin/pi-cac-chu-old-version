package com.mobilepuzzle.candypopHD.ads;

import android.util.Log;
import android.widget.FrameLayout;

import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdListener;
import com.adsdk.sdk.banner.AdView;
import com.mobilepuzzle.candypopHD.CandyPop;




public class AdsMobFox implements AdListener{
	private static AdView adView = null;
	public static AdsMobFox instance = new AdsMobFox();
	public static void showMobfoxAds(FrameLayout mainLayout,CandyPop _gameLib )
	{	
		adView = new AdView(_gameLib, "http://my.mobfox.com/request.php","963e484e46818fb6c5c37e2ffc3092c1", true, true);
		adView.setAdListener(instance);
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);

		if (_gameLib.SCREEN_HEIGHT < 380) {
			adsParams.setMargins(-70, -20, -70, -20);
		}
		mainLayout.addView(adView,adsParams);
		
	}
	public void adClicked() {
		// TODO Auto-generated method stub
		
	}
	public void adClosed(Ad arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}
	public void adLoadSucceeded(Ad arg0) {
		Log.d("OK", "OK");
		
	}
	public void adShown(Ad arg0, boolean arg1) {
		Log.d("SHOW", "SHOW");
		
	}
	public void noAdFound() {
		// TODO Auto-generated method stub
		
	}
	public static void destroy() {
		// TODO Auto-generated method stub
		if(adView != null)
			adView = null;
		
	}
}
