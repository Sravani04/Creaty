package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
 * Created by yellowsoft on 9/11/17.
 */

public class CartPageAdapter extends RecyclerView.Adapter<CartPageAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Products> products;
    CartPage activity;


    public static  class SimpleViewHolder  extends  RecyclerView.ViewHolder{
        ImageView edit_basket,product_image,add_btn,minus_btn,delete_basket;
        TextView product_title,product_price,product_quantity;
        public SimpleViewHolder(View view) {
            super(view);
            product_image = (ImageView) view.findViewById(R.id.product_image);
            product_title =(TextView) view.findViewById(R.id.product_title);
            product_price = (TextView) view.findViewById(R.id.product_price);
            product_quantity = (TextView) view.findViewById(R.id.product_quantity);
            add_btn=(ImageView) view.findViewById(R.id.add_btn);
            minus_btn = (ImageView) view.findViewById(R.id.minus_btn);
            delete_basket = (ImageView) view.findViewById(R.id.delete_basket);


        }
    }



    public CartPageAdapter(Context context,ArrayList<Products> products,CartPage activity){
        this.context  = context;
        inflater = LayoutInflater.from(context);
        this.products = products;
        this.activity = activity;

    }
    @Override
    public CartPageAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartpage_items,parent,false);
        return new CartPageAdapter.SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartPageAdapter.SimpleViewHolder holder, final int position) {
        try {
            if (products.get(position).customization.equals("Yes")){
                Picasso.with(context).load(products.get(position).uri).into(holder.product_image);
                Log.e("imaaa", products.get(position).uri);
            }else if (products.get(position).customization.equals("No")){
                Picasso.with(context).load(products.get(position).images.get(0).image).placeholder(R.drawable.placeholder).into(holder.product_image);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductDetail.class);
                intent.putExtra("products",products.get(position));
                context.startActivity(intent);
            }
        });

        holder.product_title.setText(products.get(position).title);
        holder.product_price.setText(products.get(position).price + " "+ "KD");
        holder.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.get(position).cart_quantity < 10){
                    products.get(position).cart_quantity ++;
                    holder.product_quantity.setText(String.valueOf(products.get(position).cart_quantity));
                    activity.calculate_total_price();
                }
            }
        });

        holder.minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.get(position).cart_quantity > 1){
                    products.get(position).cart_quantity--;
                    holder.product_quantity.setText(String.valueOf(products.get(position).cart_quantity));
                    activity.calculate_total_price();
                }
            }
        });



        holder.delete_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    products.remove(holder.getAdapterPosition());
                    Session.deleteCart(context);
                    for (int i=0;i<products.size();i++){
                        Session.SetCartProducts(context,products.get(i));
                    }
                    notifyDataSetChanged();
                    activity.calculate_total_price();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();
    }


}

