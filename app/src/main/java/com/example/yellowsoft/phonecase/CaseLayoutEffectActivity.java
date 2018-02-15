package com.example.yellowsoft.phonecase;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.net.Uri;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedadeltito.photoeditorsdk.PhotoEditorSDK;
import com.eminayar.panter.DialogType;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.interfaces.OnSingleCallbackConfirmListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.zip.Inflater;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yellowsoft on 24/10/17.
 */

public class CaseLayoutEffectActivity extends Activity implements GLSurfaceView.Renderer {

    private RecyclerView recList;
    int mCurrentEffect;
    public GLSurfaceView mEffectView;
    public int[] mTextures = new int[2];
    private EffectContext mEffectContext;
    private Effect mEffect;
    private TextureRenderer mTexRenderer = new TextureRenderer();
    private int mImageWidth;
    private int mImageHeight;
    private boolean mInitialized = false;
    private volatile boolean saveFrame;
    public ImageView effect_btn, layout_btn, camera_btn, back_btn;
    public LinearLayout hidden_rcview, hidden_layout, collage_view, drop_receiver, bottom_left;
    private TextView button;
    String bitmapPath;
    Uri bitmapUri;
    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    public ImageView myimage1, myimage2,myimage3,myimage4, back_image, top, cart_btn;
    private TextView addtext_btn;
    private int colorCodeTextView = -1;
    private ArrayList<Integer> colorPickerColors;
    private PhotoEditorSDK photoEditorSDK;
    private RelativeLayout parentImageRelativeLayout;
    private ImageView photoEditImageView;
    FloatingActionButton fab;
    private boolean isOpen = false;
    private RelativeLayout layoutContent;
    private LinearLayout layoutButtons, place_order;
    private LinearLayout layoutMain;
    private static final int REQUEST_CODE = 1;
    String result;
    RecyclerView recyclerView;
    CartAdapter cartPageAdapter;
    TextView add_to_cart;
    float x, y, z, last_x, last_y, last_z;
    long lastUpdate;
    private static final int SHAKE_THRESHOLD = 800;
    SensorManager sensorMgr;
    EditText addTextEditText;
    Products products;
    float total_cart_price = 0.0f;
    TextView total_price;
    ArrayList<Products> productsfrom_api;
    EditText coupon_text;
    TextView check_btn, discount_value, total_discount_price, shipping_price;
    LinearLayout line4, discount, line5, after_discount;
    TextView product_title, cart_items;
    Float grandtotal;
    CaseLayoutEffectActivity activity;
     int x1, y1,w=100,  h=100;
    GLES10 mGL;
    RecyclerView rv;
    ImageAdapter imageAdapter;
    ArrayList<MyImage> images;
    boolean capture;
    Bitmap mBitmap;
    int image_position;
    int text_position;
    Bitmap bitmap;







    public void setCurrentEffect(int effect) {
        mCurrentEffect = effect;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example1);
        mEffectView = (GLSurfaceView) findViewById(R.id.effectsview);
        mEffectView.setEGLContextClientVersion(2);
        mEffectView.setRenderer(this);
        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mEffectView.setZOrderOnTop(false);

        mCurrentEffect = 0;
        //back_image = (ImageView) findViewById(R.id.back_image);

        collage_view = (LinearLayout) findViewById(R.id.collage_view);
        hidden_layout = (LinearLayout) findViewById(R.id.hidden_layout);
        camera_btn = (ImageView) findViewById(R.id.camera_btn);
        button = (TextView) findViewById(R.id.button);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        effect_btn = (ImageView) findViewById(R.id.effect_btn);
        hidden_rcview = (LinearLayout) findViewById(R.id.hidden_rcview);
        drop_receiver = (LinearLayout) findViewById(R.id.drop_receiver);
        photoEditImageView = (ImageView) findViewById(R.id.photo_edit_iv);
        top = (ImageView) findViewById(R.id.top);
        back_image = (ImageView) findViewById(R.id.back_image);
        addtext_btn = (TextView) findViewById(R.id.addtext_btn);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);
        layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        cart_btn = (ImageView) findViewById(R.id.cart_btn);
        place_order = (LinearLayout) findViewById(R.id.place_order);
        line4 = (LinearLayout) findViewById(R.id.line4);
        discount = (LinearLayout) findViewById(R.id.discount);
        line5 = (LinearLayout) findViewById(R.id.line5);
        after_discount = (LinearLayout) findViewById(R.id.after_discount);
        coupon_text = (EditText) findViewById(R.id.coupon_text);
        check_btn = (TextView) findViewById(R.id.check_btn);
        product_title = (TextView) findViewById(R.id.product_title);
        cart_items = (TextView) findViewById(R.id.cart_items);
        shipping_price = (TextView) findViewById(R.id.shipping_price);
        rv = (RecyclerView) findViewById(R.id.main_list_view);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
//        add_to_cart = (TextView) findViewById(R.id.add_to_cart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        productsfrom_api = new ArrayList<>();
        images = new ArrayList<>();

        imageAdapter = new ImageAdapter(this, images,this);
        rv.setAdapter(imageAdapter);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        rv.addItemDecoration(itemDecoration);

//        rv.setLayoutManager(new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,true));
//        int spanCount = 4; // 3 columns
//        int spacing = dpToPx(1); // 50px
//        boolean includeEdge = false;
//        rv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));






        View addTextPopupWindowRootView = LayoutInflater.from(this).inflate(R.layout.add_text_popup_window, null);
        addTextEditText = (EditText) addTextPopupWindowRootView.findViewById(R.id.add_text_edit_text);






        recList = (RecyclerView) findViewById(R.id.rc_filter);
        recList.setHasFixedSize(true);
        recList.setLayoutManager(layoutManager);

        FilterAdapterFactory filterAdapter = new FilterAdapterFactory(this,CaseLayoutEffectActivity.this);
        recList.setAdapter(filterAdapter);

        recList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setCurrentEffect(position);
                mEffectView.requestRender();
                //setCurrentEffect(mEffectView.getRenderMode());
            }
        }));







        cart_items.setText(String.valueOf(Session.GetCartProducts(this).size()));


        if (getIntent() != null && getIntent().hasExtra("products")) {
            products = (Products) getIntent().getSerializableExtra("products");
        }

        product_title.setText(products.title);

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(CaseLayoutEffectActivity.this).equals("-1")) {
                    Intent intent = new Intent(CaseLayoutEffectActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(CaseLayoutEffectActivity.this, CheckoutPage.class);
                    intent.putExtra("total", total_discount_price.getText().toString());
                    startActivity(intent);
                }
            }
        });


        button.setEnabled(false);


        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //effect_btn.callOnClick();
                //hidden_rcview.setVisibility(View.VISIBLE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        return;
                    }
                }
//                mInitialized = false;
//                mEffectView.setDrawingCacheEnabled(true);
//                mEffectView.buildDrawingCache(true);
//                final Bitmap bitmap = Bitmap.createBitmap(mEffectView.getDrawingCache());
//                mEffectView.setDrawingCacheEnabled(false);
//                mEffectView.destroyDrawingCache();
//                myimage2.setImageBitmap(bitmap);

                  mEffectView.setVisibility(View.GONE);
                  mInitialized = false;
                  String root = Environment.getExternalStorageDirectory().toString();
                  File myDir = new File(root + "/saved_images");
                  myDir.mkdirs();
                  Random generator = new Random();
                  int n = 10000;
                  n = generator.nextInt(n);
                  String fname = "temp_image_"+n+".jpg";
                  File file = new File (myDir, fname);
                  File folder = new File(Environment.getExternalStorageDirectory(), ".nomedia");
                  folder.mkdir();
                  if (file.exists ()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap = getBitmapFromView(collage_view);
                     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    Log.i("TAG", "Image SAVED=========="+file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                    bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), getBitmapFromView(collage_view), "collage_share", null);
                    bitmapPath = file.getAbsolutePath();
                    Log.e("bitmapPath", bitmapPath);
                    // mEffectView.setVisibility(View.VISIBLE);
                    if (bitmapPath != null) {
                        bitmapUri = Uri.parse(bitmapPath);
                        getText();
                    }



//                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    if (shouldShowRequestPermissionRationale(
//                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    }
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                    return;
//                }
//                mEffectView.setVisibility(View.GONE);
//                effect_btn.callOnClick();
//                hidden_rcview.setVisibility(View.VISIBLE);
//                mInitialized = false;
//                bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), getBitmapFromView(collage_view,photoEditImageView,mEffectView),"collage_share", null);
//                mEffectView.setVisibility(View.VISIBLE);
//                if(bitmapPath!= null) {
//                    bitmapUri = Uri.parse(bitmapPath);
//                    Runtime.getRuntime().maxMemory();
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("image/png");
//                    intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
//                    startActivity(Intent.createChooser(intent, "Share via"));
//                }


            }


        });


        if (Session.GetCartProducts(this).size() == 0){
            Log.e("cart","disabled");
            cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CaseLayoutEffectActivity.this,CartEmpty.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CaseLayoutEffectActivity.this, CartPage.class);
                    startActivity(intent);
                }
            });
        }




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaseLayoutEffectActivity.this.onBackPressed();
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.headercolor), android.graphics.PorterDuff.Mode.MULTIPLY);
        Picasso.with(this).load(products.full_image).into(back_image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {

            }
        });

        Picasso.with(this).load(products.transparent_image).into(top);
        Log.e("transparent_image", products.transparent_image);

        camera_btn.setBackgroundColor(Color.parseColor("#E7C943"));
        addtext_btn.setBackgroundColor(Color.parseColor("#ffffff"));
        effect_btn.setBackgroundColor(Color.parseColor("#ffffff"));
        if (hidden_layout.isShown()) {
            hidden_layout.setVisibility(View.GONE);
        } else {
            hidden_layout.setVisibility(View.VISIBLE);
            hidden_rcview.setVisibility(View.GONE);
        }

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera_btn.setBackgroundColor(Color.parseColor("#E7C943"));
                addtext_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                effect_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                addtext_btn.setTextColor(Color.parseColor("#000000"));
                if (hidden_layout.isShown()) {
                    hidden_layout.setVisibility(View.GONE);
                } else {
                    hidden_layout.setVisibility(View.VISIBLE);
                    hidden_rcview.setVisibility(View.GONE);
                }

            }
        });

        effect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effect_btn.setBackgroundColor(Color.parseColor("#E7C943"));
                camera_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                addtext_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                addtext_btn.setTextColor(Color.parseColor("#000000"));

                if (hidden_rcview.isShown()) {
                    hidden_rcview.setVisibility(View.GONE);
                } else {
                    hidden_rcview.setVisibility(View.VISIBLE);
                    hidden_layout.setVisibility(View.GONE);
                    mEffectView.setVisibility(View.VISIBLE);
                    mInitialized = false;
                    mEffectView.invalidate();
                }
            }
        });

        // findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
        //findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.myimage3).setOnTouchListener(new MyTouchListener());
      //  findViewById(R.id.myimage4).setOnTouchListener(new MyTouchListener());


        total_price = (TextView) findViewById(R.id.total_price);

//        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
//        sensorMgr.registerListener(this,
//                SensorManager.SENSOR_ACCELEROMETER,
//                SensorManager.SENSOR_DELAY_GAME);


        myimage1 = (ImageView) findViewById(R.id.myimage1);
        myimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick_image();

            }
        });

      //  myimage2 = (ImageView) findViewById(R.id.myimage2);
       // myimage3 = (ImageView) findViewById(R.id.myimage3);
        //myimage4 = (ImageView) findViewById(R.id.myimage4);
        //myimage2.setOnTouchListener(new MyTouchListener());
        //myimage3.setOnTouchListener(new MyTouchListener());
        //myimage4.setOnTouchListener(new MyTouchListener());

        //myimage2.setVisibility(View.GONE);
//        if (myimage2.isShown()) {
//            myimage2.setVisibility(View.VISIBLE);
//            myimage3.setVisibility(View.VISIBLE);
//            myimage4.setVisibility(View.VISIBLE);
//        } else {
//            myimage2.setVisibility(View.GONE);
//            myimage3.setVisibility(View.GONE);
//            myimage4.setVisibility(View.GONE);
//        }




        //findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
        //findViewById(R.id.topright).setOnDragListener(new MyDragListener());
        bottom_left = (LinearLayout) findViewById(R.id.bottomleft);
        bottom_left.setOnDragListener(new MyDragListener());

        drop_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity(backupimages.get(image_position))
                        .start(CaseLayoutEffectActivity.this);
            }
        });
        //findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());

        findViewById(R.id.holder_one).setOnDragListener(new MyDragListener());
       // findViewById(R.id.holder_two).setOnDragListener(new MyDragListener());
        //findViewById(R.id.holder_three).setOnDragListener(new MyDragListener());
        //findViewById(R.id.holder_four).setOnDragListener(new MyDragListener());
        findViewById(R.id.drop_receiver).setOnDragListener(new MyDragListener());


        RelativeLayout deleteRelativeLayout = (RelativeLayout) findViewById(R.id.delete_rl);
        parentImageRelativeLayout = (RelativeLayout) findViewById(R.id.parent);


        photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(CaseLayoutEffectActivity.this)
                .parentView(parentImageRelativeLayout) // add parent image view
                .childView(photoEditImageView) // add the desired image view
                .deleteView(deleteRelativeLayout)// add the deleted view that will appear during the movement of the views
                .textView(addtext_btn)
                //.brushDrawingView(brushDrawingView) // add the brush drawing view that is responsible for drawing on the image view
                .buildPhotoEditorSDK(); // build photo editor sdk
        //photoEditorSDK.setOnPhotoEditorSDKListener((OnPhotoEditorSDKListener) this);

        colorPickerColors = new ArrayList<>();
        colorPickerColors.add(getResources().getColor(R.color.black));
        colorPickerColors.add(getResources().getColor(R.color.blue_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.brown_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.green_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.orange_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.red_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.red_orange_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.sky_blue_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.violet_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.white));
        colorPickerColors.add(getResources().getColor(R.color.yellow_color_picker));
        colorPickerColors.add(getResources().getColor(R.color.yellow_green_color_picker));

        addtext_btn.setEnabled(false);
        effect_btn.setEnabled(false);

        line4.setVisibility(View.GONE);
        discount.setVisibility(View.GONE);
        line5.setVisibility(View.GONE);
        after_discount.setVisibility(View.GONE);


        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_coupon();
            }
        });


        addtext_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtext_btn.setBackgroundColor(Color.parseColor("#E7C943"));
                addtext_btn.setTextColor(Color.parseColor("#ffffff"));
                camera_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                effect_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                openAddTextPopupWindow("", -1);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClick();
            }
        });


        fab.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startRawX;
            float distanceX;

            float startY;
            float startRawY;
            float distanceY;

            int lastAction;


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = view.getX() - motionEvent.getRawX();
                        startRawX = motionEvent.getRawX();
                        startY = view.getY() - motionEvent.getRawY();
                        startRawY = motionEvent.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setX(motionEvent.getRawX() + startX);
                        view.setY(motionEvent.getRawY() + startY);
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_UP:
                        distanceX = motionEvent.getRawX() - startRawX;
                        view.setX(motionEvent.getRawX() + startX);
                        view.setY(motionEvent.getRawY() + startY);

                        if (Math.abs(distanceX) < 10) {
                            viewMenu();
                        }
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.basket_list);
        cartPageAdapter = new CartAdapter(CaseLayoutEffectActivity.this, productsfrom_api, this);
        recyclerView.setAdapter(cartPageAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        get_products();

    }


    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics =CaseLayoutEffectActivity.this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }




    void onFabClick() {
        //sheetLayout.expandFab();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void viewMenu() {

        if (!isOpen) {

            int x = layoutContent.getRight();
            int y = layoutContent.getBottom();

            int startRadius = 0;
            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.headercolor, null)));
            fab.setImageResource(R.drawable.close_icon);

            Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);

            layoutButtons.setVisibility(View.VISIBLE);
            anim.start();

            isOpen = true;

        } else {

            int x = layoutButtons.getRight();
            int y = layoutButtons.getBottom();

            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = 0;

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.headercolor, null)));
            fab.setImageResource(R.drawable.add_cart_btn);

            Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

            isOpen = false;
        }
    }


    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, ProductDetail.class);
        startActivityForResult(intent, REQUEST_CODE);
    }


    private void openAddTextPopupWindow(String text, int colorCode) {
        colorCodeTextView = colorCode;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addTextPopupWindowRootView = inflater.inflate(R.layout.add_text_popup_window, null);
        addTextEditText = (EditText) addTextPopupWindowRootView.findViewById(R.id.add_text_edit_text);
        TextView addTextDoneTextView = (TextView) addTextPopupWindowRootView.findViewById(R.id.add_text_done_tv);
        RecyclerView addTextColorPickerRecyclerView = (RecyclerView) addTextPopupWindowRootView.findViewById(R.id.add_text_color_picker_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CaseLayoutEffectActivity.this, LinearLayoutManager.HORIZONTAL, false);
        addTextColorPickerRecyclerView.setLayoutManager(layoutManager);
        addTextColorPickerRecyclerView.setHasFixedSize(true);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(CaseLayoutEffectActivity.this, colorPickerColors);
        colorPickerAdapter.setOnColorPickerClickListener(new ColorPickerAdapter.OnColorPickerClickListener() {
            @Override
            public void onColorPickerClickListener(int colorCode) {
                addTextEditText.setTextColor(colorCode);
                colorCodeTextView = colorCode;
            }
        });
        addTextColorPickerRecyclerView.setAdapter(colorPickerAdapter);
        if (stringIsNotEmpty(text)) {
            addTextEditText.setText(text);
            addTextEditText.setTextColor(colorCode == -1 ? getResources().getColor(R.color.white) : colorCode);
        }
        final PopupWindow pop = new PopupWindow(CaseLayoutEffectActivity.this);
        pop.setContentView(addTextPopupWindowRootView);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(null);
        pop.showAtLocation(addTextPopupWindowRootView, Gravity.CENTER, 0, 0);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        addTextDoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText(addTextEditText.getText().toString(), colorCodeTextView);
                Log.e("text", addTextEditText.getText().toString());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                pop.dismiss();
                addtext_btn.setEnabled(false);
            }
        });


    }

    public void getText(){
        Intent intent = new Intent(CaseLayoutEffectActivity.this, PreviewPage.class);
        intent.putExtra("image", bitmapUri.toString());
        intent.putExtra("image_path", bitmapPath);
        intent.putExtra("products", products);
        intent.putExtra("gallery_path",selected_image_path);
        if (addTextEditText.getText().toString().equals("")){
            intent.putExtra("text","");
        }else {
            intent.putExtra("text", addTextEditText.getText().toString());
        }
        Log.e("prrr", products.toString());
        Log.e("gallerypPath",selected_image_path);
        startActivity(intent);

    }


    private boolean stringIsNotEmpty(String string) {
        if (string != null && !string.equals("null")) {
            if (!string.trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    private void addText(String text, int colorCodeTextView) {
        photoEditorSDK.addText(text, colorCodeTextView);
    }


    public void pick_image() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                return;
            }
        }

        final String[] cases = {"Camera", "Gallery", "Choose from Library"};
        final PanterDialog panterDialog = new PanterDialog(CaseLayoutEffectActivity.this);
        panterDialog.setHeaderBackground(R.color.white);
        panterDialog.setTitle("Select Image");
        panterDialog.setDialogType(DialogType.SINGLECHOICE);
        panterDialog.setPositive("OK");
        panterDialog.setNegative("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panterDialog.dismiss();
            }
        });
        panterDialog.items(cases, new OnSingleCallbackConfirmListener() {
            @Override
            public void onSingleCallbackConfirmed(PanterDialog dialog, int pos, String text) {
                if (cases[pos].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (cases[pos].equals("Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, SELECT_FILE);
                } else if (cases[pos].equals("Choose from Library")) {
                    Intent intent = new Intent(CaseLayoutEffectActivity.this, GalleryActivity.class);
                    startActivityForResult(intent, 2);
                }
            }
        });
        panterDialog.isCancelable(false);
        panterDialog.show();
    }



    String selected_image_path = "";
    Uri selected_image_uri;
    ArrayList<Uri> backupimages = new ArrayList<>();

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && imageReturnedIntent != null) {
                Bundle bundle = imageReturnedIntent.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                Uri selectedImage = getImageUri(CaseLayoutEffectActivity.this, bmp);
                selected_image_uri = selectedImage;
                backupimages.add(selected_image_uri);
                selected_image_path = getRealPathFromURI(selectedImage);
                MyImage image = new MyImage();
                image.setId(String.valueOf(imageAdapter.getItemCount()));
                image.setPath(Uri.parse(selected_image_path));
                Log.e("load",selected_image_path);
                images.add(image);
                button.setEnabled(true);
                addtext_btn.setEnabled(true);
                effect_btn.setEnabled(true);
                Toast.makeText(CaseLayoutEffectActivity.this, "Here " + getRealPathFromURI(selectedImage), Toast.LENGTH_LONG).show();
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImage = imageReturnedIntent.getData();
                File f = new File(getRealPathFromURI(selectedImage));
                Drawable d = Drawable.createFromPath(f.getPath());
                bottom_left.setBackground(d);
                MyImage image = new MyImage();
                image.setId(String.valueOf(imageAdapter.getItemCount()));
                image.setPath(selectedImage);
                Log.e("load",selectedImage.getPath());
                images.add(image);
                imageAdapter.notifyDataSetChanged();
                File new_file = new File(selectedImage.getPath());
                selected_image_uri = selectedImage;
                backupimages.add(selected_image_uri);
                selected_image_path = getRealPathFromURI(selectedImage);
                button.setEnabled(true);
                addtext_btn.setEnabled(true);
                effect_btn.setEnabled(true);
                Log.e("selected_image_path", selected_image_path);
            } else if (requestCode == 2) {

                if (resultCode == Activity.RESULT_OK) {

                    // do something with the result
                    if (imageReturnedIntent != null && imageReturnedIntent.hasExtra("image")) {
                        String image1 = imageReturnedIntent.getExtras().getString("image");
                        try {
                            URL url = new URL(image1);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            Uri selectedImage = getImageUri(CaseLayoutEffectActivity.this, bmp);
                            File f = new File(getRealPathFromURI(selectedImage));
                            Drawable d = Drawable.createFromPath(f.getPath());
                            bottom_left.setBackground(d);
                            MyImage image = new MyImage();
                            image.setId(String.valueOf(imageAdapter.getItemCount()));
                            image.setPath(selectedImage);
                            Log.e("load", selectedImage.getPath());
                            images.add(image);
                            imageAdapter.notifyDataSetChanged();
                            selected_image_uri = selectedImage;
                            backupimages.add(selected_image_uri);
                            selected_image_path = getRealPathFromURI(selectedImage);
                            button.setEnabled(true);
                            addtext_btn.setEnabled(true);
                            effect_btn.setEnabled(true);
                            Log.e("cate", bmp.toString());
                            Log.e("selec",selected_image_path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }


                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // some stuff that will happen if there's no result
                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
                    if (resultCode == Activity.RESULT_OK) {
                            Uri resultUri = result.getUri();
                            Log.e("po", resultUri.getPath());
                            mEffectView.setVisibility(View.GONE);
                            File f = new File(getRealPathFromURI(resultUri));
                            Drawable d = Drawable.createFromPath(f.getPath());
                            bottom_left.setBackground(d);

//                      myimage2.setImageURI(resultUri);
                            button.setEnabled(true);
                            addtext_btn.setEnabled(true);
                            effect_btn.setEnabled(true);
                            camera_btn.callOnClick();

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
            }

        }
    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }





    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap


        return returnedBitmap;
    }


//    @Override
//    public void onSensorChanged(int sensor, float[] values) {
//
//        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
//            long curTime = System.currentTimeMillis();
//            // only allow one update every 100ms.
//            if ((curTime - lastUpdate) > 100) {
//                long diffTime = (curTime - lastUpdate);
//                lastUpdate = curTime;
//
//                x = values[SensorManager.DATA_X];
//                y = values[SensorManager.DATA_Y];
//                z = values[SensorManager.DATA_Z];
//
//                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
//
//                if (speed > SHAKE_THRESHOLD) {
//                    if (Session.get_shake_status(CaseLayoutEffectActivity.this)) {
//                        Log.e("com", "coming or not");
//                        device_shaked();
////                        if(tabsAdapter.homeFragment!=null){
////                            tabsAdapter.homeFragment.device_shaked();
////                        }
//                    }
//                }
//                last_x = x;
//                last_y = y;
//                last_z = z;
//            }
//        }
//
//    }

//    @Override
//    public void onAccuracyChanged(int i, int i1) {
//
//    }
//
//    public void device_shaked() {
//        if (top.getVisibility() == View.VISIBLE) {
//            //myimage2.setVisibility(View.VISIBLE);
//            // myimage2.setImageResource(R.drawable.placeholder);
//            File imgFile = new File(selected_image_path);
//            if (imgFile.exists()) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//             //   myimage2.setImageBitmap(myBitmap);
//            }
//            top.setVisibility(View.GONE);
//        }
//    }




    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.border);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    // v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    // v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    bottom_left.addView(view);
                    view.setVisibility(View.VISIBLE);
                    imageAdapter.notifyDataSetChanged();
                   // myimage2.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }


    public final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                Log.e("drag", "ontouc");
                return true;
            } else {
                return false;
            }
        }
    }



    public void loadTextures() {
        // Generate textures
        GLES20.glGenTextures(2, mTextures, 0);

        // Load input bitmap
        Bitmap bitmap = getBitmapFromView(bottom_left);
        mImageWidth = bitmap.getWidth();
        mImageHeight = bitmap.getHeight();
        mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);


        // Upload to texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // Set texture parameters
        GLToolbox.initTexParams();
        bitmap.recycle();
    }


    private void initEffect() {
        EffectFactory effectFactory = mEffectContext.getFactory();
        if (mEffect != null) {
            mEffect.release();
        }
        /**
         * Initialize the correct effect based on the selected menu/action item
         */
        switch (mCurrentEffect) {

            case 0:
                break;

            case 1:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_AUTOFIX);
                mEffect.setParameter("scale", 0.5f);
                break;

            case 2:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BLACKWHITE);
                mEffect.setParameter("black", .1f);
                mEffect.setParameter("white", .7f);
                break;

            case 3:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BRIGHTNESS);
                mEffect.setParameter("brightness", 2.0f);
                break;

            case 4:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CONTRAST);
                mEffect.setParameter("contrast", 1.4f);
                break;

            case 5:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CROSSPROCESS);
                break;

            case 6:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
                break;

            case 7:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DUOTONE);
                mEffect.setParameter("first_color", Color.YELLOW);
                mEffect.setParameter("second_color", Color.DKGRAY);
                break;

            case 8:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FILLLIGHT);
                mEffect.setParameter("strength", .8f);
                break;

            case 9:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FISHEYE);
                mEffect.setParameter("scale", .5f);
                break;

            case 10:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
                mEffect.setParameter("vertical", true);
                break;

            case 11:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
                mEffect.setParameter("horizontal", true);
                break;

            case 12:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAIN);
                mEffect.setParameter("strength", 1.0f);
                break;

            case 13:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
                break;

            case 14:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_LOMOISH);
                break;

            case 15:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_NEGATIVE);
                break;

            case 16:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_POSTERIZE);
                break;

            case 17:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_ROTATE);
                mEffect.setParameter("angle", 180);
                break;

            case 18:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SATURATE);
                mEffect.setParameter("scale", .5f);
                break;

            case 19:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SEPIA);
                break;

            case 20:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SHARPEN);
                break;

            case 21:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_TEMPERATURE);
                mEffect.setParameter("scale", .9f);
                break;

            case 22:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_TINT);
                mEffect.setParameter("tint", Color.MAGENTA);
                break;

            case 23:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_VIGNETTE);
                mEffect.setParameter("scale", .5f);
                break;

            default:
                break;

        }
    }

    private void applyEffect() {
        mEffect.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1]);


    }


    private void renderResult() {
        if (mCurrentEffect != 0) {
            // if no effect is chosen, just render the original bitmap
            mTexRenderer.renderTexture(mTextures[1]);
        } else {
            saveFrame = true;
            // render the result of applyEffect()
            mTexRenderer.renderTexture(mTextures[0]);
        }


    }


    @Override
    public void onDrawFrame(GL10 gl) {

        Log.e("testing","check");


        if (!mInitialized) {
            // Only need to do this once
            mEffectContext = EffectContext.createWithCurrentGlContext();
            mTexRenderer.init();
            loadTextures();
            mInitialized = true;

        }
        if (mCurrentEffect != 0) {
            // if an effect is chosen initialize it and apply it to the texture
            initEffect();
            applyEffect();

        }

        renderResult();
        if (saveFrame) {
//            saveFrame = false;
//            takeScreenshot(gl);
//            return;
            saveBitmap(takeScreenshot(gl));

        }



    }








    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (mTexRenderer != null) {
            mTexRenderer.updateViewSize(width, height);
        }
    }


    private void saveBitmap(Bitmap bitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "temp_image.jpg";
        File file = new File (myDir, fname);
        File folder = new File(Environment.getExternalStorageDirectory(), ".nomedia");
        folder.mkdir();
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Log.i("TAG", "Image SAVED=========="+file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public Bitmap takeScreenshot(GL10 gl) {
        final int mWidth = mEffectView.getWidth();
        final int mHeight = mEffectView.getHeight();
        IntBuffer ib = IntBuffer.allocate(mWidth * mHeight);
        IntBuffer ibt = IntBuffer.allocate(mWidth * mHeight);


        gl.glReadPixels(0, 0, mWidth, mHeight, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);

        // Convert upside down mirror-reversed image to right-side up normal
        // image.
        for (int i = 0; i < mHeight; i++) {
            for (int j = 0; j < mWidth; j++) {
                ibt.put((mHeight - i - 1) * mWidth + j, ib.get(i * mWidth + j));
            }
        }

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mBitmap.copyPixelsFromBuffer(ibt);
        Log.e("caa", mBitmap.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//stuff that updates ui
                bottom_left.setBackground(new BitmapDrawable(mBitmap));


            }
        });


        return mBitmap;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }


    public void get_products() {
        Log.e("cart_list", String.valueOf(Session.GetCartProducts(CaseLayoutEffectActivity.this).toString()));


        JsonArray temp;
        temp = Session.GetCartProducts(CaseLayoutEffectActivity.this);
        for (int i=0;i<temp.size();i++){
            Log.e("result",temp.toString());
            Products products = new Products(temp.get(i).getAsJsonObject(),CaseLayoutEffectActivity.this);
            productsfrom_api.add(products);
            Log.e("_price",products.price);
            total_cart_price = total_cart_price + (products.cart_quantity * Float.parseFloat(products.price));
        }
        cartPageAdapter.notifyDataSetChanged();

        Float grandtotal = Float.parseFloat(shipping_price.getText().toString());
        Log.e("price",String.valueOf(total_cart_price + 50 +"KD"));
        total_price.setText(String.valueOf(total_cart_price + 50 + "KD"));

    }


    public void calculate_total_price(){

        total_cart_price = 0.0f;
        for (int i=0;i<productsfrom_api.size();i++){
            total_cart_price = total_cart_price + (productsfrom_api.get(i).cart_quantity* Float.parseFloat(productsfrom_api.get(i).price));
        }
        grandtotal = Float.parseFloat(shipping_price.getText().toString());
        Log.e("price",String.valueOf(total_cart_price + 50 +"KD"));
        total_price.setText(String.valueOf(total_cart_price + 50 + "KD"));
    }


    public void check_coupon(){
        String coupon_string = coupon_text.getText().toString();
        if (coupon_string.equals("")){
            Toast.makeText(CaseLayoutEffectActivity.this,"Please Enter Coupon Code",Toast.LENGTH_SHORT).show();
            coupon_text.requestFocus();
        }else {
            final KProgressHUD hud = KProgressHUD.create(CaseLayoutEffectActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setMaxProgress(100)
                    .show();
            Ion.with(this)
                    .load(Session.SERVER_URL + "coupon-check.php")
                    .setBodyParameter("coupon", coupon_string)
                    .setBodyParameter("cart_total", String.valueOf(total_cart_price))
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                hud.dismiss();
                                Log.e("coupon", result.toString());
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(CaseLayoutEffectActivity.this, result.get("discount_value").getAsString(), Toast.LENGTH_SHORT).show();
                                    line4.setVisibility(View.VISIBLE);
                                    discount.setVisibility(View.VISIBLE);
                                    line5.setVisibility(View.VISIBLE);
                                    after_discount.setVisibility(View.VISIBLE);
                                    discount_value.setText(result.get("discount_value").getAsString());
                                  //  Float totalprice = Float.parseFloat(total_price.getText().toString());
                                    Float discountprice = Float.parseFloat(discount_value.getText().toString());

                                    total_discount_price.setText(String.valueOf(total_cart_price + 50 - discountprice) +""+"KD");
                                } else {
                                    Toast.makeText(CaseLayoutEffectActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        }
                    });
        }
    }

//    950292



}

