package com.mobilepuzzle.candypopHD.ads;

//import com.apperhand.device.android.AndroidSDKProvider;
import java.util.Random;

import com.google.ads.Ad;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import com.mobilepuzzle.candypopHD.CandyPop;
import com.mobilepuzzle.candypopHD.GameLib;
import com.google.ads.AdListener;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;


public class AdmobAds  implements AdListener  {
	
	private static AdView adView;
	public static Context context;
	public static CandyPop gameLib;
	public static FrameLayout mainLayout;
	public static AdmobAds instance;
	
	public static String UID_ADMOB_STRING = "a1520af535cd875";//kiem tra trong file manifest nua
	public static int CURRENT_ID_ADS = -1;
	public static int countShowAddID = 0;
	public AdmobAds()
	{
		
	}
	public static void showAdmobAds(FrameLayout _mainLayout,CandyPop _gameLib )
	{
			gameLib = _gameLib;
			mainLayout = _mainLayout;
			adView = new AdView(gameLib, AdSize.BANNER, UID_ADMOB_STRING);
			mainLayout.setPadding(0, 0, 0, 0);
			FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);

			if (gameLib.SCREEN_HEIGHT < 380) {
				adsParams.setMargins(-70, -20, -70, -20);
			}
			mainLayout.addView(adView, adsParams);
			AdRequest request = new AdRequest();			
			adView.loadAd(request);
		
	}	
	public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode errorCode) {
	  Log.d("Admob Error", "failed to receive ad (" + errorCode + ")");
	  AdsManager.showAds(mainLayout, gameLib);
	}
	
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		
	}		
	public static void adsDestroy()
	{		
			if(adView!= null)
				adView.destroy();
	}
}

