package com.example.yellowsoft.phonecase;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.LoginFilter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 13/11/17.
 */

public class CheckoutPage extends AppCompatActivity {
    ImageView back_btn,country_dropdown,state_dropdown,imageView2,imageView3;
    TextView login_btn,total_amount,ship_amt,final_cost,state_option,country_option,credit_card,paypal;
    String total_price,shipping_price,city_id,country_id,state_id;
    EditText email,fname,lname,address,pincode,city,phone;
    LinearLayout country,select_state;
    ArrayList<Country> countriesfrom_api;
    Country selectedCountry;
    ArrayList<Products> products;
    String message,coupon,discount_value,encodedString="";
    String path,gallerypath;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);
        login_btn = (TextView) findViewById(R.id.login_btn);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        total_amount = (TextView) findViewById(R.id.total_amount);
        ship_amt = (TextView) findViewById(R.id.ship_amt);
        final_cost = (TextView) findViewById(R.id.final_cost);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        email = (EditText) findViewById(R.id.email);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        state_option = (TextView) findViewById(R.id.state_option);
        country = (LinearLayout) findViewById(R.id.country);
        city = (EditText) findViewById(R.id.city);
        country_option = (TextView) findViewById(R.id.country_option);
        country_dropdown = (ImageView) findViewById(R.id.country_dropdown);
        select_state = (LinearLayout) findViewById(R.id.select_state);
        state_dropdown = (ImageView) findViewById(R.id.state_dropdown);
        credit_card = (TextView) findViewById(R.id.credit_card);
        paypal = (TextView) findViewById(R.id.paypal);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        phone = (EditText) findViewById(R.id.phone);


        countriesfrom_api = new ArrayList<>();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckoutPage.this.onBackPressed();
            }
        });

        if (getIntent()!=null && getIntent().hasExtra("total")){
            total_price = getIntent().getStringExtra("total");
            coupon = getIntent().getStringExtra("text");
            discount_value = getIntent().getStringExtra("dv");
            shipping_price=getIntent().getStringExtra("dc");
//            gallerypath = getIntent().getStringExtra("lpath");
        }

//        products = (ArrayList<Products>) getIntent().getSerializableExtra("products");
//        Log.e("products", String.valueOf(products));





        if (Session.GetUserId(CheckoutPage.this).equals("-1")) {
            login_btn.setVisibility(View.VISIBLE);
        }else {
           login_btn.setVisibility(View.GONE);
        }


        total_amount.setText(total_price + "KD");
        final_cost.setText(total_price);
        Log.e("final_cost",final_cost.getText().toString());

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutPage.this,LoginPage.class);
                startActivity(intent);
            }
        });

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onCountryChoice();
                dialog.show();
            }
        });

        country_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onCountryChoice();
                dialog.show();
            }
        });

        country_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onCountryChoice();
                dialog.show();
            }
        });

        select_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onStatesChoice();
                dialog.show();
            }
        });

        state_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onStatesChoice();
                dialog.show();
            }
        });

        state_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onStatesChoice();
                dialog.show();
            }
        });

        credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutPage.this,PaymentPage.class);
                intent.putExtra("amount",final_cost.getText().toString());
                startActivityForResult(intent,1);
               // place_order();

            }
        });

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutPage.this,PaymentPage.class);
                intent.putExtra("amount",final_cost.getText().toString());
                startActivityForResult(intent,1);
            }
        });

        get_members();

        get_countries();


    }


    public void get_countries(){
        final KProgressHUD hud = KProgressHUD.create(CheckoutPage.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"states.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            Log.e("country",result.toString());
                            for (int i=0;i<result.size();i++){
                                Country country = new Country(result.get(i).getAsJsonObject(),CheckoutPage.this);
                                countriesfrom_api.add(country);
                            }


                        }catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }




    public Dialog onCountryChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] array = new CharSequence[countriesfrom_api.size()];
        for(int i=0;i<countriesfrom_api.size();i++){

            array[i] = countriesfrom_api.get(i).title;
        }

        builder.setTitle("Select Country")
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String selectedItem = array[i].toString();
                        Log.e("select",selectedItem);
                        country_option.setText(selectedItem);
                        country_id = countriesfrom_api.get(i).id;
                        selectedCountry = countriesfrom_api.get(i);



                    }
                })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    public Dialog onStatesChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[selectedCountry.states.size()];
        for(int i=0;i<selectedCountry.states.size();i++){

            array[i] = selectedCountry.states.get(i).title;
        }
        builder.setTitle("Select Area").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                state_option.setText(selectedItem);
                state_id = selectedCountry.states.get(i).id;

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    public void get_members(){
        final KProgressHUD hud = KProgressHUD.create(CheckoutPage.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"members.php")
                .setBodyParameter("member_id",Session.GetUserId(CheckoutPage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            fname.setText(jsonObject.get("fname").getAsString());
                            lname.setText(jsonObject.get("lname").getAsString());
                            email.setText(jsonObject.get("email").getAsString());
                            address.setText(jsonObject.get("address").getAsString());
                            city.setText(jsonObject.get("city").getAsString());
                            pincode.setText(jsonObject.get("pincode").getAsString());
                            phone.setText(jsonObject.get("phone").getAsString());

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
                if (data != null && data.hasExtra("message")) {
                    message = data.getExtras().getString("message");
                    Log.e("toast", message);
                    if (message.equals("success")){
                        place_order();
                    }else if (message.equals("failure")){
                        Toast.makeText(CheckoutPage.this,"Please Try Again",Toast.LENGTH_SHORT).show();
                    }
                }





            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }

    public void encodeImagetoString(final String uri) {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                encodedString = "";
                Bitmap bitmap = BitmapFactory.decodeFile(uri, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, Base64.NO_WRAP);


                return encodedString;
            }

            @Override
            protected void onPostExecute(String msg) {

                // Put converted Image string into Async Http Post param
                // Trigger Image upload
                // makeHTTPCall();
            }
        }.execute(null, null, null);
    }

    private String convertToBase64(String imagePath) {


            File file = new File(getRealPathFromUri(this, Uri.parse(imagePath)));
            Uri uri1 = Uri.parse(file.getPath());
            // File imageFile = new File(imagePath);
            Log.e("path", imagePath);

            Bitmap bitmap = BitmapFactory.decodeFile(uri1.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();
            String img_str = Base64.encodeToString(image, 0);
            Log.e("bitmappath", img_str);
            byte[] decodedByte = Base64.decode(img_str, 0);
            Bitmap newBitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            // imageView2.setImageBitmap(newBitmap);
            Log.e("decode", newBitmap.toString());

            return img_str;

    }


    private String Base64(String imagePath) {
        String temp ="";
        temp = imagePath;
            Log.e("path", temp);
            Bitmap bitmap = BitmapFactory.decodeFile(temp);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] image=stream.toByteArray();
            String img_str="";
            img_str = Base64.encodeToString(image, Base64.NO_WRAP);
            Log.e("bitmappath", img_str);
            byte[] decodedByte = Base64.decode(img_str, Base64.NO_WRAP);
            Bitmap newBitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
           // imageView2.setImageBitmap(newBitmap);
          //  imageView3.setImageBitmap(newBitmap);
            Log.e("decode", newBitmap.toString());
            return img_str;


    }




    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }








    public void place_order(){

        final KProgressHUD hud = KProgressHUD.create(CheckoutPage.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("member_id",Session.GetUserId(CheckoutPage.this));
        JsonParser jsonParser = new JsonParser();

        JsonArray jsonArray = Session.GetCartProducts(this);

        JsonArray jsonArray1 = new JsonArray();
        Log.e("products_cart",jsonArray.toString());
        for (int i=0;i<jsonArray.size();i++){
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("product_id",jsonArray.get(i).getAsJsonObject().get("id").getAsString());
            jsonObject2.addProperty("quantity",jsonArray.get(i).getAsJsonObject().get("cart_quantity").getAsString());
            jsonObject2.addProperty("customized",jsonArray.get(i).getAsJsonObject().get("customization").getAsString());
            if (jsonArray.get(i).getAsJsonObject().get("customization").getAsString().equals("Yes")) {
                    String t=Base64(jsonArray.get(i).getAsJsonObject().get("gallerypath").getAsString());
                    jsonObject2.addProperty("orginal_image", t);
                    Log.e("paa_originL", t);
                    jsonArray1.add(jsonObject2);
                   // String extension = Base64(jsonArray.get(i).getAsJsonObject().get("gallerypath").getAsString()).replaceAll("^.*\\.", "");
                    jsonObject2.addProperty("modified_image", convertToBase64(jsonArray.get(i).getAsJsonObject().get("uri").getAsString()));
                    jsonObject2.addProperty("photo_text", jsonArray.get(i).getAsJsonObject().get("text").getAsString());
                   // jsonObject2.addProperty("orginal_image_ext", "." + extension);

                }else {
                //for (i = 0; i < jsonArray.get(i).getAsJsonObject().get("images").getAsJsonArray().size(); i++) {
                    jsonObject2.addProperty("orginal_image", jsonArray.get(i).getAsJsonObject().get("images").getAsJsonArray().get(i).getAsJsonObject().get("image").getAsString());
                    Log.e("no", jsonArray.get(i).getAsJsonObject().get("images").getAsJsonArray().get(i).getAsJsonObject().get("image").getAsString());
                    //String extension = jsonArray.get(i).getAsJsonObject().get("images").getAsJsonArray().get(i).getAsJsonObject().get("image").getAsString().replaceAll("^.*\\.", "");
                    // Log.e("extension", "." + extension);
                    //jsonObject2.addProperty("orginal_image_ext", "." + extension);
                //}
                    jsonArray1.add(jsonObject2);

            }

            jsonObject2.addProperty("price",jsonArray.get(i).getAsJsonObject().get("price").getAsString());
            Log.e("productsresponse",jsonArray1.toString());
        }
        jsonObject.add("products",jsonArray1);


        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("firstname",fname.getText().toString());
        jsonObject1.addProperty("lastname",lname.getText().toString());
        jsonObject1.addProperty("address",address.getText().toString());
        jsonObject1.addProperty("city",city.getText().toString());
        jsonObject1.addProperty("state",state_option.getText().toString());
        jsonObject1.addProperty("country",country_option.getText().toString());
        jsonObject1.addProperty("pincode",pincode.getText().toString());
        jsonObject1.addProperty("email",email.getText().toString());
        jsonObject.add("address",jsonObject1);

        jsonObject.addProperty("coupon_code", coupon);
        jsonObject.addProperty("discount_amount",discount_value);
        jsonObject.addProperty("total_price", total_price);
        jsonObject.addProperty("delivery_charges",shipping_price);
        jsonObject.addProperty("payment_method","Tap");
        Log.e("reeeee",jsonObject.toString());
        if (address.getText().toString().equals("")){
            Toast.makeText(CheckoutPage.this,"Please Enter Address",Toast.LENGTH_SHORT).show();
            address.requestFocus();
        }else if (fname.getText().toString().equals("")){
            Toast.makeText(CheckoutPage.this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
            fname.requestFocus();
        }else if (lname.getText().toString().equals("")){
            Toast.makeText(CheckoutPage.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            lname.requestFocus();
        }else if (phone.getText().toString().equals("")){
            Toast.makeText(CheckoutPage.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
            phone.requestFocus();
        }else if (city.getText().toString().equals("")){
            Toast.makeText(CheckoutPage.this,"Please Enter City",Toast.LENGTH_SHORT).show();
            city.requestFocus();
        }else if (email.getText().toString().equals("")){
            Toast.makeText(CheckoutPage.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else if (pincode.getText().toString().equals("")){
            Toast.makeText(CheckoutPage.this,"Please Enter Pincode",Toast.LENGTH_SHORT).show();
            pincode.requestFocus();
        }else {
            Ion.with(this)
                    .load(Session.SERVER_URL + "place-order.php")
                    .setBodyParameter("content",jsonObject.toString())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                hud.dismiss();
                                if (result.get("status").getAsString().equals("Success")) {
                                    Log.e("order_id",result.get("order_id").getAsString());
                                    Log.e("result", result.get("message").getAsString());
                                    Toast.makeText(CheckoutPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    if (Session.GetUserId(CheckoutPage.this).equals("-1")) {
                                        Intent intent = new Intent(CheckoutPage.this, RegisterPage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(CheckoutPage.this, ThankyouScreen.class);
                                        startActivity(intent);
                                         finish();
                                    }
                                } else {
                                    Toast.makeText(CheckoutPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }
}
