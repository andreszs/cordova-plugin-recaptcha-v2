var RecaptchaProxy = {
  verify: function(successCallback, errorCallback, args) {
    var siteKeyWeb = '';
    if (args && typeof (args[0]) === 'object' && typeof (args[0].siteKeyWeb) === 'string' && args[0].siteKeyWeb.length > 0) {
      siteKeyWeb = args[0].siteKeyWeb;
    } else {
      errorCallback('args.siteKeyWeb is not set');
	  return;
    }

    function loadRecaptchaScript(callback) {
      if (typeof grecaptcha !== 'undefined') {
        callback();
        return;
      }

      var script = document.createElement('script');
      script.src = 'https://www.google.com/recaptcha/api.js';
      script.async = true;
      script.defer = true;
      script.onload = callback;
      script.onerror = function() {
        errorCallback('Failed to load reCAPTCHA script');
      };
      document.head.appendChild(script);
    }

    function waitForGrecaptcha(callback) {
      if (typeof grecaptcha !== 'undefined' && typeof grecaptcha.render === 'function') {
        callback();
      } else {
        setTimeout(function() {
          waitForGrecaptcha(callback);
        }, 1000);
      }
    }

    loadRecaptchaScript(function() {
      waitForGrecaptcha(function() {
        // Create a temporary container for the reCAPTCHA widget
        var tempContainer = document.createElement('div');
        document.body.appendChild(tempContainer);

        var widgetId = grecaptcha.render(tempContainer, {
          'sitekey': siteKeyWeb,
          'size': 'invisible',
          'callback': function(token) {
            if (typeof successCallback === 'function') {
              successCallback(token);
            }
            // Clean up the temporary container
            document.body.removeChild(tempContainer);
          },
          'error-callback': function(error) {
            if (typeof errorCallback === 'function') {
              errorCallback(error);
            }
            // Clean up the temporary container
            document.body.removeChild(tempContainer);
          }
        });

        // Execute the reCAPTCHA challenge
        grecaptcha.execute(widgetId);
      });
    });
  }
};

module.exports = RecaptchaProxy;

require('cordova/exec/proxy').add('Recaptcha', RecaptchaProxy);
