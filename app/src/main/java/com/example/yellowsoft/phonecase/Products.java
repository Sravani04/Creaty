package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 16/11/17.
 */

public class Products implements Serializable {
    public  String id,title,title_ar,price,stock,sale,customization,quantity,full_image,transparent_image,description,
            description_ar,coll_id,coll_title,coll_title_ar,brand_id,brand_title,brand_title_ar,model_id,model_title,model_title_ar,final_price;
    int cart_quantity;
    String shipping_price;
    public ArrayList<Images> images;
    String uri,text,gallerypath;
    public Products(JsonObject jsonObject, Context context){
        try {
            id = jsonObject.get("id").getAsString();
            title = jsonObject.get("title").getAsString();
            title_ar = jsonObject.get("title_ar").getAsString();
            price = jsonObject.get("price").getAsString();
            stock = jsonObject.get("stock").getAsString();
            sale = jsonObject.get("sale").getAsString();
            customization = jsonObject.get("customization").getAsString();
            quantity = jsonObject.get("quantity").getAsString();
            full_image = jsonObject.get("full_image").getAsString();
            transparent_image = jsonObject.get("transparent_image").getAsString();
            description = jsonObject.get("description").getAsString();
            description_ar = jsonObject.get("description_ar").getAsString();
            coll_id = jsonObject.get("collection").getAsJsonObject().get("id").getAsString();
            coll_title = jsonObject.get("collection").getAsJsonObject().get("title").getAsString();
            coll_title_ar = jsonObject.get("collection").getAsJsonObject().get("title_ar").getAsString();
            brand_id = jsonObject.get("brand").getAsJsonObject().get("id").getAsString();
            brand_title = jsonObject.get("brand").getAsJsonObject().get("title").getAsString();
            brand_title_ar = jsonObject.get("brand").getAsJsonObject().get("title_ar").getAsString();
            model_id = jsonObject.get("model").getAsJsonObject().get("id").getAsString();
            model_title = jsonObject.get("model").getAsJsonObject().get("title").getAsString();
            model_title_ar = jsonObject.get("model").getAsJsonObject().get("title_ar").getAsString();
            if (jsonObject.has("uri")){
                uri = jsonObject.get("uri").getAsString();
            }else {
                uri = "";
            }

            if (jsonObject.has("text")){
                text = jsonObject.get("text").getAsString();
            }else {
                text = "";
            }

            if (jsonObject.has("gallerypath")){
                gallerypath = jsonObject.get("gallerypath").getAsString();
            }else {
                gallerypath = "";
            }

            cart_quantity = 1;
            shipping_price = "50 KD";
            images = new ArrayList<>();

            if (jsonObject.has("images")) {

                for (int i = 0; i <jsonObject.get("images").getAsJsonArray().size(); i++) {
                    Images image = new Images(jsonObject.get("images").getAsJsonArray().get(i).getAsJsonObject(), context);
                    images.add(image);
                }
            }else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class Images implements Serializable{
        public  String id,color_id,color_title,color_title_ar,code,image;
        public Images(JsonObject jsonObject,Context context){
            Log.e("images",jsonObject.toString());
            try {
                id = jsonObject.get("id").getAsString();
                color_id = jsonObject.get("color").getAsJsonObject().get("id").getAsString();
                color_title = jsonObject.get("color").getAsJsonObject().get("title").getAsString();
                color_title_ar = jsonObject.get("color").getAsJsonObject().get("title_ar").getAsString();
                code = jsonObject.get("color").getAsJsonObject().get("code").getAsString();
                image = jsonObject.get("image").getAsString();

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    public JsonObject getJson(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",id);
        jsonObject.addProperty("title",title);
        jsonObject.addProperty("title_ar",title_ar);
        jsonObject.addProperty("price",price);
        jsonObject.addProperty("stock",stock);
        jsonObject.addProperty("sale",sale);
        jsonObject.addProperty("customization",customization);
        jsonObject.addProperty("full_image",full_image);
        jsonObject.addProperty("transparent_image",transparent_image);
        jsonObject.addProperty("quantity",quantity);
        jsonObject.addProperty("final_price",final_price);
        jsonObject.addProperty("description",description);
        jsonObject.addProperty("description_ar",description_ar);

        jsonObject.addProperty("uri",uri);
        jsonObject.addProperty("text",text);
        jsonObject.addProperty("gallerypath",gallerypath);
        jsonObject.addProperty("cart_quantity",cart_quantity);


        JsonObject collection = new JsonObject();
        collection.addProperty("id",coll_id);
        collection.addProperty("title",coll_title);
        collection.addProperty("title_ar",coll_title_ar);

        JsonObject brand = new JsonObject();
        brand.addProperty("id",brand_id);
        brand.addProperty("title",brand_title);
        brand.addProperty("title_ar",brand_title_ar);

        JsonObject model = new JsonObject();
        model.addProperty("id",model_id);
        model.addProperty("title",model_title);
        model.addProperty("title_ar",model_title_ar);


        JsonArray imagess = new JsonArray();
        for (int i=0;i<this.images.size();i++){
            JsonObject image = new JsonObject();
            image.addProperty("id",this.images.get(i).id);
            image.addProperty("image",this.images.get(i).image);

            JsonObject color = new JsonObject();
            color.addProperty("id",this.images.get(i).color_id);
            color.addProperty("title",this.images.get(i).color_title);
            color.addProperty("title_ar",this.images.get(i).color_title_ar);
            color.addProperty("code",this.images.get(i).code);
            image.add("color",color);

            imagess.add(image);

        }



        jsonObject.add("collection",collection);
        jsonObject.add("brand",brand);
        jsonObject.add("model",model);
        jsonObject.add("images",imagess);






        return jsonObject;
    }





}
