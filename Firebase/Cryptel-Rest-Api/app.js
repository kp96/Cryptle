var Firebase = require('firebase');
var request = require('request');
var ref = new Firebase('https://url hidden for obvious reasons.firebaseio.com');
var otp = {
  url : 'https://control.msg91.com/api/sendhttp.php',
  data : {
    authkey : 'key hidden for obvious reasons',
    sender : '*****',
    route : '4',
    country : '91',
    response : 'json'
  }
}
ref.child("voice").on('value', function(snapshot) {
	var data = snapshot.val();
  console.log(data);
	var mobiles = [data.two.number];
	var message = data.message;
	for(var mobile in mobiles) {
		sendMessage(mobiles[mobile], message, function(a,b,c){});
	}
  function sendMessage(mobile, message, callback) {
    var smsdata = JSON.parse(JSON.stringify(otp.data));
    smsdata.mobiles = mobile;
    smsdata.message = message;
    var str = Object.keys(smsdata).map(function (key) {
      return encodeURIComponent(key) + '=' + encodeURIComponent(smsdata[key]);
    }).join('&');
    var url = otp.url + '?' + str;
    request.get({url : url}, function(err, resp, html) {
      callback(err, resp, html);
    });
  }
});

