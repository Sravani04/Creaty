package com.example.yellowsoft.phonecase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 20/12/17.
 */

public class OrdersAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Orders> orders;

    public OrdersAdapter(Context context, ArrayList<Orders> orders){
        this.context = context;
        this.orders = orders;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return orders.size();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.order_items,null);
        LinearLayout item = (LinearLayout) item_view.findViewById(R.id.item);
        TextView order_id = (TextView) item_view.findViewById(R.id.order_id);
        TextView date = (TextView) item_view.findViewById(R.id.date);
        TextView payment_status = (TextView) item_view.findViewById(R.id.payment_status);
        TextView delivery_status = (TextView) item_view.findViewById(R.id.delivery_status);
        TextView total_price = (TextView) item_view.findViewById(R.id.total_price);
        order_id.setText(orders.get(position).id);
        date.setText(orders.get(position).date);
        payment_status.setText(orders.get(position).payment_status);
        delivery_status.setText(orders.get(position).delivery_status);
        total_price.setText(orders.get(position).total + "KD");

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("coming","Yes");
                Intent intent = new Intent(context,OrderDetailPage.class);
                intent.putExtra("orders",orders.get(position));
                Log.e("ordersresponse",orders.get(position).toString());
                context.startActivity(intent);
            }
        });




        return item_view;
    }

    public void onClick(){

    }
}
