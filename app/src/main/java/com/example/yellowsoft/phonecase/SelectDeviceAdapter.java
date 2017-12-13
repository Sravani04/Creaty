package com.example.yellowsoft.phonecase;

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
 * Created by yellowsoft on 3/11/17.
 */

public class SelectDeviceAdapter extends RecyclerView.Adapter<SelectDeviceAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Integer> images;
    ArrayList<String> titles;
    String custom;
    Brands brands;

    public static  class SimpleViewHolder  extends  RecyclerView.ViewHolder{
        TextView device_title;
        ImageView device_image;
        public SimpleViewHolder(View view) {
            super(view);
            device_image = (ImageView) view.findViewById(R.id.device_image);
            device_title = (TextView) view.findViewById(R.id.device_title);

        }
    }

    public SelectDeviceAdapter(Context context,Brands brands,String custom){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.images = images;
        this.titles = titles;
        this.custom = custom;
        this.brands=brands;

    }
    @Override
    public SelectDeviceAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_device_screen_items,parent,false);
        return new SelectDeviceAdapter.SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SelectDeviceAdapter.SimpleViewHolder holder, final int position) {
        holder.device_title.setText(brands.models.get(position).title);
        Log.e("titles",brands.models.get(position).title);
        Picasso.with(context).load(brands.models.get(position).image).placeholder(R.drawable.placeholder).into(holder.device_image);
        holder.device_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, ChooseCaseScreen.class);
                    intent.putExtra("brand_id",brands.id);
                    intent.putExtra("model_id",brands.models.get(position).id);
                    intent.putExtra("custom",custom);
                    context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return brands.models.size();
    }
}

