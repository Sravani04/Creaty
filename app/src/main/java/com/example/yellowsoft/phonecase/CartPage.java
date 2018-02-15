package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.StreamBody;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 9/11/17.
 */

public class CartPage extends Activity {
    RecyclerView recyclerView;
    CartPageAdapter adapter;
    LinearLayout checkout_btn;
    ImageView back_btn;
    Float total;
    float total_cart_price = 0.0f;
    TextView total_price,shipping_price;
    String price;
    ArrayList<Products> productsfrom_api;
    EditText coupon_text;
    TextView check_btn,discount_value,total_discount_price;
    String tot_price,dis;
    LinearLayout line4,discount,line5,after_discount;
    Float grandtotal;
    Uri imageUri;
    String sp;
    String tot,localpath;
    int delivery_charges =0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);
        productsfrom_api = new ArrayList<>();


//        String previewimage = getIntent().getExtras().getString("createdimage");
//        if(previewimage!= null) {
//            imageUri=Uri.parse(previewimage);
//            Log.e("idfdsf",previewimage.toString());
//
//        }


//        if (getIntent()!= null && getIntent().hasExtra("ggpath")){
//
//            localpath = getIntent().getStringExtra("ggpath");
//
//        }



        recyclerView = (RecyclerView) findViewById(R.id.basket_list);
        checkout_btn = (LinearLayout) findViewById(R.id.checkout_btn);
        discount_value = (TextView) findViewById(R.id.discount_value);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        total_price = (TextView) findViewById(R.id.total_price);
        coupon_text = (EditText) findViewById(R.id.coupon_text);
        check_btn = (TextView) findViewById(R.id.check_btn);
        total_discount_price = (TextView) findViewById(R.id.total_discount_price);
        line4 = (LinearLayout) findViewById(R.id.line4);
        discount = (LinearLayout) findViewById(R.id.discount);
        line5 = (LinearLayout) findViewById(R.id.line5);
        shipping_price = (TextView) findViewById(R.id.shipping_price);
        after_discount = (LinearLayout) findViewById(R.id.after_discount);
        adapter = new CartPageAdapter(this,productsfrom_api,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shipping_price.setText(delivery_charges+"KD");

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout();
//                if (Session.GetUserId(CartPage.this).equals("-1")){
//                    Intent intent = new Intent(CartPage.this,GuestPage.class);
//                    intent.putExtra("total", String.valueOf(total));
//                    intent.putExtra("dc", String.valueOf(delivery_charges));
//                    intent.putExtra("text",coupon_text.getText().toString());
//                    intent.putExtra("dv",discount_value.getText().toString());
//
//                    startActivity(intent);
//                }else {
//
//                }

            }
        });

        line4.setVisibility(View.GONE);
        discount.setVisibility(View.GONE);
        line5.setVisibility(View.GONE);
        after_discount.setVisibility(View.GONE);


        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  check_coupon();
            }
        });


        get_products();
    }


    public void get_products() {
        Log.e("cart_list", String.valueOf(Session.GetCartProducts(CartPage.this).toString()));
        JsonArray temp;

        temp = Session.GetCartProducts(CartPage.this);
        for (int i=0;i<temp.size();i++){
            Log.e("result",temp.toString());
            Products products = new Products(temp.get(i).getAsJsonObject(),CartPage.this);
            productsfrom_api.add(products);
            Log.e("_price",products.price);
            total_cart_price = total_cart_price + (products.cart_quantity * Float.parseFloat(products.price));
//            sp = String.valueOf(Float.parseFloat(products.shipping_price));
        }
        total=total_cart_price+delivery_charges;
        total_discount_price.setText(String.valueOf(total) +"KD");
        adapter.notifyDataSetChanged();
//        grandtotal = Float.parseFloat(shipping_price.getText().toString());
        total_price.setText(String.valueOf(total_cart_price) + "KD");
         tot = String.valueOf(total_cart_price + 50);
    }


    public void calculate_total_price(){

        total_cart_price = 0.0f;
        for (int i=0;i<productsfrom_api.size();i++){
            total_cart_price = total_cart_price + (productsfrom_api.get(i).cart_quantity* Float.parseFloat(productsfrom_api.get(i).price));
        }
        total_price.setText(String.valueOf(total_cart_price)+"KD");
        total=total_cart_price+delivery_charges;
        total_discount_price.setText(String.valueOf(total) +"KD");
    }

    public void check_coupon(){
        String coupon_string = coupon_text.getText().toString();
        if (coupon_string.equals("")){
            Toast.makeText(CartPage.this,"Please Enter Coupon Code",Toast.LENGTH_SHORT).show();
            coupon_text.requestFocus();
        }else {
            final KProgressHUD hud = KProgressHUD.create(CartPage.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setMaxProgress(100)
                    .show();
            Ion.with(this)
                    .load(Session.SERVER_URL + "coupon-check.php")
                    .setBodyParameter("coupon", coupon_string)
                    .setBodyParameter("cart_total", String.valueOf(total_cart_price))
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                hud.dismiss();
                                Log.e("coupon", result.toString());
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(CartPage.this, result.get("discount_value").getAsString(), Toast.LENGTH_SHORT).show();
                                    line4.setVisibility(View.VISIBLE);
                                    discount.setVisibility(View.VISIBLE);
                                    line5.setVisibility(View.VISIBLE);
                                    after_discount.setVisibility(View.VISIBLE);
                                    discount_value.setText(result.get("discount_value").getAsString()+"KD");
                                   // Float totalprice = Float.parseFloat(total_price.getText().toString());
                                    Float discountprice = Float.parseFloat(result.get("discount_value").getAsString());
                                    total_cart_price=total_cart_price-discountprice;
                                    total=total_cart_price+delivery_charges;
                                    total_discount_price.setText(String.valueOf(total) +"KD");
//                                    total_discount_price.setText(String.valueOf(total_cart_price + delivery_charges - discountprice) +"KD");

                                } else {
                                    Toast.makeText(CartPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        }
                    });
        }
    }

    public void checkout(){


        if (coupon_text.getText().toString().equals("")){
            Intent intent = new Intent(CartPage.this, CheckoutPage.class);
            intent.putExtra("total", String.valueOf(total));
            intent.putExtra("dc", String.valueOf(delivery_charges));
            intent.putExtra("text","");
            intent.putExtra("dv","");
//            intent.putExtra("lpath",productsfrom_api);
//            Log.e("print", localpath);
            startActivity(intent);

        }else {
            Intent intent = new Intent(CartPage.this, CheckoutPage.class);
            intent.putExtra("total", String.valueOf(total));
            intent.putExtra("dc", String.valueOf(delivery_charges));
            intent.putExtra("text",coupon_text.getText().toString());
            intent.putExtra("dv",discount_value.getText().toString());
//            intent.putExtra("lpath",localpath);
//            Log.e("print", localpath);
            startActivity(intent);
        }
    }







}

