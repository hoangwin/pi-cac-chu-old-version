package com.game.fruitshooter.v2.ads;

//import com.apperhand.device.android.AndroidSDKProvider;
import com.game.fruitshooter.v2.GameLib;
import com.game.fruitshooter.v2.MainActivity;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.inmobi.androidsdk.IMAdRequest;
import com.inmobi.androidsdk.IMAdView;
//import com.inneractive.api.ads.InneractiveAd;

import android.content.Context;
import android.widget.FrameLayout;

public class AdsManager {
	
	private static AdView adView;
	public static Context context;
	public static GameLib gameLib;
	public static AdsManager instance;
	
	public static String UID_ADMOB_STRING = "a15208746b39a3d";//hoang.nguyenmau@hotmail.com kiem tra trong file manifest nua
	public static String UID_INNER_ACTIVE_STRING = "hoangnguyenmau_BubbleFruit_Android";// chi mot cho
	public static String UID_INMOBI = 	"faa84edfbcf049b9ad39a5b7dc6057a9";// candy blast
	//cd7bcda3215c47018f32056edc659b37
	//public static InneractiveAd iaBanner;
	
	public AdsManager()
	{
		
	}
	public static void showAdmobAds(FrameLayout mainLayout,MainActivity _gameLib )
	{
			gameLib = _gameLib;
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
	
	public static void showStartApp(MainActivity _gameLib)//not full
	{		
			//	AndroidSDKProvider.setTestMode(true);
			//AndroidSDKProvider.initSDK(_gameLib.context);			
			//AdPreferences adPreferences = new AdPreferences("106420618", "206410428", AdPreferences.TYPE_INAPP_EXIT);
			//htmlAd = new HtmlAd(this);
			//htmlAd.load(adPreferences);
		
	}
	
	public static void showInnerActive(FrameLayout mainLayout,MainActivity _gameLib)
	{
		/*
			FrameLayout layout1 = new FrameLayout(_gameLib);
			FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);
			
			
			iaBanner = new InneractiveAd(_gameLib, UID_INNER_ACTIVE_STRING, InneractiveAd.IaAdType.Banner, 60 );
			mainLayout.addView(iaBanner,adsParams);
		*/
			
	}
	public static void showInMobi(FrameLayout mainLayout,MainActivity _gameLib)
	{
		IMAdView imAdView = new IMAdView(_gameLib, IMAdView.INMOBI_AD_UNIT_320X50,UID_INMOBI);	
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);
		imAdView.disableHardwareAcceleration();
			if (gameLib.SCREEN_HEIGHT <= 400) {
				adsParams.setMargins(-70, -20, -70, -20);
			}
		imAdView.setLayoutParams(adsParams);//		
		mainLayout.addView(imAdView);
		IMAdRequest adRequest = new IMAdRequest();
		//adRequest.setTestMode(true);
		//imAdView.setIMAdListener(mIMAdListener);
        imAdView.setIMAdRequest(adRequest);;	        
        imAdView.loadNewAd(adRequest);
	}
	public static void adsDestroy()
	{
		if(adView!= null)
			adView.destroy();
		/*
		if (iaBanner != null){
		    iaBanner .cleanUp();     
		}
		*/
		
	}
}
