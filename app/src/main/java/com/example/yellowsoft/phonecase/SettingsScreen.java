package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by yellowsoft on 23/10/17.
 */

public class SettingsScreen extends Activity {
    ImageView back_btn;
    LinearLayout customer_btn,shipping_btn;
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        customer_btn = (LinearLayout) findViewById(R.id.customer_btn);
        shipping_btn = (LinearLayout) findViewById(R.id.shipping_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SettingsScreen.this,ProfileActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsScreen.this,CustomerServiceScreen.class);
                startActivity(intent);
            }
        });

        shipping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsScreen.this,ShippingScreen.class);
                startActivity(intent);
            }
        });
    }
}
