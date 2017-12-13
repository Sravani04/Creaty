package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by yellowsoft on 18/10/17.
 */

public class ProductDetail extends Activity {
    ImageView back_btn,cart_btn;
    ViewPager viewPager;
    ArrayList<Integer> images;
    CircleIndicator indicator;
    int currentPage =0;
    LinearLayout continue_btn;
    TextView product_price,text_change,description,product_title,title;
    String type;
    GridView gridView;
    ColorsAdapter colorsAdapter;
    Products products;
    ImageView product_image;
    TextView cart_items;
    ArrayList<Products> productsfrom_api;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        continue_btn = (LinearLayout) findViewById(R.id.continue_btn);
        gridView = (GridView) findViewById(R.id.colors_list);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        text_change = (TextView) findViewById(R.id.text_change);
        product_price = (TextView) findViewById(R.id.product_price);
        description = (TextView) findViewById(R.id.description);
        product_title = (TextView) findViewById(R.id.product_title);
        product_image = (ImageView) findViewById(R.id.product_image);
        title = (TextView) findViewById(R.id.title);
        cart_items = (TextView) findViewById(R.id.cart_items);
        productsfrom_api = new ArrayList<>();
        images = new ArrayList<>();
        images.add(R.drawable.detailimage);
        images.add(R.drawable.detailimage2);
        images.add(R.drawable.detailimage3);
        if (getIntent()!=null && getIntent().hasExtra("products")){
            products = (Products) getIntent().getSerializableExtra("products");
        }

        cart_items.setText(String.valueOf(Session.GetCartProducts(this).size()));

        colorsAdapter = new ColorsAdapter(this,products);
        gridView.setAdapter(colorsAdapter);

        Picasso.with(ProductDetail.this).load(products.images.get(0).image).into(product_image);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Picasso.with(ProductDetail.this).load(products.images.get(i).image).into(product_image);
                Log.e("image",products.images.get(i).image);
            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.customization.equals("Yes")){
                    Intent intent = new Intent(ProductDetail.this, CaseLayoutEffectActivity.class);
                    intent.putExtra("products", products);
                    startActivity(intent);
                }else if (products.customization.equals("No")){
//                   productsfrom_api.clear();
//                    if (Session.GetCartProducts(ProductDetail.this).equals("-1")){
//                        Session.SetCartProducts(ProductDetail.this, products);
//                    }else {
//                        JsonArray jsonArray = Session.GetCartProducts(ProductDetail.this);
//                        for (int i = 0; i < jsonArray.size(); i++) {
//                            productsfrom_api.add(new Products(jsonArray.get(i).getAsJsonObject(), ProductDetail.this));
//                            if (products.id.equals(productsfrom_api.get(i).id)) {
//                                productsfrom_api.get(i).quantity = String.valueOf(Integer.parseInt(productsfrom_api.get(i).quantity + 1));
//                            }
//                        }
//
//
//                        Session.deleteCart(ProductDetail.this);
//                        for (int i = 0; i < productsfrom_api.size(); i++) {
//                            Session.SetCartProducts(ProductDetail.this, productsfrom_api.get(i));
//                        }
//                    }
                    Session.SetCartProducts(ProductDetail.this, products);
                    Log.e("cart_products_size", String.valueOf(Session.GetCartProducts(ProductDetail.this).size()));
                    Toast.makeText(ProductDetail.this, "Product is added to the Cart", Toast.LENGTH_SHORT).show();
                    Log.e("pro_price", products.price);
                    Intent intent = new Intent(ProductDetail.this, CartPage.class);
                    intent.putExtra("products", products);
                    startActivity(intent);
                }
            }
        });

        description.setText(Html.fromHtml(products.description));
        product_price.setText(products.price +" "+ "KD");
        product_title.setText(products.title);
        title.setText(products.title);

       // gridView.setNumColumns(products.images.size());

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetail.this.onBackPressed();
            }
        });

       if(products.customization.equals("Yes")){
           text_change.setText("CONTINUE");
       }else if(products.customization.equals("No")){
           text_change.setText("ADD TO CART");
       }




        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this,CartPage.class);
                startActivity(intent);
            }
        });





    }





}
