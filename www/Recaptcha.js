"use strict";
var exec = require('cordova/exec');

var Recaptcha = {
  verify: function(successCallback, errorCallback, args) {
    exec(successCallback, errorCallback, "Recaptcha", "verify", [args]);
  }
};

module.exports = Recaptcha;
