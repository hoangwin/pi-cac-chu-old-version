/** Millennial Media Ad View Integration - Additional Features
 *
 * Included in this example is an optional implementation of the MMAdListener methods and
 * how to show static interstitials using the 4.5+ methods fetch/check/display.
 *
 * See also: README.txt or http://wiki.millennialmedia.com/index.php/Android
 *
 */

package com.millennialmedia.android.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMException;
import com.millennialmedia.android.MMInterstitial;
import com.millennialmedia.android.MMSDK;
import com.millennialmedia.android.RequestListener;

public class InterstitialActivity extends Activity implements RequestListener
{
	// The ad view object
	private MMInterstitial fetchAvailableDisplayInterstitial;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interstitial_layout);

		/**** Millennial Media Interstitial Ad Integration ****/

		// Create the adView
		fetchAvailableDisplayInterstitial = new MMInterstitial(this);

		// Set your apid
		fetchAvailableDisplayInterstitial.setApid(SampleAppTabActivity.INTERSTITIAL_APID);

		Button button = (Button) findViewById(R.id.fetchButton);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				String message = "Fetching cached ad...";
				Log.i(MMSDK.SDKLOG, "Fetch - " + message);
				Toast toast = Toast.makeText(InterstitialActivity.this, message, Toast.LENGTH_SHORT);
				toast.show();
				fetchAvailableDisplayInterstitial.fetch();
			}
		});

		button = (Button) findViewById(R.id.checkButton);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				boolean isAdAvailable = fetchAvailableDisplayInterstitial.isAdAvailable();
				String message = isAdAvailable ? "Cached ad is ready to view." : "Cached ad is not ready to view.";
				Log.i(MMSDK.SDKLOG, "Check - " + message);
				Toast toast = Toast.makeText(InterstitialActivity.this, message, Toast.LENGTH_SHORT);
				toast.show();
			}
		});

		button = (Button) findViewById(R.id.displayButton);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				boolean displayed = fetchAvailableDisplayInterstitial.display();
				String message = "Cached ad is now displaying...";
				if(!displayed)
				{
					message = "Cached ad is not ready to view.";
					Toast toast = Toast.makeText(InterstitialActivity.this, message, Toast.LENGTH_SHORT);
					toast.show();
				}
				Log.i(MMSDK.SDKLOG, "Display - " + message);
			}
		});
	}

	/******** (Optional) Millennial Media Ad View Listener Integration ********/
	@Override
	public void MMAdOverlayLaunched(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") Overlay Launched");
	}

	@Override
	public void MMAdRequestIsCaching(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") caching started");

		Toast toast = Toast.makeText(this, "Caching started.", Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public void requestCompleted(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") caching completed successfully.");

		Toast toast = Toast.makeText(this, "Finished caching.", Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public void requestFailed(MMAd mmAd, MMException exception)
	{
		Log.i(MMSDK.SDKLOG,String.format("Millennial Media Ad (" + mmAd.getApid() + ") fetch request failed with error: %d %s.", exception.getCode(), exception.getMessage()));

		Toast toast = Toast.makeText(this, String.format("Fetch request failed with error: %d %s.", exception.getCode(), exception.getMessage()), Toast.LENGTH_SHORT);
		toast.show();
	}

	/* Use this if you wish to be notified that an advertisement was closed */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
		case 0:
			if(resultCode == RESULT_CANCELED)
			{
				Log.i(MMSDK.SDKLOG, "Millennial Ad Overlay Closed");
			}
		}
	}

	@Override
	public void MMAdOverlayClosed(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") Overlay closed");
	}

	@Override
	public void onSingleTap(MMAd mmAd)
	{
		Log.i(MMSDK.SDKLOG, "Millennial Media Ad (" + mmAd.getApid() + ") single tap");
	}
}
