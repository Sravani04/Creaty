package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 20/12/17.
 */

public class OrdersPage extends Activity {
    ImageView back_btn;
    ListView listView;
    OrdersAdapter adapter;
    ArrayList<Orders> ordersfrom_api;
    LinearLayout no_orders_page;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        listView = (ListView) findViewById(R.id.orders_list);
        no_orders_page = (LinearLayout) findViewById(R.id.no_orders_page);
        ordersfrom_api = new ArrayList<>();
        adapter = new OrdersAdapter(this,ordersfrom_api);
        listView.setAdapter(adapter);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrdersPage.this.onBackPressed();
            }
        });

        get_orders();



    }

    public void get_orders(){
        final KProgressHUD hud = KProgressHUD.create(OrdersPage.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"order-history.php")
                .setBodyParameter("member_id",Session.GetUserId(this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try{
                            hud.dismiss();
                            Log.e("orders",result.toString());
                            for (int i=0;i<result.size();i++){
                                Orders orders = new Orders(result.get(i).getAsJsonObject(),OrdersPage.this);
                                ordersfrom_api.add(orders);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                        if (ordersfrom_api.size() == 0)
                        {
                            no_orders_page.setVisibility(View.VISIBLE);
                        }else {
                            no_orders_page.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
