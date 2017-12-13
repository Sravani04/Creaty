package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by yellowsoft on 29/11/17.
 */

public class GalleryImageActivity extends Activity {
    ImageView back_btn;
    RecyclerView recyclerView;
    GalleryImageAdapter adapter;
    Gallery gallery;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_list);
        if (getIntent()!=null && getIntent().hasExtra("gallery")){
            gallery = (Gallery) getIntent().getSerializableExtra("gallery");
        }
        back_btn = (ImageView) findViewById(R.id.back_btn);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new GalleryImageAdapter(this,gallery,this);
        recyclerView.setAdapter(adapter);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryImageActivity.this.onBackPressed();
            }
        });
    }
}
