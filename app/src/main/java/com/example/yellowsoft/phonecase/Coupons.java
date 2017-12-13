package com.example.yellowsoft.phonecase;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 18/11/17.
 */

public class Coupons implements Serializable {
    public String id,code,min_amount,discount_type,discount,no_of_times,start_date,end_date;

    public Coupons(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        code = jsonObject.get("code").getAsString();
        min_amount = jsonObject.get("min_amount").getAsString();
        discount_type = jsonObject.get("discount_type").getAsString();
        discount = jsonObject.get("discount").getAsString();
        no_of_times = jsonObject.get("no_of_times").getAsString();
        start_date = jsonObject.get("start_date").getAsString();
        end_date = jsonObject.get("end_date").getAsString();
    }
}
