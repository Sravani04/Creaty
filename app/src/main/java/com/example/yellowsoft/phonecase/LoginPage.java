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

public class LoginPage extends Activity {
    TextView login_btn, forgotpass_btn, register_btn;
    EditText email, password;
    Uri myUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        login_btn = (TextView) findViewById(R.id.login_btn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        forgotpass_btn = (TextView) findViewById(R.id.forgotpass_btn);
        register_btn = (TextView) findViewById(R.id.register_btn);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             login();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

        forgotpass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, ForgetPasswordPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void login() {
        String email_string = email.getText().toString();
        String password_string = password.getText().toString();
        if (email_string.equals("")) {
            Toast.makeText(LoginPage.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        } else if (password_string.equals("")) {
            Toast.makeText(LoginPage.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        } else {
            final KProgressHUD hud = KProgressHUD.create(LoginPage.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setMaxProgress(100)
                    .show();
            Ion.with(this)
                    .load(Session.SERVER_URL + "login.php")
                    .setBodyParameter("email", email_string)
                    .setBodyParameter("password", password_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                hud.dismiss();
                                Log.e("loginresult",result.toString());
                                if (result.get("status").getAsString().equals("Success")) {
                                    Session.SetUserId(LoginPage.this, result.get("member_id").getAsString());
                                    Log.e("member_id", result.get("member_id").toString());
//                                    Toast.makeText(LoginPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                    });

        }

    }
}
