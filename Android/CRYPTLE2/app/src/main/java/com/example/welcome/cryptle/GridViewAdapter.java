package com.example.welcome.cryptle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by welcome on 28-May-16.
 */
public class GridViewAdapter extends ArrayAdapter{
    private Context context;
    private int layoutResourceId;
    private ArrayList<NameImage> data= new ArrayList<>();
    private ImageLoader mImageLoader;
    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.nameofmembers);
            holder.linear = (LinearLayout)row.findViewById(R.id.linear1);
            holder.image = (NetworkImageView) row.findViewById(R.id.imageformembers);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        NameImage item = data.get(position);
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MessageDisplay.class);
                context.startActivity(intent);
            }
        });
        holder.imageTitle.setText(item.getName());
        Log.e("error", item.getName());
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        Log.e("error1", item.getThumbnail());
        holder.image.setImageUrl(item.getThumbnail(), mImageLoader);
        holder.image.setDefaultImageResId(R.mipmap.ic_launcher);
        return row;
    }
        static class ViewHolder{
        TextView imageTitle;
        NetworkImageView image;
        LinearLayout linear;
        }
}
