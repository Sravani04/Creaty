package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yellowsoft on 4/1/18.
 */

public class GuestPage extends Activity{
    TextView login_btn,guest;
    String total_price,shipping_price,coupon,discount_value;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_page);
        login_btn = (TextView) findViewById(R.id.login_btn);
        guest = (TextView) findViewById(R.id.guest);

        if (getIntent()!=null && getIntent().hasExtra("total")){
            total_price = getIntent().getStringExtra("total");
            coupon = getIntent().getStringExtra("text");
            discount_value = getIntent().getStringExtra("dv");
            shipping_price=getIntent().getStringExtra("dc");
//            gallerypath = getIntent().getStringExtra("lpath");
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestPage.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coupon.equals("")){
                    Intent intent = new Intent(GuestPage.this, CheckoutPage.class);
                    intent.putExtra("total", total_price);
                    intent.putExtra("dc", shipping_price);
                    intent.putExtra("text","");
                    intent.putExtra("dv","");
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(GuestPage.this, CheckoutPage.class);
                    intent.putExtra("total", total_price);
                    intent.putExtra("dc", shipping_price);
                    intent.putExtra("text",coupon);
                    intent.putExtra("dv",discount_value);
                    startActivity(intent);
                }

            }
        });

    }
}
