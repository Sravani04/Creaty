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
 * Created by yellowsoft on 3/11/17.
 */

public class ChooseCaseScreen extends Activity {
    RecyclerView recyclerView;
    ChooseCaseAdapter adapter;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    ArrayList<String> prices;
    ImageView back_btn,cart_btn;
    String brandid,modelid,custom;
    ArrayList<Products> productsfrom_api;
    TextView cart_items;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_case);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        cart_items = (TextView) findViewById(R.id.cart_items);
        titles = new ArrayList<>();
        images = new ArrayList<>();
        prices = new ArrayList<>();

        productsfrom_api = new ArrayList<>();

        titles.add("Classic Grip");
        titles.add("Snap Case");

        images.add(R.drawable.transparent_back2);
        images.add(R.drawable.transparent_backcover);

        prices.add("$40 USD");
        prices.add("$35 USD");

        if (getIntent()!=null && getIntent().hasExtra("brand_id")){
            brandid = getIntent().getStringExtra("brand_id");
            modelid = getIntent().getStringExtra("model_id");
        }

        cart_items.setText(String.valueOf(Session.GetCartProducts(this).size()));


        if (getIntent()!=null && getIntent().hasExtra("custom"))
            custom = getIntent().getStringExtra("custom");
        else
            custom = getIntent().getStringExtra("custom");



        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ChooseCaseAdapter(this,productsfrom_api);
        recyclerView.setAdapter(adapter);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseCaseScreen.this.onBackPressed();
            }
        });

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseCaseScreen.this,CartPage.class);
                startActivity(intent);
            }
        });

        get_products();



    }

    public void get_products(){
        final KProgressHUD hud = KProgressHUD.create(ChooseCaseScreen.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"products.php")
                .setBodyParameter("custom",custom)
                .setBodyParameter("brand",brandid)
                .setBodyParameter("model",modelid)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            Log.e("pr",result.toString());
                            Log.e("custom",custom);
                            for (int i=0;i<result.size();i++){
                                Products products = new Products(result.get(i).getAsJsonObject(),ChooseCaseScreen.this);
                                productsfrom_api.add(products);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}

