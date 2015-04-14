package com.mobilepuzzle.candypopHD.ads;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import com.adsdk.sdk.banner.AdView;
import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMException;
import com.millennialmedia.android.MMInterstitial;
import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMBroadcastReceiver;
import com.millennialmedia.android.MMRequest;
import com.millennialmedia.android.MMSDK;
import com.millennialmedia.android.RequestListener;
import com.millennialmedia.android.RequestListener.RequestListenerImpl;


import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mobilepuzzle.candypopHD.CandyPop;

public class MillennialAds {
	public static CandyPop gameLib;
	public static FrameLayout mainLayout;
	
	public static RefreshHandler handler;	
	public static MMAdView adView;
	
	public static void showMillenialAds(FrameLayout _mainLayout,CandyPop _gameLib)
	{
		gameLib =_gameLib;
		mainLayout = _mainLayout;
		adView = new MMAdView(_gameLib);
		handler = new RefreshHandler(adView);
		// Set your apid
		adView.setApid( "131916");

		// (Highly Recommended) Set the id to preserve your ad on configuration changes. Save Battery!
		// Each MMAdView you give requires a unique id.
		adView.setId(MMSDK.getDefaultAdId());

		int placementWidth = 320;
		int placementHeight = 50;			
		adView.setWidth(placementWidth);
		adView.setHeight(placementHeight);
		
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);

		if (_gameLib.SCREEN_HEIGHT < 380) {
			adsParams.setMargins(-70, -20, -70, -20);
		}
		mainLayout.addView(adView,adsParams);		
		// Setup the request for ad button		

		// (Optional/Recommended) Set meta data (will be applied to subsequent ad requests)
		//Map<String, String> metaData = createMetaData();
		adView.setListener(new com.mobilepuzzle.candypopHD.ads.AdListener());
		MMRequest request = new MMRequest();
		//request.setMetaValues(metaData);
		adView.setMMRequest(request);
	
		//khogn can cai nay vi no da tu dong refest o MainAcivity.onresume() va onPause
		//adView.getAd();
		
		
	}
	public static void onResume()
	{
		handler.onResume();	
	}
	public static void onPause()
	{
		handler.onPause();	
	}
}
class RefreshHandler extends Handler
{
	private static final long TIME_TO_REFRESH_IN_MILLIS = 60000;
	private static final int MSG_REFRESH_BANNER = 4;
	private WeakReference<MMAdView> mmAdViewRef;

	public RefreshHandler(MMAdView adView)
	{
		mmAdViewRef = new WeakReference<MMAdView>(adView);
	}

	@Override
	public void handleMessage(android.os.Message msg)
	{
		switch(msg.what)
		{
		case MSG_REFRESH_BANNER:
			if(mmAdViewRef != null)
			{
				MMAdView adView = mmAdViewRef.get();
				if(adView != null)
				{
					adView.getAd();
					sendEmptyMessageDelayed(MSG_REFRESH_BANNER, TIME_TO_REFRESH_IN_MILLIS);
				}
			}
			break;
		}
	};

	public void onPause()
	{
		removeMessages(MSG_REFRESH_BANNER);
	}

	public void onResume()
	{
		sendEmptyMessage(MSG_REFRESH_BANNER);
	}
}
class AdListener implements RequestListener
{
	public void MMAdOverlayLaunched(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") overlay launched");
	}

	public void MMAdRequestIsCaching(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") caching started");
	}

	public void requestCompleted(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG,"Millennial Media Ad (" + mmAd.getApid() + ") request succeeded");
	}

	public void requestFailed(MMAd mmAd, MMException exception)
	{
		if(MillennialAds.adView!= null)
		MillennialAds.adView = null;
		
		AdsManager.showAds(MillennialAds.mainLayout, MillennialAds.gameLib);
		Log.i(MMSDK.SDKLOG,String.format("Millennial Media Ad (" + mmAd.getApid() + ") request failed with error: %d %s.", exception.getCode(), exception.getMessage()));
	}

	public void MMAdOverlayClosed(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") overlay closed");

	}
	public void onSingleTap(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") single tap");
	}
}