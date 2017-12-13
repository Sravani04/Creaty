package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 20/10/17.
 */

public class TemplateScreenAdapter extends RecyclerView.Adapter<TemplateScreenAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Integer> images;

    public static  class SimpleViewHolder  extends  RecyclerView.ViewHolder{
        ImageView template_image;
        public SimpleViewHolder(View view) {
            super(view);
            template_image = (ImageView) view.findViewById(R.id.template_image);


        }
    }

    public TemplateScreenAdapter(Context context,ArrayList<Integer> images){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.images = images;

    }
    @Override
    public TemplateScreenAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_screen_items,parent,false);
        return new TemplateScreenAdapter.SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TemplateScreenAdapter.SimpleViewHolder holder, final int position) {
        holder.template_image.setImageResource(images.get(position));
        holder.template_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductsActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return images.size();
    }
}

