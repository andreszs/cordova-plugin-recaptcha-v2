package com.andreszs.recaptcha;

import java.util.concurrent.Executor;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Cordova Recaptcha plugin to verify Google Recaptcha on android.
 */
public class Recaptcha extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("verify")) {
			try {
				JSONObject arg_object = args.getJSONObject(0);
				String siteKey = arg_object.getString("siteKeyAndroid");
				this.verify(siteKey, callbackContext);
			} catch (JSONException e) {
				callbackContext.error("Verify called without providing siteKeyAndroid");
			}
			return true;
		}
		return false;
	}

	private void verify(String siteKey, CallbackContext callbackContext) {
		final CallbackContext finalCallbackContext = callbackContext; // Declare callbackContext as final

		SafetyNet.getClient(cordova.getActivity())
				.verifyWithRecaptcha(siteKey)
				.addOnSuccessListener(cordova.getActivity(),
						new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
					@Override
					public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
						String userResponseToken = response.getTokenResult();
						if (!userResponseToken.isEmpty()) {
							finalCallbackContext.success(userResponseToken); // Use finalCallbackContext
						} else {
							finalCallbackContext.error("Response token was empty"); // Use finalCallbackContext
						}
					}
				})
				.addOnFailureListener(cordova.getActivity(),
						new OnFailureListener() {
					@Override
					public void onFailure(Exception e) {
						if (e instanceof ApiException) {
							// An error we know about occurred.
							ApiException apiException = (ApiException) e;
							int statusCode = apiException.getStatusCode();
							String message = getSafetyNetStatusCodeString(statusCode);
							finalCallbackContext.error(message);
						} else {
							// A different, unknown type of error occurred.
							finalCallbackContext.error(e.getMessage());
						}
					}
				}
				);
	}

	private String getSafetyNetStatusCodeString(int statusCode) {
		String message;
		switch (statusCode) {
			case SafetyNetStatusCodes.SAFE_BROWSING_UNSUPPORTED_THREAT_TYPES:
				message = "reCAPTCHA error: Unsupported threat types.";
				break;
			case SafetyNetStatusCodes.SAFE_BROWSING_MISSING_API_KEY:
				message = "reCAPTCHA error: Missing API key.";
				break;
			case SafetyNetStatusCodes.SAFE_BROWSING_API_NOT_AVAILABLE:
				message = "reCAPTCHA error: Safe Browsing API not available.";
				break;
			case SafetyNetStatusCodes.VERIFY_APPS_NOT_AVAILABLE:
				message = "reCAPTCHA error: Verify Apps not available.";
				break;
			case SafetyNetStatusCodes.VERIFY_APPS_INTERNAL_ERROR:
				message = "reCAPTCHA error: Verify Apps internal error.";
				break;
			case SafetyNetStatusCodes.VERIFY_APPS_NOT_ENABLED:
				message = "reCAPTCHA error: Verify Apps not enabled.";
				break;
			case SafetyNetStatusCodes.UNSUPPORTED_SDK_VERSION:
				message = "reCAPTCHA error: Unsupported SDK version.";
				break;
			case SafetyNetStatusCodes.RECAPTCHA_INVALID_SITEKEY:
				message = "reCAPTCHA error: Invalid site key.";
				break;
			case SafetyNetStatusCodes.RECAPTCHA_INVALID_KEYTYPE:
				message = "reCAPTCHA error: Invalid key type.";
				break;
			case SafetyNetStatusCodes.SAFE_BROWSING_API_NOT_INITIALIZED:
				message = "reCAPTCHA error: Safe Browsing API not initialized.";
				break;
			case SafetyNetStatusCodes.OS_VERSION_NOT_SUPPORTED:
				message = "reCAPTCHA error: OS version not supported.";
				break;
			case SafetyNetStatusCodes.INVALID_ADMIN_APPLICATION:
				message = "reCAPTCHA error: Invalid admin application.";
				break;
			case SafetyNetStatusCodes.RECAPTCHA_INVALID_PACKAGE_NAME:
				message = "reCAPTCHA error: Invalid package name.";
				break;
			default:
				message = "reCAPTCHA error: " + CommonStatusCodes.getStatusCodeString(statusCode);
				break;
		}
		return message;
	}
}
