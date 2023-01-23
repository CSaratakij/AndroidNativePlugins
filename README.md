# Android Native Plugins

This is Android Studio project that contains modules for Android Native Plugins. Created by Thanut Panichyotai (@[LuviKunG](https://github.com/LuviKunG)).

This work also developed under the policy of the company **[Miimo AI Co.,Ltd.](https://miimo.ai/)** and using in internal application.

## How to use

- Install latest version of **Android Studio**.
- Open this Android Project.
- Select module **Native Android** (com.luvikung.nativeandroid) in Android Studio.
- Select 'Build' > 'Make Module' to compile the module into an *\*.aar* file.
	- If the build menu are disabled, you must select the module before perform build the module.
- Navigate into the project's folder and redirect into *nativeandroid\build\outputs\aar*.
- Copy *\*.aar* file into your Unity project asset folder.
	- This file must be in under special folders of *Plugins/Android*.
- Copy or import *NativeAndroid.cs* file into your Unity project.
- Call the static function in `NativeAndroid` class to use this native android plugins.

## What's this Android module can do?

- Create dynamic alert box.
- Wake lock the device (Required minimal API of 23 (Android M)).
- Pick date.
- Pick time. (Both AM/PM and 24Hrs format)
- Show toast message.
- Open Android App Settings.
- Open Google Play Accounts. (include subscriptions)