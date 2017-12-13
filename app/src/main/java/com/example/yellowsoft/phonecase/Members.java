package com.example.yellowsoft.phonecase;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 16/11/17.
 */

public class Members implements Serializable{
    public static String id,fname,lname,email,phone,address,city,state_id,state_title,title_ar,country_id,country_title,
    country_title_ar,pincode,image;

    public Members(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        fname = jsonObject.get("fname").getAsString();
        lname = jsonObject.get("lname").getAsString();
        email = jsonObject.get("email").getAsString();
        phone = jsonObject.get("phone").getAsString();
        address = jsonObject.get("address").getAsString();
        city = jsonObject.get("city").getAsString();
        state_id = jsonObject.get("state").getAsJsonObject().get("id").getAsString();
        state_title = jsonObject.get("state").getAsJsonObject().get("title").getAsString();
        title_ar = jsonObject.get("state").getAsJsonObject().get("title_ar").getAsString();
        country_id= jsonObject.get("country").getAsJsonObject().get("id").getAsString();
        country_title= jsonObject.get("country").getAsJsonObject().get("title").getAsString();
        country_title_ar= jsonObject.get("country").getAsJsonObject().get("title_ar").getAsString();
        pincode = jsonObject.get("pincode").getAsString();
        image = jsonObject.get("image").getAsString();

    }
}
