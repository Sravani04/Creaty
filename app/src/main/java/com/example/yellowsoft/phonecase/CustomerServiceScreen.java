package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by yellowsoft on 23/10/17.
 */

public class CustomerServiceScreen extends Activity {
    ImageView back_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerServiceScreen.this.onBackPressed();
                finish();
            }
        });


    }
}
