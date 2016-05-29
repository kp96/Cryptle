package com.example.welcome.cryptle;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by welcome on 29-May-16.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ListRowViewHolder> {
public Context context;
private ArrayList<Home> home;
    private int focusseditem =0;
    public HomeAdapter(Context context,ArrayList<Home> home)
    {
        this.context = context;
        this.home = home;
    }
    @Override
    public ListRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        ListRowViewHolder holder = new ListRowViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(ListRowViewHolder holder, int position) {
        Home home1 = home.get(position);
        holder.itemView.setSelected(focusseditem == position);
        holder.getLayoutPosition();
        holder.status.setText(Html.fromHtml(home1.getStatus()));
        holder.deviceid.setText(Html.fromHtml(home1.getDevice()));
    }

    @Override
    public int getItemCount() {
        Log.e("size", home.size() + "");
        return (null!=home? home.size() : 0);
    }

    public static class ListRowViewHolder extends RecyclerView.ViewHolder{

        private TextView deviceid,status;
        Typeface typeface;
        public ListRowViewHolder(View itemView) {
            super(itemView);
            typeface = Typeface.createFromAsset(itemView.getContext().getAssets(),"JosefinSans-SemiBold.ttf");
            deviceid = (TextView)itemView.findViewById(R.id.devid);
            deviceid.setTypeface(typeface);
            status = (TextView)itemView.findViewById(R.id.intdetect);
            status.setTypeface(typeface);
        }
    }
}
