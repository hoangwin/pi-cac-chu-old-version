package com.mobilepuzzle.candypopHD.ads;

import android.R.string;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.inmobi.androidsdk.IMAdListener;
import com.inmobi.androidsdk.IMAdRequest;
import com.inmobi.androidsdk.IMAdRequest.ErrorCode;
import com.inmobi.androidsdk.IMAdView;
import com.mobilepuzzle.candypopHD.CandyPop;

public class InmobiAds {

	public static String LOG_TAG ="INMOBI :";
	public static String UID_INMOBI = 	"7fc9008af84e417795d186bac724064b";// cua game pet connect //chi mot cho
	public static IMAdView imAdView;
	public static void showInMobi(FrameLayout mainLayout,CandyPop _gameLib)
	{		
		imAdView = new IMAdView(_gameLib, IMAdView.INMOBI_AD_UNIT_320X50,UID_INMOBI);	
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);
		imAdView.disableHardwareAcceleration();
		imAdView.setLayoutParams(adsParams);//		
		mainLayout.addView(imAdView);
		IMAdRequest adRequest = new IMAdRequest();
		//adRequest.setTestMode(true);
		imAdView.setIMAdListener(mIMAdListener);
        imAdView.setIMAdRequest(adRequest);;	        
        imAdView.loadNewAd(adRequest);
	}
	
	
	
	private static IMAdListener mIMAdListener = new IMAdListener() {

		public void onShowAdScreen(IMAdView adView) {
			
			Log.i(LOG_TAG,
					"InMobiAdActivity-> onShowAdScreen, adView: " + adView);

		}

		public void onDismissAdScreen(IMAdView adView) {
			Log.i(LOG_TAG,
					"InMobiAdActivity-> onDismissAdScreen, adView: " + adView);
		}

		public void onAdRequestFailed(IMAdView adView, ErrorCode errorCode) {
			Log.i(LOG_TAG,
					"InMobiAdActivity-> onAdRequestFailed, adView: " + adView
							+ " ,errorCode: " + errorCode);
			//Toast.makeText(CandyPop.mainActivity,"Ad failed to load. Check the logcat for logs. Errorcode: "+ errorCode, Toast.LENGTH_SHORT).show();
		}

		public void onAdRequestCompleted(IMAdView adView) {
			Log.i(LOG_TAG,
					"InMobiAdActivity-> onAdRequestCompleted, adView: "
							+ adView);
		}

		public void onLeaveApplication(IMAdView adView) {
			Log.i(LOG_TAG,
					"InMobiAdActivity-> onLeaveApplication, adView: "
							+ adView);
		}
	};
}
	

	

