package com.example.yellowsoft.phonecase;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by yellowsoft on 13/11/17.
 */

public class PreviewPage extends Activity {
    ImageView preview,back_btn,share_btn;
    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
    String bitmapPath;
    Uri bitmapUri;
    LinearLayout add_to_cart;
    TextView product_title;
    Products products;
    Uri myUri;
    String myuristring,myuristring_path;
    String text,gpath;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_page);
        preview = (ImageView) findViewById(R.id.preview);
       // back_btn = (ImageView) findViewById(R.id.back_btn);
        share_btn = (ImageView) findViewById(R.id.share_btn);
        add_to_cart = (LinearLayout) findViewById(R.id.add_to_cart);
        product_title = (TextView) findViewById(R.id.product_title);

        Bundle extras = getIntent().getExtras();
         myUri = Uri.parse(extras.getString("image"));
        myuristring = extras.getString("image");
        myuristring_path = extras.getString("image_path");
        Log.e("fdsf",myUri.toString());

        products = (Products) getIntent().getSerializableExtra("products");

        text = getIntent().getStringExtra("text");

        gpath = getIntent().getStringExtra("gallery_path");

//        product_title.setText(products.title);


        Ion.with(this).load(myUri.toString()).intoImageView(preview);
        Log.e("image", String.valueOf(myUri.toString()));

//        final Bitmap bitmap = getIntent().getParcelableExtra("image");
//        preview.setImageBitmap(bitmap);

//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PreviewPage.this.onBackPressed();
//            }
//        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    return;
                }

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/png");
                    intent.putExtra(Intent.EXTRA_STREAM, myUri);
                    startActivity(Intent.createChooser(intent, "Share via"));

            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.uri = myuristring;
                products.text = text;
                products.gallerypath = gpath;
                Log.e("gallll",products.gallerypath);
                Log.e("uri",products.uri);
                Session.SetCartProducts(PreviewPage.this, products);
                    Log.e("cart_products_size", String.valueOf(Session.GetCartProducts(PreviewPage.this).size()));
                    Toast.makeText(PreviewPage.this, "Product is added to the Cart", Toast.LENGTH_SHORT).show();
                    Log.e("pro_price", products.price);
                    Intent intent = new Intent(PreviewPage.this, CartPage.class);
//                    intent.putExtra("ggpath",gpath);

                    //intent.putExtra("products", products);
                    startActivity(intent);
                   // intent.putExtra("createdimage",myUri.toString());
                   // Log.e("createdimage",myUri.toString());


            }
        });




    }
}
