package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 16/11/17.
 */

public class ForgetPasswordPage extends Activity {
    TextView submit_btn,back_btn;
    EditText email;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        back_btn = (TextView) findViewById(R.id.back_btn);
        email = (EditText) findViewById(R.id.email);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(ForgetPasswordPage.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgot_password();
            }
        });

    }

    public void forgot_password(){
        String email_string = email.getText().toString();
        if (email_string.equals("")){
            Toast.makeText(ForgetPasswordPage.this,"Please Enter Your Email Id",Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else {
            final KProgressHUD hud = KProgressHUD.create(ForgetPasswordPage.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setMaxProgress(100)
                    .show();
            Ion.with(this)
                    .load(Session.SERVER_URL+"forget-password.php")
                    .setBodyParameter("email",email_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                             try {
                                 hud.dismiss();
                                 if (result.get("status").getAsString().equals("Success")){
                                     Toast.makeText(ForgetPasswordPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(ForgetPasswordPage.this,LoginPage.class);
                                     startActivity(intent);
                                     finish();
                                 }else {
                                     Toast.makeText(ForgetPasswordPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                 }
                             }catch (Exception e1){
                                 e1.printStackTrace();
                             }
                        }
                    });
        }
    }
}
