package com.millennialmedia.android.sampleapp;

import java.util.Map;

import android.os.Bundle;

import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMRequest;

/** <pre>Millennial Media Banner Ad View XML Integration
 *
 * In this file is code for integrating an MMAdView banner ad into your app via XML.
 *
 * Provided below is the corresponding Java code for integrating an MMAdView into your app using an XML layout. Please refer to banner_layout.xml for the associated layout code. In your layout you MUST specify apid and adType attributes for your
 * MMAdView.
 *
 * The code below demonstrates how you can get a reference to your MMAdView by using the findViewById method. It is highly recommended that you provide meta data such as the desired height and width of ads, keywords describing the context and
 * demographic data about your users.
 *
 * You can also implement a listener to provide callback methods about events such as ad success or ad failure.
 *
 * For a complete list of XML attributes, meta data keys, and listener methods please see consult the Android SDK wiki reference: http://wiki.millennialmedia.com/index.php/Android</pre> */

public class BannerXmlActivity extends MMBannerActivity
{
	private MMAdView adViewFromXml;
	RefreshHandler handler;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// The sample app will load the correct layout based on the screen size and orientation. The layouts can be found
		// under the res folder in the project.
		setContentView(R.layout.banner_from_xml_layout);

		// Find the ad view for reference
		adViewFromXml = (MMAdView) findViewById(R.id.adView);

		// (Optional/Recommended) Set meta data (will be applied to subsequent ad requests)
		Map<String, String> metaData = createMetaData();

		MMRequest request = new MMRequest();
		request.setMetaValues(metaData);
		adViewFromXml.setMMRequest(request);

		// (Optional) Set an event listener.
		// See AdListener.java for a basic implementation
		adViewFromXml.setListener(new AdListener());

		adViewFromXml.getAd();

		// (Optional) Setup an automatic refresh timer
		handler = new RefreshHandler(adViewFromXml);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if(handler != null)
			handler.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		if(handler != null)
			handler.onPause();
	}
}
