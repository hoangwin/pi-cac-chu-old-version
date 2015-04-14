package com.xiaxio.petmonsterpop;

//import com.apperhand.device.android.AndroidSDKProvider;


import com.inneractive.api.ads.InneractiveAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class AdsManager {
	
	public static Context context;
	public static GameLib gameLib;
	public static AdsManager instance;
	
	public static String UID_ADMOB_STRING = "a151c55c766a12b";//kiem tra trong file manifest nua
	public static String UID_INNER_ACTIVE_STRING = "Caogia_PetMonsterPop_Android";// chi mot cho
	public static String UID_INMOBI = 	"d43688dbc2e1409b9a70349150833a52";// cua game pet monster pop//chi mot cho
	public static InneractiveAd iaBanner;
	
	public AdsManager()
	{
		
	}
	/*
	public static void showAdmobAds(FrameLayout mainLayout,Monster _gameLib )
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
	*/
	public static void showStartApp(FrameLayout mainLayout,Monster _gameLib)//not full
	{		
		//
		StartAppSDK.init(_gameLib, "106420618", "207384838", true);
		 //  RelativeLayout mainLayout = new RelativeLayout(_gameLib);// (RelativeLayout)findViewById(R.id.mainLayout);   
		   // Define StartApp Banner
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);
		   Banner startAppBanner = new Banner(_gameLib);			  
		   // Add to main Layout
		   mainLayout.addView(startAppBanner, adsParams);	
		
	}
	
	public static void showInnerActive(FrameLayout mainLayout,Monster _gameLib)
	{
		if(true)//inner-active
		{
			FrameLayout layout1 = new FrameLayout(_gameLib);
			FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);
			
			
			iaBanner = new InneractiveAd(_gameLib, UID_INNER_ACTIVE_STRING, InneractiveAd.IaAdType.Banner, 60 );
			mainLayout.addView(iaBanner,adsParams);
			/*
			if(InneractiveAd.displayAd(_gameLib, layout1, UID_INNER_ACTIVE_STRING, InneractiveAd.IaAdType.Banner, 60))
			{
				mainLayout.addView(iaBanner,adsParams);
			}
			else
			{
				
			}
			*/
			
		}
	}
	public static void showInMobi(FrameLayout mainLayout,Monster _gameLib)
	{
		/*
		IMAdView imAdView = new IMAdView(_gameLib, IMAdView.INMOBI_AD_UNIT_320X50,UID_INMOBI);	
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);
		imAdView.disableHardwareAcceleration();
		imAdView.setLayoutParams(adsParams);//		
		mainLayout.addView(imAdView);
		IMAdRequest adRequest = new IMAdRequest();
		adRequest.setTestMode(true);
		//imAdView.setIMAdListener(mIMAdListener);
        imAdView.setIMAdRequest(adRequest);;	        
        imAdView.loadNewAd(adRequest);
        */
	}
	public static void adsDestroy()
	{		
		if (iaBanner != null){
		    iaBanner .cleanUp();     
		}
		
	}
}
