package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterPage  extends Activity{
    TextView register_btn,login_btn;
    EditText fname,lname,email,password,phone;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        register_btn = (TextView) findViewById(R.id.register_btn);
        login_btn = (TextView) findViewById(R.id.login_btn);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               add_member();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void add_member(){

        String fname_string = fname.getText().toString();
        String lname_string = lname.getText().toString();
        String email_string = email.getText().toString();
        String password_string = password.getText().toString();
        String phone_string = phone.getText().toString();
        if (fname_string.equals("")){
            Toast.makeText(RegisterPage.this,"Please Enter Your First Name",Toast.LENGTH_SHORT).show();
            fname.requestFocus();
        }else if (lname_string.equals("")){
            Toast.makeText(RegisterPage.this,"Please Enter Your Last Name",Toast.LENGTH_SHORT).show();
            lname.requestFocus();
        }else if (email_string.equals("")){
            Toast.makeText(RegisterPage.this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else if (password_string.equals("")){
            Toast.makeText(RegisterPage.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }else if (phone_string.equals("")){
            Toast.makeText(RegisterPage.this,"Please Enter Your Phone",Toast.LENGTH_SHORT).show();
            lname.requestFocus();
        }else {
            final KProgressHUD hud = KProgressHUD.create(RegisterPage.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setMaxProgress(100)
                    .show();
            Ion.with(this)
                    .load(Session.SERVER_URL+"member.php")
                    .setBodyParameter("fname",fname_string)
                    .setBodyParameter("lname",lname_string)
                    .setBodyParameter("email",email_string)
                    .setBodyParameter("password",password_string)
                    .setBodyParameter("phone",password_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                hud.dismiss();
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(RegisterPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(RegisterPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }
}
