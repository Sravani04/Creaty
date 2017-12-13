package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 4/11/17.
 */

public class CollocationActivity extends Activity {
    ImageView back_btn,cart_btn;
    RecyclerView recyclerView;
    CollocationAdapter adapter;
    ArrayList<String> titles;
    String custom;
    ArrayList<Collection> collectionsfrom_api;
    TextView cart_items;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collocation_screen);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        cart_items = (TextView) findViewById(R.id.cart_items);
        titles = new ArrayList<>();
        collectionsfrom_api = new ArrayList<>();
        titles.add("LUCY HALE");
        titles.add("TOP 100 DESIGNS");
        titles.add("BLACK BEAUTY");
        titles.add("THE SKINNY CONFIDENTIAL");
        titles.add("PINK ABOUT IT");
        titles.add("DARLING MAGAZINE");

        if (getIntent()!=null && getIntent().hasExtra("custom"))
            custom = getIntent().getStringExtra("custom");
        else
            custom = getIntent().getStringExtra("custom");
        adapter = new CollocationAdapter(this,collectionsfrom_api,custom);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        cart_items.setText(String.valueOf(Session.GetCartProducts(CollocationActivity.this).size()));


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollocationActivity.this.onBackPressed();
            }
        });

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollocationActivity.this,CartPage.class);
                startActivity(intent);
            }
        });
        get_collections();
    }

    public void get_collections(){
        final KProgressHUD hud = KProgressHUD.create(CollocationActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"collections.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            for (int i=0;i<result.size();i++){
                                Collection collection = new Collection(result.get(i).getAsJsonObject(),CollocationActivity.this);
                                collectionsfrom_api.add(collection);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }
}
