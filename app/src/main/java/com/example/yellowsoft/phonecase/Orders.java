package com.example.yellowsoft.phonecase;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 20/12/17.
 */

public class Orders implements Serializable {
    public String id,fname,lname,address,city,state,country,pincode,phone,email,coupon_code,discount_amount,price,
            delivery_charges,payment_method,payment_status,delivery_status,date,refid,payid,crdtype,trackid,crd,hashcode,
    product_id,product_name,product_name_ar,quantity,product_price,total,customized,original_image,modified_image,photo_text,
    history_id,message,time;
    public Orders(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        fname = jsonObject.get("fname").getAsString();
        lname = jsonObject.get("lname").getAsString();
        address = jsonObject.get("address").getAsString();
        city = jsonObject.get("city").getAsString();
        state = jsonObject.get("state").getAsString();
        country = jsonObject.get("country").getAsString();
        pincode = jsonObject.get("pincode").getAsString();
        phone = jsonObject.get("phone").getAsString();
        email = jsonObject.get("email").getAsString();
        coupon_code = jsonObject.get("coupon_code").getAsString();
        discount_amount = jsonObject.get("discount_amount").getAsString();
        price = jsonObject.get("price").getAsString();
        delivery_charges = jsonObject.get("delivery_charges").getAsString();
        payment_method = jsonObject.get("payment_method").getAsString();
        payment_status = jsonObject.get("payment_status").getAsString();
        delivery_status = jsonObject.get("delivery_status").getAsString();
        date = jsonObject.get("date").getAsString();
        refid = jsonObject.get("refid").getAsString();
        payid = jsonObject.get("payid").getAsString();
        crdtype = jsonObject.get("crdtype").getAsString();
        trackid = jsonObject.get("trackid").getAsString();
        crd = jsonObject.get("crd").getAsString();
        hashcode = jsonObject.get("hashcode").getAsString();
        for (int i=0;i<jsonObject.get("products").getAsJsonArray().size();i++){
             product_id = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("product_id").getAsString();
             product_name = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("product_name").getAsString();
            product_name_ar = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("product_name_ar").getAsString();
            quantity = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("quantity").getAsString();
            product_price = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("price").getAsString();
            total = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("total").getAsString();
            customized = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("customized").getAsString();
            original_image = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("original_image").getAsString();
            modified_image = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("modified_image").getAsString();
            photo_text = jsonObject.get("products").getAsJsonArray().get(i).getAsJsonObject().get("photo_text").getAsString();

        }

        for (int i=0;i<jsonObject.get("history").getAsJsonArray().size();i++){
            history_id = jsonObject.get("history").getAsJsonArray().get(i).getAsJsonObject().get("id").getAsString();
            message = jsonObject.get("history").getAsJsonArray().get(i).getAsJsonObject().get("message").getAsString();
            time = jsonObject.get("history").getAsJsonArray().get(i).getAsJsonObject().get("time").getAsString();
        }

    }
}
