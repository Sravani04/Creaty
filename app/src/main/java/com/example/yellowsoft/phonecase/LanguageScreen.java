package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 3/11/17.
 */

public class LanguageScreen extends Activity {
    TextView arabic_btn,english_btn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_screen);
        arabic_btn = (TextView) findViewById(R.id.arabic_btn);
        english_btn = (TextView) findViewById(R.id.english_btn);


        english_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LanguageScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        arabic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LanguageScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }




}
