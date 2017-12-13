package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 20/10/17.
 */

public class TemplateScreen extends Activity {
    ImageView back_btn,cart_btn;
    RecyclerView recyclerView;
    TemplateScreenAdapter adapter;
    ArrayList<Integer> images;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_screen);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        images = new ArrayList<>();
        images.add(R.drawable.iphone6);
        images.add(R.drawable.iphone6s);
        images.add(R.drawable.iphone6);
        images.add(R.drawable.iphone6s);
        images.add(R.drawable.iphone6);
        images.add(R.drawable.iphone6s);
        images.add(R.drawable.iphone6);
        images.add(R.drawable.iphone6s);
        images.add(R.drawable.iphone6);
        images.add(R.drawable.iphone6s);
        images.add(R.drawable.iphone6);
        images.add(R.drawable.iphone6s);
        images.add(R.drawable.iphone6);
        images.add(R.drawable.iphone6s);

        adapter = new TemplateScreenAdapter(this,images);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setScrollContainer(true);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemplateScreen.this.onBackPressed();
            }
        });
    }
}
