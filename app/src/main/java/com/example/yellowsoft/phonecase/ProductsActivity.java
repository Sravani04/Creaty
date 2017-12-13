package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 18/10/17.
 */

public class ProductsActivity extends Activity {
    RecyclerView recyclerView;
    ProductsAdapter adapter;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    ImageView back_btn,cart_btn;
    String custom;
    ArrayList<Brands> brandsfrom_api;
    ArrayList<Products> productsfrom_api;
    TextView cart_items;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_screen);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        cart_items = (TextView) findViewById(R.id.cart_items);
        titles = new ArrayList<>();
        brandsfrom_api = new ArrayList<>();
        productsfrom_api = new ArrayList<>();

        titles.add("Apple");
        titles.add("Samsung");
        titles.add("HTC");
        titles.add("Sony");



        images = new ArrayList<>();
        images.add(R.drawable.image11);
        images.add(R.drawable.image10);
        images.add(R.drawable.image9);
        images.add(R.drawable.image8);

        if (getIntent()!=null && getIntent().hasExtra("custom"))
            custom = getIntent().getStringExtra("custom");
        else
            custom = getIntent().getStringExtra("custom");

        cart_items.setText(String.valueOf(Session.GetCartProducts(this).size()));

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ProductsAdapter(this,brandsfrom_api,custom);
        recyclerView.setAdapter(adapter);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductsActivity.this.onBackPressed();
            }
        });

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivity.this,CartPage.class);
                startActivity(intent);
            }
        });

        get_brands();


    }

    public void get_brands(){
        final KProgressHUD hud = KProgressHUD.create(ProductsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"models.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            Log.e("models",result.toString());
                            for (int i=0;i<result.size();i++) {
                                Brands brands = new Brands(result.get(i).getAsJsonObject(), ProductsActivity.this);
                                brandsfrom_api.add(brands);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }




}
