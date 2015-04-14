package com.mobilepuzzle.candypopHD.ads;

//import com.apperhand.device.android.AndroidSDKProvider;
import java.util.Random;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import com.mobilepuzzle.candypopHD.CandyPop;
import com.mobilepuzzle.candypopHD.GameLib;

import android.content.Context;
import android.widget.FrameLayout;


public class AdsManager {
	
	private static AdView adView;
	public static Context context;
	public static GameLib gameLib;
	public static AdsManager instance;	
	public static int CURRENT_ID_ADS = -1;
	public static int countShowAddID = 0;
	public AdsManager()
	{
		
	}
	
	public static void showStartApp(CandyPop _gameLib)//not full
	{		
			//AndroidSDKProvider.setTestMode(true);
			//AndroidSDKProvider.initSDK(_gameLib.context);			
			//AdPreferences adPreferences = new AdPreferences("106420618", "206410428", AdPreferences.TYPE_INAPP_EXIT);
			//htmlAd = new HtmlAd(this);
			//htmlAd.load(adPreferences);
		
	}
	
	
	
	
	public static void showAds(FrameLayout mainLayout,CandyPop _gameLib ) {
		//can chu y 3 bang tsau se load lai neu faild
		// admob
		//inner-active
		//Millennial
		Random r = new Random();
		CURRENT_ID_ADS = r.nextInt(5);		
		//TapJoyAds.showTapJoyAds(layout, this);		
		countShowAddID++;
		if(countShowAddID > 2)
			return;
		
		switch(CURRENT_ID_ADS)
		{ 
		case 0:
			AdsMobFox.showMobfoxAds(mainLayout, _gameLib);
			break;
		case 1:
			AdmobAds.showAdmobAds(mainLayout, _gameLib);
			break;
		case 2:
			InmobiAds.showInMobi(mainLayout, _gameLib);
			break;
		case 3:
			InnerActiveAds.showInnerActive(mainLayout, _gameLib);
			break;
		case 4:
			MillennialAds.showMillenialAds(mainLayout, _gameLib);
			break;
		default:
			AdmobAds.showAdmobAds(mainLayout, _gameLib);
			break;
		}
	}
	public static void adsDestroy()
	{
		switch(CURRENT_ID_ADS)
		{ 
		case 0:
			AdsMobFox.destroy();
			break;
		case 1://admob
			AdmobAds.adsDestroy();	
			break;
		case 2://inmobi
			//InmobiAds.d
			break;
		case 3://inneractive
			InnerActiveAds.adsDestroy();
			break;
		case 4:
			//MillennialAds.
			break;
		default:		
			break;
		}
		
	}
	public static void onPause()
	{
		switch(CURRENT_ID_ADS)
		{ 
		case 0://Mobfox
			//AdsMobFox.destroy();
			break;
		case 1://admob
			//if(adView!= null)
			//	adView.destroy();	
			break;
		case 2://inmobi
			//InmobiAds.d
			break;
		case 3://inner-active
			//InnerActiveAds.adsDestroy();
			break;
		case 4://millennial
			if(MillennialAds.handler != null)
				MillennialAds.onPause();
			break;
		default:		
			break;
		}
		
	}
	public static void onResume()
	{
		switch(CURRENT_ID_ADS)
		{ 
		case 0:
			//AdsMobFox.destroy();
			break;
		case 1://admob
			//if(adView!= null)
			//	adView.destroy();	
			break;
		case 2:
			//InmobiAds.d
			break;
		case 3:
			//InnerActiveAds.adsDestroy();
			break;
		case 4:
			if(MillennialAds.handler != null)
				MillennialAds.onResume();
			break;
		default:		
			break;
		}
		
	}
}

