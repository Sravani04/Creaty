//package com.example.yellowsoft.phonecase;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.ColorFilter;
//import android.graphics.LightingColorFilter;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.support.v4.view.PagerAdapter;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
///**
// * Created by yellowsoft on 18/10/17.
// */
//
//public class ProductDetailAdapter extends PagerAdapter {
//    Context context;
//    LayoutInflater layoutInflater;
//    ArrayList<Integer> images;
//    ProductDetail activity;
//    Products products;
//
//
//    public ProductDetailAdapter(Context context,Products products,ProductDetail activity) {
//        this.context = context;
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.products = products;
//        this.activity = activity;
//    }
//
//    @Override
//    public int getCount() {
//        return products.images.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == ((LinearLayout) object);
//    }
//
//    @Override
//    public Object instantiateItem(final ViewGroup container, final int position) {
//        View itemView = layoutInflater.inflate(R.layout.product_items, container, false);
//        final ImageView banner_image= (ImageView) itemView.findViewById(R.id.product_image);
//        activity.product_price.setText(products.price);
//        activity.description.setText(products.description);
//        activity.continue_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (products.customization.equals("Yes")) {
//                    Intent intent = new Intent(context, CaseLayoutEffectActivity.class);
//                    intent.putExtra("products", products);
//                    context.startActivity(intent);
//                }else if (products.customization.equals("No")){
//                    Intent intent = new Intent(context,CartPage.class);
//                    context.startActivity(intent);
//                }
//            }
//        });
//        activity.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Picasso.with(context).load(products.images.get(i).image).placeholder(R.drawable.placeholder).into(banner_image);
//                    Log.e("imagepath", products.images.get(position).image);
//            }
//        });
//
//        activity.gridView.setNumColumns(products.images.size());
//        container.addView(itemView);
//        return itemView;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((LinearLayout) object);
//    }
//}
//
