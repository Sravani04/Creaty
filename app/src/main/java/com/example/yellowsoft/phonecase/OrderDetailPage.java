package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yellowsoft on 21/12/17.
 */

public class OrderDetailPage extends Activity {
    ImageView back_btn;
    TextView order_id,date,payment_status,payment_type,delivery_status,fname,address,phone,email,title,quantity,price,delivery_charges,wc,sub_total,grand_total;
    Orders orders;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        if (getIntent()!=null && getIntent().hasExtra("orders")){
            orders = (Orders) getIntent().getSerializableExtra("orders");
            Log.e("orderprint",orders.toString());
        }
        order_id = (TextView) findViewById(R.id.order_id);
        date = (TextView) findViewById(R.id.date);
        payment_status = (TextView) findViewById(R.id.payment_status);
        payment_type = (TextView) findViewById(R.id.payment_tyoe);
        delivery_status = (TextView) findViewById(R.id.delivery_status);
        fname = (TextView) findViewById(R.id.fname);
        address = (TextView) findViewById(R.id.address);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        title = (TextView) findViewById(R.id.title);
        quantity = (TextView) findViewById(R.id.quantity);
        price = (TextView) findViewById(R.id.price);
        delivery_charges = (TextView) findViewById(R.id.delivery_charges);
        wc = (TextView) findViewById(R.id.wc);
        sub_total = (TextView) findViewById(R.id.sub_total);
        grand_total = (TextView) findViewById(R.id.grand_total);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        order_id.setText(orders.id);
        date.setText(orders.date);
        payment_status.setText(orders.payment_status);
        payment_type.setText(orders.payment_method);
        delivery_status.setText(orders.delivery_status);
        fname.setText(orders.fname);
        address.setText(orders.address);
        phone.setText(orders.phone);
        email.setText(orders.email);
        title.setText(orders.product_name);
        quantity.setText(orders.quantity);
        price.setText(orders.product_price);
        delivery_charges.setText(orders.delivery_charges + "KD");
        sub_total.setText(orders.price +"KD");
        grand_total.setText(orders.total + "KD");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailPage.this.onBackPressed();
            }
        });
    }
}
