package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.JSONArrayBody;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 20/10/17.
 */

public class EditProfilePage extends Activity {
    ImageView back_btn;
    LinearLayout settings_btn;
    TextView logout_btn;
    EditText fname,lname,phone,email;
    TextView update_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        update_btn = (TextView) findViewById(R.id.update_btn);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfilePage.this,MyProfilePage.class);
                startActivity(intent);
                finish();
            }
        });


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_member();
            }
        });

        get_members();
    }

    public void edit_member(){
        String fname_string = fname.getText().toString();
        String lname_string = lname.getText().toString();
        String phone_string = phone.getText().toString();
        if (fname_string.equals("")){
            Toast.makeText(EditProfilePage.this,"Please Enter Your First Name",Toast.LENGTH_SHORT).show();
            fname.requestFocus();
        }else if (lname_string.equals("")){
            Toast.makeText(EditProfilePage.this,"Please Enter Your Last Name",Toast.LENGTH_SHORT).show();
            lname.requestFocus();
        }else if (phone_string.equals("")){
            Toast.makeText(EditProfilePage.this,"Please Enter Your Phone",Toast.LENGTH_SHORT).show();
            phone.requestFocus();
        }else {
            final KProgressHUD hud = KProgressHUD.create(EditProfilePage.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setMaxProgress(100)
                    .show();
            Ion.with(this)
                    .load(Session.SERVER_URL+"edit-member.php")
                    .setBodyParameter("member_id",Session.GetUserId(this))
                    .setBodyParameter("fname",fname_string)
                    .setBodyParameter("lname",lname_string)
                    .setBodyParameter("phone",phone_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                hud.dismiss();
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(EditProfilePage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    EditProfilePage.this.onBackPressed();
                                } else {
                                    Toast.makeText(EditProfilePage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }


    public void get_members(){
        final KProgressHUD hud = KProgressHUD.create(EditProfilePage.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"members.php")
                .setBodyParameter("member_id",Session.GetUserId(EditProfilePage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            fname.setText(jsonObject.get("fname").getAsString());
                            lname.setText(jsonObject.get("lname").getAsString());
                            phone.setText(jsonObject.get("phone").getAsString());
                            email.setText(jsonObject.get("email").getAsString());

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }
}
