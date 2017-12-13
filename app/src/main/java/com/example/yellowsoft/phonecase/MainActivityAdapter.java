package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 18/10/17.
 */

public class MainActivityAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Integer> images;
    ArrayList<Banners> banners;


    public MainActivityAdapter(Context context,ArrayList<Banners> banners) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.banners = banners;

    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.main_activity_items, container, false);
        ImageView banner_image= (ImageView) itemView.findViewById(R.id.banner_image);
        try {
            Picasso.with(context).load(banners.get(position).image).placeholder(R.drawable.placeholder).into(banner_image);
        }catch (Exception e1){
            e1.printStackTrace();
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

