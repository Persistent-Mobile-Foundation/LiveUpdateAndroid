# LiveUpdateAndroid
Application that demonstrates use of Live Update feature

Hello Live Update
In the sample application, User can roll out the feature and its properties remotely using Liveupdate Feature of Mobile Foundation

Tutorials
https://pmf.persistentproducts.com/tutorials/en/foundation/9.0/application-development/live-update-service/


Usage
From a command-line window, navigate to the project's root folder and run the following commands:

pmfdev app register - to register the application in the MobileFoundation Server.
pmfdev app push - to add the liveupdate.mobileclient scope in scope elements mappings section of security.
Add Liveupdate Features & Properties as shown below in Mobile Foundation Operations Console → [your application] → Liveupdate Settings → Schema.

Liveupdate Screenshot

In Android Studio, run the app in the Android Emulator or a physical device.
Changing Live Update Settings
In Mobile Foundation Operations Console → [your application] → Live Update Settings → Schema tab

Feature Rollout
click on the Edit icon of festivalShopping feature under Features section and Change the default value to On or Off* to enable/disable the feature remotely.

Feature Properties
Click the Edit icon of buttonLabel under Properties section and change the value to update the feature properties remotely.
Supported Versions
PSL Mobile Foundation 9.0

