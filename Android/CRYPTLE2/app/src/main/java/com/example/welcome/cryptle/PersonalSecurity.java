package com.example.welcome.cryptle;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

public class PersonalSecurity extends AppCompatActivity {

    private TextView personaltext;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_security);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"JosefinSans-SemiBold.ttf");
        personaltext = (TextView)findViewById(R.id.persnltext);
        personaltext.setTypeface(typeface);
        btn = (Button)findViewById(R.id.sendthisbtn);
        btn.setTypeface(typeface);
        SpannableString s = new SpannableString("Personal Security");
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),"amaranth.regular.ttf");
        s.setSpan(new CustomTypefaceSpan("",typeface1), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);
    }
}
