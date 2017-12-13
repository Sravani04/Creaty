package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * Created by yellowsoft on 10/11/17.
 */

public class Session {
    static String sb_shake="sb_shake";
    public  static  final String mem_id="mem_id";
    public static String SERVER_URL = "http://clients.mamacgroup.com/creaty/api/";
    public static final String cart_products = "cart_products";
    public static final String setting = "settings";
    public static final String gallery = "galleries";
    public static String  PAYMENT_URL = "http://clients.mamacgroup.com/creaty/api/Tap.php?";

    public static Boolean get_shake_status(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(sb_shake, true);
    }

    public  static void SetUserId(Context context, String id){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(mem_id,id);
        editor.commit();
    }

    public  static String GetUserId(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(mem_id,"-1");
    }

    public static void SetCartProducts(Context context,Products cart_product){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray= GetCartProducts(context);
        Log.e("adding_pro",String.valueOf(jsonArray.size()));
        jsonArray.add(cart_product.getJson());
        Log.e("adding_pro",String.valueOf(jsonArray.size()));
        Log.e("array_info",jsonArray.toString());
        editor.putString(cart_products,jsonArray.toString());
        editor.apply();
    }

    public static JsonArray GetCartProducts(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = new JsonArray();
        try
        {
            jsonArray = (JsonArray) jsonParser.parse(sharedPreferences.getString(cart_products, "[]"));
            Log.e("dfd",jsonArray.toString());
        }catch (Exception rx){
            rx.printStackTrace();
        }

        return  jsonArray;
    }

    public static void deleteCart(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cart_products,"[]");
        editor.apply();
    }


    public static void SetSettings(Context context, String settings){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(setting,settings);
        editor.commit();
    }

    public static String GetSettings(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(setting,"-1");
    }


    public static void SetGallery(Context context,String galleries){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(gallery,galleries);
        editor.commit();
    }

    public static String GetGallery(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(gallery,"-1");
    }

}
