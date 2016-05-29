package com.example.welcome.cryptle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageDisplay extends AppCompatActivity implements View.OnClickListener{
private EditText messagetosend;
    private String send;
    private Button sendbtn;
    private static final int KEY_BUTTON_UP = 0;
    private static final int KEY_BUTTON_DOWN = 1;
    private static final UUID APP_UUID = UUID.fromString("79df3e79-880b-423c-abc3-464f6a388b2b");
    private PebbleKit.PebbleDataReceiver mDataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpannableString s = new SpannableString("Send and Receive notifications");
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),"amaranth.regular.ttf");
        s.setSpan(new CustomTypefaceSpan("",typeface1), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);
        setContentView(R.layout.activity_message_display);
        messagetosend = (EditText)findViewById(R.id.texttosend);
        sendbtn = (Button)findViewById(R.id.sendmessage);
        sendbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sendmessage :
                Log.e("hi","hi");
                send=messagetosend.getText().toString();
                final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");
                final Map data = new HashMap();
                data.put("title", "Important Message");
                data.put("body", send);
                final JSONObject jsonData = new JSONObject(data);
                final String notificationData = new JSONArray().put(jsonData).toString();
                i.putExtra("messageType", "PEBBLE_ALERT");
                i.putExtra("sender", "PebbleKit Android");
                i.putExtra("notificationData", notificationData);
                sendBroadcast(i);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");
//        final Map data = new HashMap();
//        data.put("title", "Important Message");
//        data.put("body", "Hi");
//        final JSONObject jsonData = new JSONObject(data);
//        final String notificationData = new JSONArray().put(jsonData).toString();
//        i.putExtra("messageType", "PEBBLE_ALERT");
//        i.putExtra("sender", "PebbleKit Android");
//        i.putExtra("notificationData", notificationData);
//        sendBroadcast(i);
//
//    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mDataReceiver == null) {
            mDataReceiver = new PebbleKit.PebbleDataReceiver(APP_UUID) {

                @Override
                public void receiveData(Context context, int transactionId, PebbleDictionary dict) {
                    // Message received, over!
                    PebbleKit.sendAckToPebble(context, transactionId);
                    Log.e("receiveData", "Got message from Pebble!");
                    if(dict.getInteger(KEY_BUTTON_UP) != null) {
                        previousPage();
                    }

                    // Down received?
                    if(dict.getInteger(KEY_BUTTON_DOWN) != null) {
                        nextPage();
                    }
                }
            };
            PebbleKit.registerReceivedDataHandler(getApplicationContext(), mDataReceiver);
        }
    }

    private void nextPage() {
        Toast.makeText(getApplicationContext(),"Taking you to Home Security",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,HomeSecurity.class));
    }

    private void previousPage() {
        Toast.makeText(getApplicationContext(),"Taking you to Personal Security",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, PersonalSecurity.class));
    }
}
