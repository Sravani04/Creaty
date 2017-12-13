package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 29/11/17.
 */

public class GalleryActivity extends Activity {
    ImageView back_btn;
    GridView gridView;
    GalleryAdapter adapter;
    ArrayList<Gallery> galleriesfrom_api;
    String image;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_list);
        galleriesfrom_api = new ArrayList<>();
        back_btn = (ImageView) findViewById(R.id.back_btn);
        gridView = (GridView) findViewById(R.id.rv);
        adapter = new GalleryAdapter(this,galleriesfrom_api,this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GalleryActivity.this,GalleryImageActivity.class);
                intent.putExtra("gallery",galleriesfrom_api.get(i));
                startActivityForResult(intent,1);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryActivity.this.onBackPressed();
            }
        });




        get_gallery();

    }

    public void get_gallery(){
        final KProgressHUD hud = KProgressHUD.create(GalleryActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"gallery.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            Log.e("gallery",result.toString());
                            for (int i=0;i<result.size();i++){
                                Gallery gallery = new Gallery(result.get(i).getAsJsonObject(),GalleryActivity.this);
                                galleriesfrom_api.add(gallery);
                            }
                            adapter.notifyDataSetChanged();

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                // do something with the result
                if (data != null && data.hasExtra("image")) {
                    image = data.getExtras().getString("image");
                    Log.e("imageresponse", image);
                }

                Intent intent = new Intent();
                intent.putExtra("image",image);
                Log.e("fd",image);
                setResult(Activity.RESULT_OK, intent);
                finish();




            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }


}
