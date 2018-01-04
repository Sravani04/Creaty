package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 3/11/17.
 */

public class SelectDeviceScreen  extends Activity {
    RecyclerView recyclerView;
    SelectDeviceAdapter adapter;
    ArrayList<Integer> images;
    ImageView back_btn,cart_btn;
    ArrayList<String> titles;
    String custom;
    Brands brands;
    TextView cart_items;

    String id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_device_screen);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        cart_items = (TextView) findViewById(R.id.cart_items);

        images = new ArrayList<>();
        titles = new ArrayList<>();

        cart_items.setText(String.valueOf(Session.GetCartProducts(this).size()));



        images.add(R.drawable.image7);
        images.add(R.drawable.image6);
        images.add(R.drawable.image5);
        images.add(R.drawable.image4);
        images.add(R.drawable.image3);
        images.add(R.drawable.placeholder);
        images.add(R.drawable.imagelist1);

        titles.add("IPHONE X");
        titles.add("IPHONE 8");
        titles.add("IPHONE 8 PLUS");
        titles.add("IPHONE 7");
        titles.add("IPHONE 7");
        titles.add("IPHONE 6S");
        titles.add("IPHONE 6S PLUS");

        brands =(Brands) getIntent().getSerializableExtra("models");
        for (int i=0;i<brands.models.size();i++){
            Log.e("dfds",String.valueOf(brands.models.get(i).title));
        }

        if (getIntent()!=null && getIntent().hasExtra("custom"))
            custom = getIntent().getStringExtra("custom");
        else
            custom = getIntent().getStringExtra("custom");




        recyclerView = (RecyclerView) findViewById(R.id.rv);
        if (brands.models.size()<=1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else {
            recyclerView.setLayoutManager(new SpannedGridLayoutManager(
                    new SpannedGridLayoutManager.GridSpanLookup() {
                        @Override
                        public SpannedGridLayoutManager.SpanInfo getSpanInfo(int position) {
                            if (position == 0) {
                                return new SpannedGridLayoutManager.SpanInfo(2, 1);
                            } else {
                                return new SpannedGridLayoutManager.SpanInfo(1, 1);
                            }
                        }
                    },
                    2/* Three columns */,
                    1f /* We want our items to be 1:1 ratio */));
        }

        recyclerView.setHasFixedSize(true);
        adapter = new SelectDeviceAdapter(this,brands,custom);
        recyclerView.setAdapter(adapter);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectDeviceScreen.this.onBackPressed();
            }
        });

        if (Session.GetCartProducts(this).size() == 0){
            Log.e("cart","disabled");
            cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SelectDeviceScreen.this,CartEmpty.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SelectDeviceScreen.this, CartPage.class);
                    startActivity(intent);

                }
            });
        }



    }








}

