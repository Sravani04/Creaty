package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 4/11/17.
 */

public class CollocationAdapter extends RecyclerView.Adapter<CollocationAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<String> titles;
    String custom;
    ArrayList<Collection> collections;


    public static  class SimpleViewHolder  extends  RecyclerView.ViewHolder{
        TextView title;
        LinearLayout collocation_list;
        ImageView coll_image;
        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            collocation_list = (LinearLayout) view.findViewById(R.id.collocation_list);
            coll_image = (ImageView) view.findViewById(R.id.coll_image);

        }
    }

    public CollocationAdapter(Context context,ArrayList<Collection> collections,String custom){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.titles = titles;
        this.custom = custom;
        this.collections = collections;

    }
    @Override
    public CollocationAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collocation_screen_items,parent,false);
        return new CollocationAdapter.SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CollocationAdapter.SimpleViewHolder holder, final int position) {
             holder.title.setText(collections.get(position).title);
             Picasso.with(context).load(collections.get(position).image).into(holder.coll_image);
              holder.collocation_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductsActivity.class);
                intent.putExtra("custom",custom);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return collections.size();
    }
}

