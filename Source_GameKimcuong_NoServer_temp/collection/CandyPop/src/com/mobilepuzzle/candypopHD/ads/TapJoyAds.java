package com.mobilepuzzle.candypopHD.ads;



import java.util.Hashtable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilepuzzle.candypopHD.CandyPop;
import com.tapjoy.TapjoyAwardPointsNotifier;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConstants;
import com.tapjoy.TapjoyDisplayAdNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyFullScreenAdNotifier;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.tapjoy.TapjoyViewNotifier;
import com.tapjoy.TapjoyViewType;


public class TapJoyAds 
{
	static TextView pointsTextView;
	TextView tapjoySDKVersionView;

	static String displayText = "";
	static boolean earnedPoints = false;
	
	// For the display ad.
	static View adView;
	RelativeLayout relativeLayout;
	LinearLayout adLinearLayout;
	
	public static final String TAG = "TAPJOY EASY APP";
	
	static public CandyPop gameLib;
	static public FrameLayout mainLayout;
	static public void showTapJoyAds(FrameLayout _mainLayout,CandyPop _gameLib )
	{		
		mainLayout = _mainLayout;
		gameLib =_gameLib;
		adView = new View(_gameLib);
		// Enables logging to the console.
		//TapjoyLog.enableLogging(true);
		
		// OPTIONAL: For custom startup flags.
		Hashtable<String, String> flags = new Hashtable<String, String>();
		flags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
		
		// Connect with the Tapjoy server.  Call this when the application first starts.
		// REPLACE THE APP ID WITH YOUR TAPJOY APP ID.
		// REPLACE THE SECRET KEY WITH YOUR SECRET KEY.
		String tapjoyAppID = "1b607a29-b122-486b-b1a5-ff202a6a14eb";
		String tapjoySecretKey = "JBzczMuUlJho2agSd5v2";
		// NOTE: This is the only step required if you're an advertiser.
		TapjoyConnect.requestTapjoyConnect(_gameLib.getApplicationContext(), tapjoyAppID, tapjoySecretKey, flags);

		// NOTE:  The get/spend/awardTapPoints methods will only work if your virtual currency
		// is managed by Tapjoy.
		//
		// For NON-MANAGED virtual currency, TapjoyConnect.getTapjoyConnectInsance().setUserID(...)
		// must be called after requestTapjoyConnect.

		// Get notifications whenever Tapjoy currency is earned.
		TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(new TapjoyEarnedPointsNotifier()
		{
			public void earnedTapPoints(int amount)
			{
				earnedPoints = true;
				updateTextInUI("You've just earned " + amount + " Tap Points!");
			}
		});
		
		// Get notifications when Tapjoy views open or close.
		TapjoyConnect.getTapjoyConnectInstance().setTapjoyViewNotifier(new TapjoyViewNotifier()
		{
			
			public void viewWillOpen(int viewType)
			{
				TapjoyLog.i(TAG, "viewWillOpen: " + getViewName(viewType));
			}
			
			
			public void viewWillClose(int viewType)
			{
				TapjoyLog.i(TAG, "viewWillClose: " + getViewName(viewType));
			}
			
			
			public void viewDidOpen(int viewType)
			{
				TapjoyLog.i(TAG, "viewDidOpen: " + getViewName(viewType));
			}
			
			
			public void viewDidClose(int viewType)
			{
				TapjoyLog.i(TAG, "viewDidClose: " + getViewName(viewType));
			}
		});
		// Show the display/banner ad.
		TapjoyConnect.getTapjoyConnectInstance().enableDisplayAdAutoRefresh(true);		
		TapjoyConnect.getTapjoyConnectInstance().getDisplayAd(gameLib, new TapjoyDisplayAdNotifier()
		{
			
			public void getDisplayAdResponseFailed(String error)
			{
				updateTextInUI("getDisplayAd error: " + error);
			}
			
			
			public void getDisplayAdResponse(View view)
			{
				
				// Using screen width, but substitute for the any width.
				// Scale the display ad to fit incase the width is smaller than the display ad width.
				mainLayout.removeView(adView);
				adView = scaleDisplayAd(view, 320);
				FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);

				if (gameLib.SCREEN_HEIGHT < 380) {
					adsParams.setMargins(-70, -20, -70, -20);
				}		
				
				mainLayout.addView(adView,adsParams);
			}
		});
		
		

	}
	
	
	
	protected void onResume()
	{
		// Re-enable auto-refresh when we regain focus.
		TapjoyConnect.getTapjoyConnectInstance().enableDisplayAdAutoRefresh(true);

		// Inform the SDK that the app has resumed.
		TapjoyConnect.getTapjoyConnectInstance().appResume();
	}
	
	
	protected void onPause()
	{
		// Disable banner ad auto-refresh when the screen loses focus.
		TapjoyConnect.getTapjoyConnectInstance().enableDisplayAdAutoRefresh(false);
		
		// Inform the SDK that the app has paused.
		TapjoyConnect.getTapjoyConnectInstance().appPause();
	}

	//================================================================================
	// Helper Methods
	//================================================================================
	/**
	 * Update the text view in the UI.
	 * @param text							Text to update the text view with.
	 */
	private static void updateTextInUI(final String text)
	{
		displayText = text;
		
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				if (pointsTextView != null)
					pointsTextView.setText(text);
			}
		});
	}
	
	private static void runOnUiThread(Runnable runnable) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Add the banner ad to our UI.
	 * @param view							Banner ad view.
	 */
	
	
	/**
	 * Scales a display ad view to fit within a specified width. Returns a resized (smaller) view if the display ad
	 * is larger than the width. This method does not modify the view if the banner is smaller than the width (does not resize larger).
	 * @param adView                                                Display Ad view to resize.
	 * @param targetWidth                                   Width of the parent view for the display ad.
	 * @return                                                              Resized display ad view.
	 */
	private static View scaleDisplayAd(View adView, int targetWidth)
	{
		int adWidth = adView.getLayoutParams().width;
		int adHeight = adView.getLayoutParams().height;

		// Scale if the ad view is too big for the parent view.
		if (adWidth > targetWidth)
		{
			int scale;
			int width = targetWidth;
			Double val = Double.valueOf(width) / Double.valueOf(adWidth);
			val = val * 100d;
			scale = val.intValue();

			((android.webkit.WebView) (adView)).getSettings().setSupportZoom(true);
			((android.webkit.WebView) (adView)).setPadding(0, 0, 0, 0);
			((android.webkit.WebView) (adView)).setVerticalScrollBarEnabled(false);
			((android.webkit.WebView) (adView)).setHorizontalScrollBarEnabled(false);
			((android.webkit.WebView) (adView)).setInitialScale(scale);

			// Resize banner to desired width and keep aspect ratio.
			LayoutParams layout = new LayoutParams(targetWidth, (targetWidth*adHeight)/adWidth);
			adView.setLayoutParams(layout);
		}

		return adView;
	}
	
	/**
	 * Helper method to get the name of each view type.
	 * @param type							Tapjoy view type from the view notification callbacks.
	 * @return								Name of the view.
	 */
	public static String getViewName(int type)
	{
		String name = "";
		switch (type)
		{
			case TapjoyViewType.DAILY_REWARD_AD:
				name = "daily reward ad";
				break;
			case TapjoyViewType.FULLSCREEN_AD:
				name = "fullscreen ad";
				break;
			case TapjoyViewType.OFFER_WALL_AD:
				name = "offer wall ad";
				break;
			case TapjoyViewType.VIDEO_AD:
				name = "video ad";
				break;
			default:
				name = "undefined type: " + type;
				break;
		}
		
		return name;
	}



	
}
