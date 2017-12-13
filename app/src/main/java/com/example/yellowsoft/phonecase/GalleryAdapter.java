package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 29/11/17.
 */

public class GalleryAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Gallery> galleries;
    GalleryActivity activity;


    public GalleryAdapter(Context context,ArrayList<Gallery> galleries,GalleryActivity activity){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.galleries = galleries;
        this.activity = activity;
    }




    @Override
    public int getCount() {
        return galleries.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.gallery_items,null);
        TextView title = (TextView) item_view.findViewById(R.id.title);
        ImageView cat_image = (ImageView) item_view.findViewById(R.id.image);
        Picasso.with(context).load(galleries.get(i).image).into(cat_image);
        title.setText(galleries.get(i).title);

        return item_view;
    }
}

