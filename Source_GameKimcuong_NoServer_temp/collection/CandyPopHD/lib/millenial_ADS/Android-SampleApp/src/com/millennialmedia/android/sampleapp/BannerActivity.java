package com.millennialmedia.android.sampleapp;

import java.util.Map;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMRequest;
import com.millennialmedia.android.MMSDK;

/** <pre>Millennial Media Banner Ad View Integration
 *
 * In this file is code for integrating an MMAdView banner ad into your app programatically.
 *
 * After the MMAdView is instantiated it is added to the XML layout. For convenience it is added to the RelativeLayout adBannerRelativeLayout aligned at the bottom. See SampleAppTabActivity.java for more about instantiating a MMAdView.
 *
 * In this example, the MMAdView has its refresh interval disabled and is only manually refreshed using the callForAd method. While using the app press the Refresh button to perform a callForAd. This example also demonstrates a more thorough example
 * of how to provide meta data and how to use conversion tracking.
 *
 * You can implement a listener to provide callback methods about events such as ad success or ad failure.
 *
 * For a complete list of XML attributes, meta data keys, and listener methods please see consult the Android SDK wiki reference: http://wiki.millennialmedia.com/index.php/Android</pre> */

public class BannerActivity extends MMBannerActivity
{
	private static final String TAG = BannerActivity.class.getName();

	// Constants for tablet sized ads (728x90)
	private static final int IAB_LEADERBOARD_WIDTH = 728;
	private static final int IAB_LEADERBOARD_HEIGHT = 90;

	private static final int MED_BANNER_WIDTH = 480;
	private static final int MED_BANNER_HEIGHT = 60;

	// Constants for phone sized ads (320x50)
	private static final int BANNER_AD_WIDTH = 320;
	private static final int BANNER_AD_HEIGHT = 50;

	private MMAdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.banner_layout);

		/******** Millennial Media Ad View Integration ********/

		// Create the adView
		adView = new MMAdView(this);

		// Set your apid
		adView.setApid(SampleAppTabActivity.BANNER_APID);

		// (Highly Recommended) Set the id to preserve your ad on configuration changes. Save Battery!
		// Each MMAdView you give requires a unique id.
		adView.setId(MMSDK.getDefaultAdId());

		int placementWidth = BANNER_AD_WIDTH;
		int placementHeight = BANNER_AD_HEIGHT;

		// (Optional) Set the ad size
		if(canFit(IAB_LEADERBOARD_WIDTH))
		{
			placementWidth = IAB_LEADERBOARD_WIDTH;
			placementHeight = IAB_LEADERBOARD_HEIGHT;
		}
		else if(canFit(MED_BANNER_WIDTH))
		{
			placementWidth = MED_BANNER_WIDTH;
			placementHeight = MED_BANNER_HEIGHT;
		}

		// (Optional) Set the AdView size based on the placement size. You could use WRAP_CONTENT and not specify the placement size
		adView.setWidth(placementWidth);
		adView.setHeight(placementHeight);

		int layoutWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, placementWidth, getResources().getDisplayMetrics());
		int layoutHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, placementHeight, getResources().getDisplayMetrics());

		// Add the adview to the view layout
		RelativeLayout adRelativeLayout = (RelativeLayout) findViewById(R.id.adBannerRelativeLayout);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(layoutWidth, layoutHeight);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		adRelativeLayout.addView(adView, layoutParams);

		// Setup the request for ad button
		Button button = (Button) findViewById(R.id.refreshButton);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				adView.getAd();
				String message = "Refreshing MMAdView (" + adView.getId() + ")...";
				Toast toast = Toast.makeText(BannerActivity.this, message, Toast.LENGTH_SHORT);
				toast.show();
				Log.i(TAG, message);
			}
		});

		// (Optional/Recommended) Set meta data (will be applied to subsequent ad requests)
		Map<String, String> metaData = createMetaData();

		MMRequest request = new MMRequest();
		request.setMetaValues(metaData);
		adView.setMMRequest(request);

		// (Optional) Set the listener to receive events about the adview
		adView.setListener(new AdListener());

		// (Optional) Start conversion tracking
		MMSDK.trackConversion(this, SampleAppTabActivity.MY_GOAL_ID);
	}

	// Determine if the requested adWidth can fit on the screen.
	protected boolean canFit(int adWidth)
	{
		int adWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, adWidth, getResources().getDisplayMetrics());
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		return metrics.widthPixels >= adWidthPx;
	}
}
