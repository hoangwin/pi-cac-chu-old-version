package com.mobilepuzzle.candypopHD.ads;

//import com.apperhand.device.android.AndroidSDKProvider;

import com.mobilepuzzle.candypopHD.CandyPop;
import com.mobilepuzzle.candypopHD.GameLib;
import com.inneractive.api.ads.InneractiveAd;
import com.inneractive.api.ads.InneractiveAdListener;

import android.content.Context;
import android.widget.FrameLayout;

public class InnerActiveAds implements InneractiveAdListener {	
	
	public static Context context;
	public static CandyPop gameLib;
	public static FrameLayout mainLayout;
	public static InnerActiveAds instance;	
	
	public static String UID_INNER_ACTIVE_STRING = "LavaGame_CandyPopHD_Android";// NAX
	public static InneractiveAd iaBanner;
	
	public InnerActiveAds()
	{
		
	}	
	
	public static void showInnerActive(FrameLayout _mainLayout,CandyPop _gameLib)
	{				
			gameLib =_gameLib;
			mainLayout = _mainLayout;
			FrameLayout layout1 = new FrameLayout(_gameLib);
			FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);			
			instance = new InnerActiveAds();
			iaBanner = new InneractiveAd(_gameLib, UID_INNER_ACTIVE_STRING, InneractiveAd.IaAdType.Banner, 60 );
			iaBanner.setInneractiveListener(instance);
			if (_gameLib.SCREEN_HEIGHT < 380) {
				adsParams.setMargins(-70, -20, -70, -20);
			}
			mainLayout.addView(iaBanner,adsParams);				
		
	}
	
	public void onIaAdClicked() {
		// TODO Auto-generated method stub
		
	}



	public void onIaAdExpand() {
		// TODO Auto-generated method stub
		
	}



	public void onIaAdExpandClosed() {
		// TODO Auto-generated method stub
		
	}



	public void onIaAdFailed() {
		// TODO Auto-generated method stub
		adsDestroy();
		AdsManager.showAds(mainLayout, gameLib);
		
	}



	public void onIaAdReceived() {
		// TODO Auto-generated method stub
		int i =0;
		i++;
	}



	public void onIaAdResize() {
		// TODO Auto-generated method stub
		
	}



	public void onIaAdResizeClosed() {
		// TODO Auto-generated method stub
		
	}



	public void onIaDefaultAdReceived() {
		// TODO Auto-generated method stub
		
	}



	public void onIaDismissScreen() {
		// TODO Auto-generated method stub
		
	}
	public static void adsDestroy()
	{
		if (iaBanner != null){
		    iaBanner .cleanUp();     
		}
		
	}


}
