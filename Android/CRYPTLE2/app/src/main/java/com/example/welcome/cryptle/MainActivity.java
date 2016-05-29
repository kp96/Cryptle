package com.example.welcome.cryptle;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private TextView choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_family);
        choose = (TextView)findViewById(R.id.choose);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"JosefinSans-SemiBold.ttf");
        choose.setTypeface(typeface);
        SpannableString s = new SpannableString("Your family members");
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),"amaranth.regular.ttf");
        s.setSpan(new CustomTypefaceSpan("",typeface1), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_layout, getData());
        gridView.setAdapter(gridAdapter);
    }
    public String name [] = {"Rohit","Krishna","Sulabh","Hemanth","Mayank"};
    public String thumbnail [] = {"http://res.cloudinary.com/mayankbansal/image/upload/c_scale,w_600/v1464492311/Cryptle/rohit.jpg","http://res.cloudinary.com/mayankbansal/image/upload/c_scale,w_600/v1464492310/Cryptle/kp.jpg","http://res.cloudinary.com/mayankbansal/image/upload/c_scale,w_600/v1464492311/Cryptle/sulabh.jpg","http://res.cloudinary.com/mayankbansal/image/upload/c_scale,w_600/v1464492310/Cryptle/hemant.jpg","http://res.cloudinary.com/mayankbansal/image/upload/c_scale,w_600/v1464492310/Cryptle/mayank.jpg"};

    private ArrayList<NameImage> getData() {
        final ArrayList<NameImage> items = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            NameImage h = new NameImage();
            h.setName(name[i]);
            h.setThumbnail(thumbnail[i]);
            items.add(h);
        }
        return items;
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isConnected = PebbleKit.isWatchConnected(this);
        Log.i("rohit", String.valueOf(isConnected));
        Toast.makeText(this, "Pebble " + (isConnected ? "is" : "is not") + " connected!", Toast.LENGTH_LONG).show();
    }
}
