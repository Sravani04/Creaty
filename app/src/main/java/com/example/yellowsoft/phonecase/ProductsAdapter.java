package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.PluralsRes;
import android.support.annotation.RequiresApi;
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
 * Created by yellowsoft on 18/10/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    String custom;
    ArrayList<Brands> brands;

    public static  class SimpleViewHolder  extends  RecyclerView.ViewHolder{
        TextView brand_title;
        ImageView brand_image;

        public SimpleViewHolder(View view) {
            super(view);
            brand_title = (TextView) view.findViewById(R.id.brand_title);
            brand_image = (ImageView) view.findViewById(R.id.brand_image);

        }
    }

    public ProductsAdapter(Context context, ArrayList<Brands> brands,String custom){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.titles = titles;
        this.images = images;
        this.custom = custom;
        this.brands = brands;

    }
    @Override
    public ProductsAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_items,parent,false);
        return new SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.SimpleViewHolder holder, final int position) {

        holder.brand_title.setText(brands.get(position).title);
        Picasso.with(context).load(brands.get(position).image).into(holder.brand_image);

        holder.brand_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, SelectDeviceScreen.class);
                    intent.putExtra("models", brands.get(position));
                    intent.putExtra("custom",custom);
                    Log.e("mofd", String.valueOf(brands.get(position).models.size()));
                    context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return brands.size();
    }
}

