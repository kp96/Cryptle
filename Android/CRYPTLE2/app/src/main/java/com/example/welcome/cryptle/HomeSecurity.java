package com.example.welcome.cryptle;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;

public class HomeSecurity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private static final int VERTICAL_ITEM_SPACE = 20;
    private ArrayList<Home> home;
    private String deviceid[]= {"Device Id - 01","Device Id - 02","Device Id - 03","Device Id - 04","Device Id - 05"};
    private String status[] = {"Your device is safe","Intruder Detected","Your device is safe","Intruder Detected","Your device is safe"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_security);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"JosefinSans-SemiBold.ttf");
        SpannableString s = new SpannableString("Home Security");
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),"amaranth.regular.ttf");
        s.setSpan(new CustomTypefaceSpan("",typeface1), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);
        mRecyclerView = (RecyclerView)findViewById(R.id.homerecycler);
        mAdapter = new HomeAdapter(getApplicationContext(),getDataSet());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        mRecyclerView.setAdapter(mAdapter);

    }
        private ArrayList<Home> getDataSet() {
         ArrayList<Home> items = new ArrayList<>();
        String x = "hotel";
        for(int i = 0; i < 5; i++) {
            Home h = new Home();
            h.setDevice(deviceid[i]);
            h.setStatus(status[i]);
            items.add(h);
        }
        return items;
    }
}
