var five = require("johnny-five");
var Firebase = require("firebase");
var board = new five.Board();
var controller = process.argv[2] || "GP2Y0A02YK0F";

board.on("ready", function() {
  var proximity1 = new five.IR.Proximity({
    controller: controller,
    pin: "A0"
  });

  var lcd = new five.LCD({
    pins: [7, 8, 9, 10, 11, 12],
    backlight: 6
  });
  lcd.useChar("heart");
  lcd.print("No Emergencies :heart:");

  var led = new five.Led(3);
  var piezo = new five.Piezo(5);

  var myHomeRef = new Firebase("https://url hidden for obvious reasons.firebaseio.com/home/");
  var myPersonalRef = new Firebase("https://url hidden for obvious reasons/personal/");

  proximity1.on("data", function() {
    console.log("Intruder around ", this.cm+"cms");
    if (this.cm < 50) {
      led.off();
      myHomeRef.child(1).update({
        intruder: false
      });
    }
    else{
      myHomeRef.child(1).update({
        intruder: true
      });
      led.on();
    }
  });

myPersonalRef.on("child_added", function(snapshot) {
  var pState = snapshot.val();
  console.log(pState);
  if(pState.emergency){
    lcd.clear().blink().cursor(0, 0).print("Help Me !!!");
    lcd.cursor(1, 0).print(pState.gps);
  }
  else{
    lcd.clear().print("No Emergencies :heart: ");
  }
  });
  myPersonalRef.on("child_changed", function(snapshot) {
  var pState = snapshot.val();
  console.log(pState);
  if(pState.emergency){
    lcd.clear().blink().cursor(0, 0).print("Help Me !!!");
    lcd.cursor(1, 0).print(pState.gps);
  }
  else{
    lcd.clear().print("No Emergencies :heart: ");
  }
  });

myHomeRef.on("child_added", function(snapshot) {
  var homeState = snapshot.val();
  console.log(homeState);
  if(homeState.alert){
    piezo.play({
      song: [
        ["C4", 1 / 2],
        ["F4", 1 / 4],
        ["D4", 1 / 4]
      ],
      tempo: 160
    });
  }
  else{
    piezo.off();
  }
  });
  myHomeRef.on("child_changed", function(snapshot) {
    var homeState = snapshot.val();
    console.log(homeState);
    if(homeState.alert){
      piezo.play({
        song: [
          ["C4", 1 / 2],
          ["F4", 1 / 4],
          ["D4", 1 / 4]
        ],
        tempo: 160
      });
    }
    else{
      piezo.off();
  }
  });
});