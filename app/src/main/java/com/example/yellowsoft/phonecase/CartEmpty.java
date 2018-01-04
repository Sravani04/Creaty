package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yellowsoft on 3/1/18.
 */

public class CartEmpty extends Activity {
    TextView here_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_empty);
        here_btn = (TextView) findViewById(R.id.here_btn);
        here_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartEmpty.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
