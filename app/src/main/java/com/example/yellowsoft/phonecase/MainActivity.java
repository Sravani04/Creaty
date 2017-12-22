package com.example.yellowsoft.phonecase;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView menu_btn;
    ViewPager viewPager;
    MainActivityAdapter adapter;
    ArrayList<Integer> images;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    RelativeLayout design_btn,collection_btn;
    ImageView previous_btn,next_btn,cart_btn;
    Uri bitmapUri;
    ArrayList<Banners> bannersfrom_api;
    TextView cart_items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    LinearLayout drawerView;
    RelativeLayout mainView;
    TextView home_btn,coll_btn,profile_btn,orders_btn;


    private void openNavigation(){

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))

            mDrawerLayout.closeDrawer(GravityCompat.START,true);
        else
            mDrawerLayout.openDrawer(GravityCompat.START,true);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
      //  case_btn = (LinearLayout) findViewById(R.id.case_btn);
        design_btn = (RelativeLayout) findViewById(R.id.design_btn);
        collection_btn = (RelativeLayout) findViewById(R.id.collection_btn);
        previous_btn = (ImageView) findViewById(R.id.previous_btn);
        next_btn = (ImageView) findViewById(R.id.next_btn);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        cart_items = (TextView) findViewById(R.id.cart_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        home_btn = (TextView) findViewById(R.id.home_btn);
        coll_btn = (TextView) findViewById(R.id.coll_btn);
        profile_btn = (TextView) findViewById(R.id.profile_btn);
        orders_btn = (TextView) findViewById(R.id.orders_btn);

        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        drawerView = (LinearLayout) findViewById(R.id.drawerView);
        mainView = (RelativeLayout) findViewById(R.id.mainView);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();

            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
//                if (Session.GetLang(MainActivity.this).equals("en")){
//                    mainView.setTranslationX(slideOffset * drawerView.getWidth());
//                }else {
//                    mainView.setTranslationX(-slideOffset * drawerView.getWidth());
//                }
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();


            }
        };


        bannersfrom_api = new ArrayList<>();

        images = new ArrayList<>();
        images.add(R.drawable.image14);
        images.add(R.drawable.image7);
        images.add(R.drawable.image12);
        adapter = new MainActivityAdapter(this,bannersfrom_api);
        viewPager.setAdapter(adapter);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == bannersfrom_api.size() +1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };


        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CartPage.class);
                startActivity(intent);
            }
        });

        cart_items.setText(String.valueOf(Session.GetCartProducts(this).size()));
        Log.e("items",String.valueOf(Session.GetCartProducts(this).size()));



        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        design_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProductsActivity.class);
                intent.putExtra("custom","1");
                startActivity(intent);
            }
        });

        collection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CollocationActivity.class);
                intent.putExtra("custom","0");
                startActivity(intent);
            }
        });


        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavigation();
            }
        });


        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
            }
        });



        coll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CollocationActivity.class);
                intent.putExtra("custom","0");
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START, true);

            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(MainActivity.this).equals("-1")) {
                    Intent intent = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(MainActivity.this, MyProfilePage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START, true);
                }

            }
        });

        orders_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(MainActivity.this).equals("-1")) {
                    Intent intent = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(MainActivity.this, OrdersPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START, true);
                }
            }
        });


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewPager.setCurrentItem(getItem(+1),true);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewPager.setCurrentItem(getItem(-1),true);
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });


        if(images.size()<=1){
            previous_btn.setVisibility(View.GONE);
            next_btn.setVisibility(View.GONE);
        }else {
            previous_btn.setVisibility(View.GONE);
            next_btn.setVisibility(View.VISIBLE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    previous_btn.setVisibility(View.GONE);
                }else {
                    previous_btn.setVisibility(View.VISIBLE);
                }

                if (position == adapter.getCount()-1){
                    next_btn.setVisibility(View.GONE);
                }else {
                    next_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


       get_banners();
    }


    public void get_banners(){
        final KProgressHUD hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setMaxProgress(100)
                .show();
        Ion.with(this)
                .load(Session.SERVER_URL+"banners.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hud.dismiss();
                            Log.e("banners",result.toString());
                            for (int i=0;i<result.size();i++){
                                Banners banners = new Banners(result.get(i).getAsJsonObject(),MainActivity.this);
                                bannersfrom_api.add(banners);
                            }
                            adapter.notifyDataSetChanged();

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }








}
