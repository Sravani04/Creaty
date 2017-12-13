package com.example.yellowsoft.phonecase;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 21/11/17.
 */

public class Gallery implements Serializable {
    public String id,title,title_ar,image;
    public ArrayList<Images> images;

    public Gallery(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        image = jsonObject.get("image").getAsString();

        images = new ArrayList<>();
        for (int i=0;i<jsonObject.get("images").getAsJsonArray().size();i++){
            Images image = new Images(jsonObject.get("images").getAsJsonArray().get(i).getAsJsonObject(),context);
            images.add(image);
        }
    }

    public class Images implements Serializable{
        public String id,title,title_ar,image;

        public Images(JsonObject jsonObject,Context context){
            id = jsonObject.get("id").getAsString();
            title = jsonObject.get("title").getAsString();
            title_ar = jsonObject.get("title_ar").getAsString();
            image = jsonObject.get("image").getAsString();
        }
    }
}
