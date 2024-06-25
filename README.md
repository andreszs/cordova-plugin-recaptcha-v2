![npm](https://img.shields.io/npm/dt/cordova-plugin-recaptcha-v2) ![npm](https://img.shields.io/npm/v/cordova-plugin-recaptcha-v2) ![GitHub package.json version](https://img.shields.io/github/package-json/v/andreszs/cordova-plugin-recaptcha-v2?color=FF6D00&label=master&logo=github) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/andreszs/cordova-plugin-recaptcha-v2) ![GitHub top language](https://img.shields.io/github/languages/top/andreszs/cordova-plugin-recaptcha-v2) ![GitHub](https://img.shields.io/github/license/andreszs/cordova-plugin-recaptcha-v2) ![GitHub last commit](https://img.shields.io/github/last-commit/andreszs/cordova-plugin-recaptcha-v2)

# cordova-plugin-recaptcha-v2

Cordova plugin for integrating the [SafetyNet Recaptcha API](https://developer.android.com/privacy-and-security/safetynet/recaptcha "SafetyNet Recaptcha API") in your Android and Browser platforms.

# Platforms

- Android
- Browser

# Features

- Supports legacy reCAPTCHA v2 site keys for Android and Web

# Installation

### Plugin Versions

| Plugin version | Cordova | Cordova Android | minSdkVersion | SafetyNet |
| --- | --- | --- | --- | --- |
| 1.0.0 | >= 10.0.0 | >= 8.0.0 |  >= 19 | 18.0.1 |

### Install latest version from NPM

```bash
  cordova plugin add cordova-plugin-recaptcha-v2
```

### Install latest version with custom [Play Services SafetyNet](https://mvnrepository.com/artifact/com.google.android.gms/play-services-safetynet "Play Services SafetyNet") version

```bash
  cordova plugin add cordova-plugin-recaptcha-v2 --variable PLAY_SERVICES_SAFETYNET_VERSION=18.0.1
```

### Install latest version from master

```bash
  cordova plugin add https://github.com/andreszs/cordova-plugin-recaptcha-v2
```
⚠ Note that using Play Services SafetyNet 18.1.0 or newer will enforce a minSdk level of 21, while 18.0.1 still works with 19.

# Methods

## verify

To invoke the SafetyNet reCAPTCHA API, you call the `verify()` method. Usually, this method corresponds to the user's selecting a UI element, such as a button, in your activity.

```javascript
cordova.plugins.Recaptcha.verify(onSuccess, onFailure, [args])
```

| **args** | Object with `siteKeyAndroid` and optional `siteKeyWeb` strings |
| --- | --- |
| siteKeyAndroid | **String**: A *v2 Android* site key from the [v3 Admin Console](https://www.google.com/recaptcha/admin/ "v3 Admin Console"). Required for the Android platform. |
| siteKeyWeb | **String**: A *v2 Invisible* site key from the [v3 Admin Console](https://www.google.com/recaptcha/admin/ "v3 Admin Console"). Required only for the browser platform. |

⚠ As of 2024, you can no longer create new Android site keys from the legacy v3 Admin Console, but you can add your app's package name to existing v2 Android keys and reuse them in any app. This is because the SafetyNet Recaptcha API has been deprecated in favor of the Recaptcha Enterprise API. Note that Enterprise keys do not work with SafetyNet Recaptcha API.

### Return values

- **reCAPTCHA response token**

When the reCAPTCHA API executes the `onSuccess()` method, the user has successfully completed the CAPTCHA challenge. However, this method only indicates that the user has solved the CAPTCHA correctly. You still need to validate the user's response token from your backend server.

To learn how to validate the user's response token, see [Verifying the user's response](https://developers.google.com/recaptcha/docs/verify "Verifying the user's response").

### Example

 ```javascript
var onSuccess = function (strToken) {
    console.log(strToken);
};
var onFailure = function (strError) {
    console.warn(strError);
};
var args = {};
args.siteKeyAndroid = YOUR_V2_ANDROID_SITE_KEY;
args.siteKeyWeb = YOUR_V2_INVISIBLE_SITE_KEY;
cordova.plugins.Recaptcha.verify(onSuccess, onFailure, args);
```

# Plugin demo app

- [Compiled APK and reference](https://www.andreszsogon.com/cordova-recaptcha-v2-demo/)
- [Source code for www folder](https://github.com/andreszs/cordova-plugin-demos)

<img src="https://github.com/andreszs/cordova-plugin-demos/blob/main/com.andreszs.recaptcha.v2.demo/screenshots/recaptcha-v2-001.png?raw=true" width="200" /> <img src="https://github.com/andreszs/cordova-plugin-demos/blob/main/com.andreszs.recaptcha.v2.demo/screenshots/recaptcha-v2-002.png?raw=true" width="200" /> <img src="https://github.com/andreszs/cordova-plugin-demos/blob/main/com.andreszs.recaptcha.v2.demo/screenshots/recaptcha-v2-003.png?raw=true" width="200" /> <img src="https://github.com/andreszs/cordova-plugin-demos/blob/main/com.andreszs.recaptcha.v2.demo/screenshots/recaptcha-v2-004.png?raw=true" width="200" />

# Changelog

### 1.0.0

- First version
- Tested on Android 4.4 and up
- Tested on browser platform
