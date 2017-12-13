package com.example.yellowsoft.phonecase;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 16/11/17.
 */

public class Brands implements Serializable {
    public  String id,title,title_ar,image;
    public ArrayList<Models> models;

    public Brands(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        image = jsonObject.get("image").getAsString();
        models = new ArrayList<>();
        for (int i=0;i<jsonObject.get("models").getAsJsonArray().size();i++){
            Models model = new Models(jsonObject.get("models").getAsJsonArray().get(i).getAsJsonObject(),context);
            models.add(model);
        }
    }

    public class Models implements Serializable {
        public String id,title,title_ar,image;

        public Models(JsonObject jsonObject, Context context){
            id = jsonObject.get("id").getAsString();
            title = jsonObject.get("title").getAsString();
            title_ar = jsonObject.get("title_ar").getAsString();
            image = jsonObject.get("image").getAsString();

        }
    }


}
