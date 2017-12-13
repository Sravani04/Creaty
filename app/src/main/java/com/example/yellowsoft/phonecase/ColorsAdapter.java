package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 17/11/17.
 */

public class ColorsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    Products products;

    public ColorsAdapter(Context context, Products products){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.images.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.color_items,null);
        TextView color_code = (TextView) item_view.findViewById(R.id.color_code);
     //   color_code.setTextColor(Integer.parseInt(colors.get(i).code));
        GradientDrawable backgroundGradient = (GradientDrawable) color_code.getBackground();
        backgroundGradient.setColor(Color.parseColor(products.images.get(i).code.toString()));

        return item_view;
    }
}
