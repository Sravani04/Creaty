package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 3/11/17.
 */

public class ChooseCaseAdapter extends RecyclerView.Adapter<ChooseCaseAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    ArrayList<String> prices;
    ArrayList<Products> products;

    public static  class SimpleViewHolder  extends  RecyclerView.ViewHolder{
        TextView case_title,case_price;
        ImageView case_image,next_btn;
        LinearLayout item;
        ProgressBar progressBar;
        public SimpleViewHolder(View view) {
            super(view);
            case_title = (TextView) view.findViewById(R.id.case_title);
            case_price = (TextView) view.findViewById(R.id.case_cost);
            case_image = (ImageView) view.findViewById(R.id.case_image);
            next_btn = (ImageView) view.findViewById(R.id.next_btn);
            item = (LinearLayout) view.findViewById(R.id.item);
            progressBar = (ProgressBar) view.findViewById(R.id.progress);



        }
    }

    public ChooseCaseAdapter(Context context,ArrayList<Products> products){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.titles = titles;
        this.images = images;
        this.prices = prices;
        this.products = products;

    }
    @Override
    public ChooseCaseAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_case_items,parent,false);
        return new ChooseCaseAdapter.SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ChooseCaseAdapter.SimpleViewHolder holder, final int position) {


        holder.case_title.setText(products.get(position).title);
        holder.case_price.setText(products.get(position).price + " " + "KD");
      //  holder.case_image.setImageResource(images.get(position));

        holder.progressBar.setVisibility(View.VISIBLE);
        holder.progressBar.setIndeterminate(true);
        holder.progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.headercolor), android.graphics.PorterDuff.Mode.MULTIPLY);
        Picasso.with(context).load(products.get(position).images.get(0).image).placeholder(R.drawable.placeholder)
                .into(holder.case_image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (holder.progressBar != null) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });


       // Picasso.with(context).load(products.get(position).images.get(0).image).placeholder(R.drawable.placeholder).into(holder.case_image);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductDetail.class);
                intent.putExtra("products",products.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();
    }




}

