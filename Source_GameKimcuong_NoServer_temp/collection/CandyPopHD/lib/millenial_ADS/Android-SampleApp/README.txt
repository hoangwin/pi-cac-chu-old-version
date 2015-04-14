Millennial Media Inc.
Android SDK Sample App
5/21/13

This sample app demonstrates how to integrate the Millennial Media Android SDK into a simple app.

Compiling and Running the App
-------------------------------------

1. Copy the MMSDK.jar file into the libs subdirectory of the SampleApp.

2. If you are using Eclipse do the following:
	a. Copy the SampleApp project directory into your workspace directory.
	b. The SampleApp should automatically build.
		If you run into problems check the following:
		- Make sure your Android SDK is setup. Check to see if the path is set under Window->Preferences->Android.
		- Try fixing the project by right clicking on SampleApp in the Project Explorer and then selecting Android Tools->Fix Project Properties
	c. Right click on SampleApp in the Project Explorer and choose Run As->Android Application.

3. If you are using the command line instead do the following:
	a. Change your current directory to SampleApp.
	b. Create the local.properties build file by running "android update project -p ."
	c. Compile the app and install it by running "ant debug install"

4. Find the SampleApp app on your emulator or device and run it!

