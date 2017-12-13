package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 29/11/17.
 */

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    Gallery galleries;
    GalleryImageActivity activity;

    public static  class SimpleViewHolder  extends  RecyclerView.ViewHolder{
        TextView image_title;
        ImageView image;
        public SimpleViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            image_title = (TextView) view.findViewById(R.id.image_title);

        }
    }

    public GalleryImageAdapter(Context context,Gallery galleries,GalleryImageActivity activity){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.galleries = galleries;
        this.activity = activity;
    }
    @Override
    public GalleryImageAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_items,null);
        return new GalleryImageAdapter.SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GalleryImageAdapter.SimpleViewHolder holder, final int position) {
        Picasso.with(context).load(galleries.images.get(position).image).into(holder.image);
        holder.image_title.setText(galleries.images.get(position).title);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("image",galleries.images.get(position).image);
                Log.e("image",galleries.images.get(position).image);
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });

    }


    @Override
    public int getItemCount() {
        return galleries.images.size();
    }
}

