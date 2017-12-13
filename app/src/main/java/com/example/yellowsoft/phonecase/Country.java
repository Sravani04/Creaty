package com.example.yellowsoft.phonecase;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 28/11/17.
 */

public class Country implements Serializable {
    public String id,title,title_ar;
    public ArrayList<States> states;
    public Country(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        states = new ArrayList<>();
        for (int i=0;i<jsonObject.get("states").getAsJsonArray().size();i++){
            States state = new States(jsonObject.get("states").getAsJsonArray().get(i).getAsJsonObject(),context);
            states.add(state);
        }
    }

    public class States implements Serializable{
        public String id,title,title_ar;
        public States(JsonObject jsonObject,Context context){
            id = jsonObject.get("id").getAsString();
            title = jsonObject.get("title").getAsString();
            title_ar = jsonObject.get("title_ar").getAsString();
        }
    }
}
