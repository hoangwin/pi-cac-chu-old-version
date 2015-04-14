/** Millennial Media Ad View Integration
 *
 * Please refer to comments and documentation provided in the code files for more information.
 *
 * BannerActivity.java - Demonstrates the minimum requirements to integrate an MMAdView into
 * your app. This sample creates an MMAdView in its XML layout file (banner_layout.xml) and one
 * programmatically.
 *
 * InterstitialActivity.java - Creates an MMAdView programatically for cached ads / interstitials and
 * adds meta data, listener callbacks, conversion tracking, and fetch/check/display interstitial capabilities.
 *
 * AdListener.java - A simple MMAdListener interface for ad callbacks.
 *
 * Below is a brief reference for the MMAdView constructor:
 *
 * public MMAdView(final Activity context, String apid, String adType, int refreshInterval, Hashtable<String, String> metaMap, boolean accelerate)
 *
 * **Required**:
 * 		- Context: The current activity to place the MMAdView in.
 * 		- APID: This number uniquely identifies your application and is given to you by Millennial Media.
 * 		- Ad Type: A string specifying which type of ad to use. See the documentation for more options.
 * 		- Refresh ads: refresh delay in seconds. Set to 0 for no refresh. Set to -1 for manual. Minimum is 30 seconds.
 * **Optional (See documentation for more info)**:
 * 		- Hashtable of meta values, e.g.: age, gender, marital status, zip code, income, latitude, longitude, width, height.
 * 		- Accelerometer disable: boolean variable to disable accelerometer ads if your app uses the accelerometer.
 *
 * See also: README.txt or http://wiki.millennialmedia.com/index.php/Android
 *
 */

package com.millennialmedia.android.sampleapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.millennialmedia.android.MMSDK;

public class SampleAppTabActivity extends TabActivity
{
	/** Declare your banner phone apid, given to you by Millennial Media */
	final static String BANNER_APID = "91528";
	/** Declare your interstitial phone apid, given to you by Millennial Media */
	final static String INTERSTITIAL_APID = "91776";
	/** Declare your interstitial tablet apid, given to you by Millennial Media */
	final static String INTERSTITIAL_TABLET_APID = "91785";

	/** Declare your GOALID, given to you by Millennial Media */
	final static String MY_GOAL_ID = "12345";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		TabHost tabHost;
		TabHost.TabSpec spec;
		Intent intent;

		MMSDK.setLogLevel(MMSDK.LOG_LEVEL_VERBOSE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tabHost = getTabHost();

		spec = tabHost.newTabSpec("Banner");
		spec.setIndicator("Banner", getResources().getDrawable(android.R.drawable.ic_menu_gallery));
		intent = new Intent().setClass(this, BannerActivity.class);
		spec.setContent(intent);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("BannerFromXml");
		spec.setIndicator("XML Banner", getResources().getDrawable(android.R.drawable.ic_menu_save));
		intent = new Intent().setClass(this, BannerXmlActivity.class);
		spec.setContent(intent);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("Interstitial");
		spec.setIndicator("Interstitial", getResources().getDrawable(android.R.drawable.ic_menu_save));
		intent = new Intent().setClass(this, InterstitialActivity.class);
		spec.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
}
