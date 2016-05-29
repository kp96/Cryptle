var UI = require('ui');
var Vibe = require('ui/vibe');
var Accel = require('ui/accel');
var Voice = require('ui/voice');
require('firebase')
Accel.init();
var ref = new Firebase('url hidden for obv reasons');
function showMenu() {
  // We create a simple menu with a few options
  var menu = new UI.Menu({
    sections: [{
      items: [{
        title: 'Personal Security',
        subtitle: 'Alert your loved ones.'
      },
      {
        title: 'Home Security',
        subtitle: 'Secure your devices.'
      },
      {
        title: 'Voice Help',
        subtitle: 'Speak to receive help'
      }]
    }]
  });
  menu.on('accelTap', function(e) {
    console.log('Accel detected');
    var options = {
      enableHighAccuracy : false,
      maximumAge : 10000,
      timeout : 30000
    };
    navigator.geolocation.getCurrentPosition(success, error, options);
    function success(pos) {
      console.log('success');
      console.log('lat=' + pos.coords.latitude + 'lon=' + pos.coords.longitude);
      ref.child("personal").child("1").update({"emergency" : true, "gps" : pos.coords.latitude + " " + pos.coords.longitude});
    }
    function error(err) {
      console.log('err occured');
      console.log(err);
    }
  });
  // When the user selects an option...
  menu.on('select', function(e) {
    // We use the title to perform operations
    switch (e.item.title) {
      case 'Home Security':
      	showSecurityView();
        break;
      case 'Personal Security':
        showPersonalView();
      	break;
      case 'Voice Help':
        showVoiceView();
        break;
    }
  });

  // Finally make sure the menu is set to show.
  menu.show();
}
var card = new UI.Card({
  title : 'Loading', 
  subtitle : 'Just a sec'
});
var personalCard = new UI.Card({
  title : 'SOS',
  subtitle : 'Click to raise alert!'
});
function showPersonalView() {
  personalCard.show();
  personalCard.on('click', 'select', function() {
    console.log('here');
    var options = {
      enableHighAccuracy : false,
      maximumAge : 10000,
      timeout : 30000
    };
    navigator.geolocation.getCurrentPosition(success, error, options);
    function success(pos) {
      console.log('success');
      console.log('lat=' + pos.coords.latitude + 'lon=' + pos.coords.longitude);
      ref.child("personal").child("1").update({"emergency" : true, "gps" : pos.coords.latitude + " " + pos.coords.longitude});
    }
    function error(err) {
      console.log('err occured');
      console.log(err);
    }
  });
  personalCard.on('click', 'down', function() {
    ref.child("personal").child("1").update({"emergency" : false});
  });
}
function showSecurityView() {
  card.show();
	ref.child("home").child("1").on('value', function(data) {
		  updateCard(data.val().intruder);
	}, function(error) {
      console.log('error');
      console.log(error);
  });
}
function updateCard(val) {
  card.title("Device ID : 1");
  if(val == true) {
    card.subtitle("Intruder Detected! Press select to raise alarm");
    Vibe.vibrate('short');
    card.on('click', 'select', function() {
      ref.child("home").child("1").update({"alert" : true});
    });
  } else {
    ref.child("home").child("1").update({"alert" : false});
    card.subtitle("Your device is safe.");
  }
}
var voiceCard = new UI.Card({
  title : 'Click to send',
  subtitle : ''
});
function showVoiceView() {
  voiceCard.show();
  Voice.dictate('start', false, function(e) {
  if (e.err) {
    console.log('Error: ' + e.err);
    return;
  }
  var message = e.transcription;
  voiceCard.subtitle('Message: ' + e.transcription);
  voiceCard.on('click', 'select', function() {
    ref.child("voice").update({"message" : message});
  });
});
}
showMenu();
