package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 18/11/17.
 */

public class ChangePasswordPage extends Activity {
    EditText old_password,password,confirm_password;
    TextView submit_btn;
    ImageView back_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        old_password = (EditText) findViewById(R.id.old_password);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_password();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordPage.this,MyProfilePage.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void change_password(){
        String opassword_string = old_password.getText().toString();
        String password_string = password.getText().toString();
        String cpassword_string = confirm_password.getText().toString();
        if (opassword_string.equals("")){
            Toast.makeText(ChangePasswordPage.this,"Please Enter Old Password",Toast.LENGTH_SHORT).show();
            old_password.requestFocus();
        }else if (password_string.equals("")){
            Toast.makeText(ChangePasswordPage.this,"Please Enter New Password",Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }else if (cpassword_string.equals("")){
            Toast.makeText(ChangePasswordPage.this,"Please Enter Confirm Password",Toast.LENGTH_SHORT).show();
            confirm_password.requestFocus();
        }else {
            final KProgressHUD hud = KProgressHUD.create(ChangePasswordPage.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setMaxProgress(100)
                    .show();
            Ion.with(this)
                    .load(Session.SERVER_URL + "change-password.php")
                    .setBodyParameter("member_id", Session.GetUserId(this))
                    .setBodyParameter("opassword", opassword_string)
                    .setBodyParameter("password", password_string)
                    .setBodyParameter("cpassword", cpassword_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                hud.dismiss();
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(ChangePasswordPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ChangePasswordPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });
        }

    }
}
