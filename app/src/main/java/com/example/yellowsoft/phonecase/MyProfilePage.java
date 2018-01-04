package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yellowsoft on 18/11/17.
 */

public class MyProfilePage extends Activity {
    ImageView back_btn;
    TextView edit_profile,change_password,gallery,logout_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
        edit_profile = (TextView) findViewById(R.id.edit_profile);
        change_password = (TextView) findViewById(R.id.change_password);
        gallery = (TextView) findViewById(R.id.gallery);
        //logout_btn = (TextView) findViewById(R.id.logout_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfilePage.this,EditProfilePage.class);
                startActivity(intent);
                finish();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfilePage.this,ChangePasswordPage.class);
                startActivity(intent);
                finish();
            }
        });

//        logout_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Session.SetUserId(MyProfilePage.this,"-1");
//                Intent intent = new Intent(MyProfilePage.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProfilePage.this.onBackPressed();
            }
        });

    }
}
