package com.example.yellowsoft.phonecase;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 17/11/17.
 */

public class Colors implements Serializable {
    public String id,title,title_ar,code;
    public Colors(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        code = jsonObject.get("code").getAsString();
    }
}
