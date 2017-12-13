//package com.example.yellowsoft.phonecase;
//
//import android.Manifest;
//import android.animation.Animator;
//import android.app.Activity;
//import android.content.ClipData;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.res.ColorStateList;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.hardware.SensorListener;
//import android.hardware.SensorManager;
//import android.media.effect.Effect;
//import android.media.effect.EffectContext;
//import android.media.effect.EffectFactory;
//import android.net.Uri;
//import android.opengl.GLES10;
//import android.opengl.GLES20;
//import android.opengl.GLException;
//import android.opengl.GLSurfaceView;
//import android.opengl.GLUtils;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.annotation.RequiresApi;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.content.res.ResourcesCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.DragEvent;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewAnimationUtils;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.ahmedadeltito.photoeditorsdk.PhotoEditorSDK;
//import com.eminayar.panter.DialogType;
//import com.eminayar.panter.PanterDialog;
//import com.eminayar.panter.interfaces.OnSingleCallbackConfirmListener;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.kaopiz.kprogresshud.KProgressHUD;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.net.URL;
//import java.nio.IntBuffer;
//import java.util.ArrayList;
//
//import javax.microedition.khronos.egl.EGLConfig;
//import javax.microedition.khronos.opengles.GL10;
//
///**
// * Created by yellowsoft on 5/12/17.
// */
//
//public class ExampleActivity extends Activity implements GLSurfaceView.Renderer,SensorListener {
//
//    private RecyclerView recList;
//    int mCurrentEffect;
//    private GLSurfaceView mEffectView;
//    private int[] mTextures = new int[2];
//    private EffectContext mEffectContext;
//    private Effect mEffect;
//    private TextureRenderer mTexRenderer = new TextureRenderer();
//    private int mImageWidth;
//    private int mImageHeight;
//    private boolean mInitialized = false;
//    private volatile boolean saveFrame;
//    private ImageView effect_btn, layout_btn, camera_btn, back_btn;
//    private LinearLayout hidden_rcview, hidden_layout, collage_view, drop_receiver, bottom_left;
//    private TextView button;
//    String bitmapPath;
//    Uri bitmapUri;
//    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
//    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
//    private ImageView myimage1, myimage2,myimage3,myimage4, back_image, top, cart_btn;
//    private TextView addtext_btn;
//    private int colorCodeTextView = -1;
//    private ArrayList<Integer> colorPickerColors;
//    private PhotoEditorSDK photoEditorSDK;
//    private RelativeLayout parentImageRelativeLayout;
//    private ImageView photoEditImageView;
//    FloatingActionButton fab;
//    private boolean isOpen = false;
//    private RelativeLayout layoutContent;
//    private LinearLayout layoutButtons, place_order;
//    private LinearLayout layoutMain;
//    private static final int REQUEST_CODE = 1;
//    String result;
//    RecyclerView recyclerView;
//    CartAdapter cartPageAdapter;
//    TextView add_to_cart;
//    float x, y, z, last_x, last_y, last_z;
//    long lastUpdate;
//    private static final int SHAKE_THRESHOLD = 800;
//    SensorManager sensorMgr;
//    EditText addTextEditText;
//    Products products;
//    float total_cart_price = 0.0f;
//    TextView total_price;
//    ArrayList<Products> productsfrom_api;
//    EditText coupon_text;
//    TextView check_btn, discount_value, total_discount_price, shipping_price;
//    LinearLayout line4, discount, line5, after_discount;
//    TextView product_title, cart_items;
//    Float grandtotal;
//    CaseLayoutEffectActivity activity;
//    int x1, y1,w=100,  h=100;
//    GLES10 mGL;
//
//
//
//    public void setCurrentEffect(int effect) {
//        mCurrentEffect = effect;
//    }
//
//    @Override
//    public void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.example1);
//        mEffectView = (GLSurfaceView) findViewById(R.id.effectsview);
//        mEffectView.setEGLContextClientVersion(2);
//        mEffectView.setRenderer(this);
//        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        mEffectView.setZOrderOnTop(false);
//
//        mCurrentEffect = 0;
//        //back_image = (ImageView) findViewById(R.id.back_image);
//
//        collage_view = (LinearLayout) findViewById(R.id.collage_view);
//        hidden_layout = (LinearLayout) findViewById(R.id.hidden_layout);
//        camera_btn = (ImageView) findViewById(R.id.camera_btn);
//        button = (TextView) findViewById(R.id.button);
//        back_btn = (ImageView) findViewById(R.id.back_btn);
//        effect_btn = (ImageView) findViewById(R.id.effect_btn);
//        hidden_rcview = (LinearLayout) findViewById(R.id.hidden_rcview);
//        drop_receiver = (LinearLayout) findViewById(R.id.drop_receiver);
//        photoEditImageView = (ImageView) findViewById(R.id.photo_edit_iv);
//        top = (ImageView) findViewById(R.id.top);
//        back_image = (ImageView) findViewById(R.id.back_image);
//        addtext_btn = (TextView) findViewById(R.id.addtext_btn);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
//        layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);
//        layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
//        cart_btn = (ImageView) findViewById(R.id.cart_btn);
//        place_order = (LinearLayout) findViewById(R.id.place_order);
//        line4 = (LinearLayout) findViewById(R.id.line4);
//        discount = (LinearLayout) findViewById(R.id.discount);
//        line5 = (LinearLayout) findViewById(R.id.line5);
//        after_discount = (LinearLayout) findViewById(R.id.after_discount);
//        coupon_text = (EditText) findViewById(R.id.coupon_text);
//        check_btn = (TextView) findViewById(R.id.check_btn);
//        product_title = (TextView) findViewById(R.id.product_title);
//        cart_items = (TextView) findViewById(R.id.cart_items);
//        shipping_price = (TextView) findViewById(R.id.shipping_price);
////        add_to_cart = (TextView) findViewById(R.id.add_to_cart);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        productsfrom_api = new ArrayList<>();
//
//        recList = (RecyclerView) findViewById(R.id.rc_filter);
//        recList.setHasFixedSize(true);
//        recList.setLayoutManager(layoutManager);
//
//        FilterAdapterFactory filterAdapter = new FilterAdapterFactory(this);
//        recList.setAdapter(filterAdapter);
//
//        recList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                setCurrentEffect(position);
//                mEffectView.requestRender();
//            }
//        }));
//
//        cart_items.setText(String.valueOf(Session.GetCartProducts(this).size()));
//
//
//        if (getIntent() != null && getIntent().hasExtra("products")) {
//            products = (Products) getIntent().getSerializableExtra("products");
//        }
//
//        product_title.setText(products.title);
//
//        place_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Session.GetUserId(ExampleActivity.this).equals("-1")) {
//                    Intent intent = new Intent(ExampleActivity.this, LoginPage.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Intent intent = new Intent(ExampleActivity.this, CheckoutPage.class);
//                    intent.putExtra("total", total_discount_price.getText().toString());
//                    startActivity(intent);
//                }
//            }
//        });
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//
//
//
//                mEffectView.setVisibility(View.GONE);
//
////                    //effect_btn.callOnClick();
//                //hidden_rcview.setVisibility(View.VISIBLE);
//                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    if (shouldShowRequestPermissionRationale(
//                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    }
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                    return;
//                }
//
//
//
//
////                mInitialized = false;
////                mEffectView.setDrawingCacheEnabled(true);
////                mEffectView.buildDrawingCache(true);
////                final Bitmap bitmap = Bitmap.createBitmap(mEffectView.getDrawingCache());
////                mEffectView.setDrawingCacheEnabled(false);
////                mEffectView.destroyDrawingCache();
////                myimage2.setImageBitmap(bitmap);
//
//
//                bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), getBitmapFromView(collage_view), "collage_share", null);
//                mEffectView.setVisibility(View.VISIBLE);
//                if (bitmapPath != null) {
//                    bitmapUri = Uri.parse(bitmapPath);
//                    Intent intent = new Intent(ExampleActivity.this, PreviewPage.class);
//                    intent.putExtra("image", bitmapUri.toString());
//                    intent.putExtra("products", products);
//                    Log.e("prrr", products.toString());
//                    startActivity(intent);
//                }
//
//
////                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
////                        != PackageManager.PERMISSION_GRANTED) {
////                    if (shouldShowRequestPermissionRationale(
////                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
////                    }
////                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
////                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
////                    return;
////                }
////                mEffectView.setVisibility(View.GONE);
////                effect_btn.callOnClick();
////                hidden_rcview.setVisibility(View.VISIBLE);
////                mInitialized = false;
////                bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), getBitmapFromView(collage_view,photoEditImageView,mEffectView),"collage_share", null);
////                mEffectView.setVisibility(View.VISIBLE);
////                if(bitmapPath!= null) {
////                    bitmapUri = Uri.parse(bitmapPath);
////                    Runtime.getRuntime().maxMemory();
////                    Intent intent = new Intent(Intent.ACTION_SEND);
////                    intent.setType("image/png");
////                    intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
////                    startActivity(Intent.createChooser(intent, "Share via"));
////                }
//
//
//            }
//        });
//
//        cart_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ExampleActivity.this, CartPage.class);
//                startActivity(intent);
//            }
//        });
//
//
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ExampleActivity.this.onBackPressed();
//            }
//        });
//
//
//        Picasso.with(this).load(products.full_image).into(back_image);
//        Picasso.with(this).load(products.transparent_image).into(top);
//        Log.e("transparent_image", products.transparent_image);
//
//        camera_btn.setBackgroundColor(Color.parseColor("#E7C943"));
//        addtext_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//        effect_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//        if (hidden_layout.isShown()) {
//            hidden_layout.setVisibility(View.GONE);
//        } else {
//            hidden_layout.setVisibility(View.VISIBLE);
//            hidden_rcview.setVisibility(View.GONE);
//        }
//
//        camera_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                camera_btn.setBackgroundColor(Color.parseColor("#E7C943"));
//                addtext_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//                effect_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//                addtext_btn.setTextColor(Color.parseColor("#000000"));
//                if (hidden_layout.isShown()) {
//                    hidden_layout.setVisibility(View.GONE);
//                } else {
//                    hidden_layout.setVisibility(View.VISIBLE);
//                    hidden_rcview.setVisibility(View.GONE);
//                }
//
//            }
//        });
//
//        effect_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                effect_btn.setBackgroundColor(Color.parseColor("#E7C943"));
//                camera_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//                addtext_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//                addtext_btn.setTextColor(Color.parseColor("#000000"));
//
//                if (hidden_rcview.isShown()) {
//                    hidden_rcview.setVisibility(View.GONE);
//                } else {
//                    hidden_rcview.setVisibility(View.VISIBLE);
//                    hidden_layout.setVisibility(View.GONE);
//                    mEffectView.setVisibility(View.VISIBLE);
//                    mInitialized = false;
//                    mEffectView.invalidate();
//                }
//            }
//        });
//
//        // findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
//        //findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.myimage3).setOnTouchListener(new ExampleActivity.MyTouchListener());
//        findViewById(R.id.myimage4).setOnTouchListener(new ExampleActivity.MyTouchListener());
//
//        total_price = (TextView) findViewById(R.id.total_price);
//
//        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
//        sensorMgr.registerListener(this,
//                SensorManager.SENSOR_ACCELEROMETER,
//                SensorManager.SENSOR_DELAY_GAME);
//
//
//        myimage1 = (ImageView) findViewById(R.id.myimage1);
//        myimage1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pick_image();
//
//            }
//        });
//
//        myimage2 = (ImageView) findViewById(R.id.myimage2);
//        myimage3 = (ImageView) findViewById(R.id.myimage3);
//        myimage4 = (ImageView) findViewById(R.id.myimage4);
//        myimage2.setOnTouchListener(new ExampleActivity.MyTouchListener());
//        myimage3.setOnTouchListener(new ExampleActivity.MyTouchListener());
//        myimage4.setOnTouchListener(new ExampleActivity.MyTouchListener());
//
//        //myimage2.setVisibility(View.GONE);
//        if (myimage2.isShown()) {
//            myimage2.setVisibility(View.VISIBLE);
//            myimage3.setVisibility(View.VISIBLE);
//            myimage4.setVisibility(View.VISIBLE);
//        } else {
//            myimage2.setVisibility(View.GONE);
//            myimage3.setVisibility(View.GONE);
//            myimage4.setVisibility(View.GONE);
//        }
//
//
//        //findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
//        //findViewById(R.id.topright).setOnDragListener(new MyDragListener());
//        bottom_left = (LinearLayout) findViewById(R.id.bottomleft);
//        bottom_left.setOnDragListener(new ExampleActivity.MyDragListener());
//
//        drop_receiver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CropImage.activity(selected_image_uri)
//                        .start(ExampleActivity.this);
//            }
//        });
//        //findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());
//
//        findViewById(R.id.holder_one).setOnDragListener(new ExampleActivity.MyDragListener());
//        findViewById(R.id.holder_two).setOnDragListener(new ExampleActivity.MyDragListener());
//        findViewById(R.id.holder_three).setOnDragListener(new ExampleActivity.MyDragListener());
//        findViewById(R.id.holder_four).setOnDragListener(new ExampleActivity.MyDragListener());
//        findViewById(R.id.drop_receiver).setOnDragListener(new ExampleActivity.MyDragListener());
//
//
//        RelativeLayout deleteRelativeLayout = (RelativeLayout) findViewById(R.id.delete_rl);
//        parentImageRelativeLayout = (RelativeLayout) findViewById(R.id.parent);
//
//
//        photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(ExampleActivity.this)
//                .parentView(parentImageRelativeLayout) // add parent image view
//                .childView(photoEditImageView) // add the desired image view
//                .deleteView(deleteRelativeLayout)// add the deleted view that will appear during the movement of the views
//                .textView(addtext_btn)
//                //.brushDrawingView(brushDrawingView) // add the brush drawing view that is responsible for drawing on the image view
//                .buildPhotoEditorSDK(); // build photo editor sdk
//        //photoEditorSDK.setOnPhotoEditorSDKListener((OnPhotoEditorSDKListener) this);
//
//        colorPickerColors = new ArrayList<>();
//        colorPickerColors.add(getResources().getColor(R.color.black));
//        colorPickerColors.add(getResources().getColor(R.color.blue_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.brown_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.green_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.orange_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.red_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.red_orange_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.sky_blue_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.violet_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.white));
//        colorPickerColors.add(getResources().getColor(R.color.yellow_color_picker));
//        colorPickerColors.add(getResources().getColor(R.color.yellow_green_color_picker));
//
//        addtext_btn.setEnabled(false);
//        effect_btn.setEnabled(false);
//
//        line4.setVisibility(View.GONE);
//        discount.setVisibility(View.GONE);
//        line5.setVisibility(View.GONE);
//        after_discount.setVisibility(View.GONE);
//
//
//        check_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                check_coupon();
//            }
//        });
//
//
//        addtext_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addtext_btn.setBackgroundColor(Color.parseColor("#E7C943"));
//                addtext_btn.setTextColor(Color.parseColor("#ffffff"));
//                camera_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//                effect_btn.setBackgroundColor(Color.parseColor("#ffffff"));
//                openAddTextPopupWindow("", -1);
//            }
//        });
//
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onFabClick();
//            }
//        });
//
//
//        fab.setOnTouchListener(new View.OnTouchListener() {
//            float startX;
//            float startRawX;
//            float distanceX;
//
//            float startY;
//            float startRawY;
//            float distanceY;
//
//            int lastAction;
//
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getActionMasked()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = view.getX() - motionEvent.getRawX();
//                        startRawX = motionEvent.getRawX();
//                        startY = view.getY() - motionEvent.getRawY();
//                        startRawY = motionEvent.getRawY();
//                        lastAction = MotionEvent.ACTION_DOWN;
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        view.setX(motionEvent.getRawX() + startX);
//                        view.setY(motionEvent.getRawY() + startY);
//                        lastAction = MotionEvent.ACTION_DOWN;
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        distanceX = motionEvent.getRawX() - startRawX;
//                        view.setX(motionEvent.getRawX() + startX);
//                        view.setY(motionEvent.getRawY() + startY);
//
//                        if (Math.abs(distanceX) < 10) {
//                            viewMenu();
//                        }
//                        break;
//                    case MotionEvent.ACTION_BUTTON_PRESS:
//                        break;
//                    default:
//                        return false;
//                }
//                return true;
//            }
//        });
//
//
//        recyclerView = (RecyclerView) findViewById(R.id.basket_list);
//        cartPageAdapter = new CartAdapter(ExampleActivity.this, productsfrom_api, this);
//        recyclerView.setAdapter(cartPageAdapter);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        get_products();
//
//    }
//
//
//    void onFabClick() {
//        //sheetLayout.expandFab();
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void viewMenu() {
//
//        if (!isOpen) {
//
//            int x = layoutContent.getRight();
//            int y = layoutContent.getBottom();
//
//            int startRadius = 0;
//            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());
//
//            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.headercolor, null)));
//            fab.setImageResource(R.drawable.close_icon);
//
//            Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
//
//            layoutButtons.setVisibility(View.VISIBLE);
//            anim.start();
//
//            isOpen = true;
//
//        } else {
//
//            int x = layoutButtons.getRight();
//            int y = layoutButtons.getBottom();
//
//            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
//            int endRadius = 0;
//
//            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.headercolor, null)));
//            fab.setImageResource(R.drawable.add_cart_btn);
//
//            Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
//            anim.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animator) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animator) {
//                    layoutButtons.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animator) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animator) {
//
//                }
//            });
//            anim.start();
//
//            isOpen = false;
//        }
//    }
//
//
//    public void onFabAnimationEnd() {
//        Intent intent = new Intent(this, ProductDetail.class);
//        startActivityForResult(intent, REQUEST_CODE);
//    }
//
//
//    private void openAddTextPopupWindow(String text, int colorCode) {
//        colorCodeTextView = colorCode;
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View addTextPopupWindowRootView = inflater.inflate(R.layout.add_text_popup_window, null);
//        addTextEditText = (EditText) addTextPopupWindowRootView.findViewById(R.id.add_text_edit_text);
//        TextView addTextDoneTextView = (TextView) addTextPopupWindowRootView.findViewById(R.id.add_text_done_tv);
//        RecyclerView addTextColorPickerRecyclerView = (RecyclerView) addTextPopupWindowRootView.findViewById(R.id.add_text_color_picker_recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(ExampleActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        addTextColorPickerRecyclerView.setLayoutManager(layoutManager);
//        addTextColorPickerRecyclerView.setHasFixedSize(true);
//        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(ExampleActivity.this, colorPickerColors);
//        colorPickerAdapter.setOnColorPickerClickListener(new ColorPickerAdapter.OnColorPickerClickListener() {
//            @Override
//            public void onColorPickerClickListener(int colorCode) {
//                addTextEditText.setTextColor(colorCode);
//                colorCodeTextView = colorCode;
//            }
//        });
//        addTextColorPickerRecyclerView.setAdapter(colorPickerAdapter);
//        if (stringIsNotEmpty(text)) {
//            addTextEditText.setText(text);
//            addTextEditText.setTextColor(colorCode == -1 ? getResources().getColor(R.color.white) : colorCode);
//        }
//        final PopupWindow pop = new PopupWindow(ExampleActivity.this);
//        pop.setContentView(addTextPopupWindowRootView);
//        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        pop.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
//        pop.setFocusable(true);
//        pop.setBackgroundDrawable(null);
//        pop.showAtLocation(addTextPopupWindowRootView, Gravity.CENTER, 0, 0);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//        addTextDoneTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addText(addTextEditText.getText().toString(), colorCodeTextView);
//                Log.e("text", addTextEditText.getText().toString());
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                pop.dismiss();
//                addtext_btn.setEnabled(false);
//            }
//        });
//
//
//    }
//
//
//    private boolean stringIsNotEmpty(String string) {
//        if (string != null && !string.equals("null")) {
//            if (!string.trim().equals("")) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void addText(String text, int colorCodeTextView) {
//        photoEditorSDK.addText(text, colorCodeTextView);
//    }
//
//
//    public void pick_image() {
//        final String[] cases = {"Camera", "Gallery", "Choose from Library"};
//        final PanterDialog panterDialog = new PanterDialog(ExampleActivity.this);
//        panterDialog.setHeaderBackground(R.color.white);
//        panterDialog.setTitle("Select Image");
//        panterDialog.setDialogType(DialogType.SINGLECHOICE);
//        panterDialog.setPositive("OK");
//        panterDialog.setNegative("Cancel", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                panterDialog.dismiss();
//            }
//        });
//        panterDialog.items(cases, new OnSingleCallbackConfirmListener() {
//            @Override
//            public void onSingleCallbackConfirmed(PanterDialog dialog, int pos, String text) {
//                if (cases[pos].equals("Camera")) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, REQUEST_CAMERA);
//                } else if (cases[pos].equals("Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto, SELECT_FILE);
//                } else if (cases[pos].equals("Choose from Library")) {
//                    Intent intent = new Intent(ExampleActivity.this, GalleryActivity.class);
//                    startActivityForResult(intent, 2);
//                }
//            }
//        });
//        panterDialog.isCancelable(false);
//        panterDialog.show();
//    }
//
//
//
//    String selected_image_path = "";
//    Uri selected_image_uri;
//
//    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQUEST_CAMERA && imageReturnedIntent != null) {
//                Bundle bundle = imageReturnedIntent.getExtras();
//                final Bitmap bmp = (Bitmap) bundle.get("data");
//                myimage2.setImageBitmap(bmp);
//                myimage2.setVisibility(View.VISIBLE);
//                myimage3.setImageBitmap(bmp);
//                myimage3.setVisibility(View.VISIBLE);
//                myimage4.setImageBitmap(bmp);
//                myimage4.setVisibility(View.VISIBLE);
//                Uri selectedImage = getImageUri(ExampleActivity.this, bmp);
//                selected_image_uri = selectedImage;
//                selected_image_path = getRealPathFromURI(selectedImage);
//                addtext_btn.setEnabled(true);
//                effect_btn.setEnabled(true);
//                Toast.makeText(ExampleActivity.this, "Here " + getRealPathFromURI(selectedImage), Toast.LENGTH_LONG).show();
//            } else if (requestCode == SELECT_FILE) {
//                Uri selectedImage = imageReturnedIntent.getData();
//                myimage2.setImageURI(selectedImage);
//                myimage2.setVisibility(View.VISIBLE);
//
//                myimage3.setImageURI(selectedImage);
//                myimage3.setVisibility(View.VISIBLE);
//                myimage4.setImageURI(selectedImage);
//                myimage4.setVisibility(View.VISIBLE);
//                File new_file = new File(selectedImage.getPath());
//                selected_image_uri = selectedImage;
//                selected_image_path = getRealPathFromURI(selectedImage);
//                addtext_btn.setEnabled(true);
//                effect_btn.setEnabled(true);
//                Log.e("selected_image_path", selected_image_path);
//            } else if (requestCode == 2) {
//
//                if (resultCode == Activity.RESULT_OK) {
//
//                    // do something with the result
//                    if (imageReturnedIntent != null && imageReturnedIntent.hasExtra("image")) {
//                        String image = imageReturnedIntent.getExtras().getString("image");
//                        try {
//                            URL url = new URL(image);
//                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                            myimage2.setImageBitmap(bmp);
//                            myimage2.setVisibility(View.VISIBLE);
//                            myimage3.setImageBitmap(bmp);
//                            myimage3.setVisibility(View.VISIBLE);
//                            myimage4.setImageBitmap(bmp);
//                            myimage4.setVisibility(View.VISIBLE);
//                            Uri selectedImage = getImageUri(ExampleActivity.this, bmp);
//                            selected_image_uri = selectedImage;
//                            selected_image_path = getRealPathFromURI(selectedImage);
//                            addtext_btn.setEnabled(true);
//                            effect_btn.setEnabled(true);
//                            Log.e("cate", bmp.toString());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//
//                } else if (resultCode == Activity.RESULT_CANCELED) {
//                    // some stuff that will happen if there's no result
//                }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                    CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
//                    if (resultCode == Activity.RESULT_OK) {
//                        Uri resultUri = result.getUri();
//                        mEffectView.setVisibility(View.GONE);
//                        myimage2.setImageURI(resultUri);
//                        myimage2.setVisibility(View.VISIBLE);
//                        myimage3.setImageURI(resultUri);
//                        myimage3.setVisibility(View.VISIBLE);
//                        myimage4.setImageURI(resultUri);
//                        myimage4.setVisibility(View.VISIBLE);
//                        addtext_btn.setEnabled(true);
//                        effect_btn.setEnabled(true);
//                        camera_btn.callOnClick();
//                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                        Exception error = result.getError();
//                    }
//                }
//            }
//        }
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//
//    private String getRealPathFromURI(Uri contentURI) {
//        String result;
//        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) {
//            result = contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(idx);
//            cursor.close();
//        }
//        return result;
//    }
//
//
//    public static Bitmap getBitmapFromView(View view) {
//        //Define a bitmap with the same size as the view
//        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//
//        //Bind a canvas to it
//        Canvas canvas = new Canvas(returnedBitmap);
//        //Get the view's background
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null)
//            //has background drawable, then draw it on the canvas
//            bgDrawable.draw(canvas);
//        else
//            //does not have background drawable, then draw white background on the canvas
//            canvas.drawColor(Color.WHITE);
//        // draw the view on the canvas
//        view.draw(canvas);
//        //return the bitmap
//
//
//        return returnedBitmap;
//    }
//
//
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
//                    if (Session.get_shake_status(ExampleActivity.this)) {
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
//
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
//                myimage2.setImageBitmap(myBitmap);
//            }
//            top.setVisibility(View.GONE);
//        }
//    }
//
//
//    class MyDragListener implements View.OnDragListener {
//        Drawable enterShape = getResources().getDrawable(
//                R.drawable.shape_droptarget);
//        Drawable normalShape = getResources().getDrawable(R.drawable.border);
//
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            int action = event.getAction();
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    myimage2.setVisibility(View.VISIBLE);
//                    myimage3.setVisibility(View.VISIBLE);
//                    // do nothing
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
//                    myimage2.setVisibility(View.VISIBLE);
//                    // v.setBackgroundDrawable(enterShape);
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
//                    myimage2.setVisibility(View.VISIBLE);
//                    // v.setBackgroundDrawable(normalShape);
//                    break;
//                case DragEvent.ACTION_DROP:
//                    // Dropped, reassign View to ViewGroup
//                    View view = (View) event.getLocalState();
//                    ViewGroup owner = (ViewGroup) view.getParent();
//                    owner.removeView(view);
//                    LinearLayout container = (LinearLayout) v;
//                    bottom_left.addView(view);
//                    view.setVisibility(View.VISIBLE);
//                    myimage2.setVisibility(View.VISIBLE);
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
//                    myimage2.setVisibility(View.VISIBLE);
//                    //v.setBackgroundDrawable(normalShape);
//                default:
//                    break;
//            }
//            return true;
//        }
//    }
//
//    private final class MyTouchListener implements View.OnTouchListener {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ClipData data = ClipData.newPlainText("", "");
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
//                        view);
//                view.startDrag(data, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
//                Log.e("drag", "ontouc");
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//
//    private void loadTextures() {
//        // Generate textures
//        GLES20.glGenTextures(2, mTextures, 0);
//
//        // Load input bitmap
//        Bitmap bitmap = getBitmapFromView(myimage2);
//        mImageWidth = bitmap.getWidth();
//        mImageHeight = bitmap.getHeight();
//        mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);
//
//
//        // Upload to texture
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
//        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
//
//        // Set texture parameters
//        GLToolbox.initTexParams();
//        bitmap.recycle();
//    }
//
//
//    private void initEffect() {
//        EffectFactory effectFactory = mEffectContext.getFactory();
//        if (mEffect != null) {
//            mEffect.release();
//        }
//        /**
//         * Initialize the correct effect based on the selected menu/action item
//         */
//        switch (mCurrentEffect) {
//
//            case 0:
//                break;
//
//            case 1:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_AUTOFIX);
//                mEffect.setParameter("scale", 0.5f);
//                break;
//
//            case 2:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BLACKWHITE);
//                mEffect.setParameter("black", .1f);
//                mEffect.setParameter("white", .7f);
//                break;
//
//            case 3:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BRIGHTNESS);
//                mEffect.setParameter("brightness", 2.0f);
//                break;
//
//            case 4:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CONTRAST);
//                mEffect.setParameter("contrast", 1.4f);
//                break;
//
//            case 5:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CROSSPROCESS);
//                break;
//
//            case 6:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
//                break;
//
//            case 7:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DUOTONE);
//                mEffect.setParameter("first_color", Color.YELLOW);
//                mEffect.setParameter("second_color", Color.DKGRAY);
//                break;
//
//            case 8:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FILLLIGHT);
//                mEffect.setParameter("strength", .8f);
//                break;
//
//            case 9:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FISHEYE);
//                mEffect.setParameter("scale", .5f);
//                break;
//
//            case 10:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
//                mEffect.setParameter("vertical", true);
//                break;
//
//            case 11:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
//                mEffect.setParameter("horizontal", true);
//                break;
//
//            case 12:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAIN);
//                mEffect.setParameter("strength", 1.0f);
//                break;
//
//            case 13:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
//                break;
//
//            case 14:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_LOMOISH);
//                break;
//
//            case 15:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_NEGATIVE);
//                break;
//
//            case 16:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_POSTERIZE);
//                break;
//
//            case 17:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_ROTATE);
//                mEffect.setParameter("angle", 180);
//                break;
//
//            case 18:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SATURATE);
//                mEffect.setParameter("scale", .5f);
//                break;
//
//            case 19:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SEPIA);
//                break;
//
//            case 20:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_SHARPEN);
//                break;
//
//            case 21:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_TEMPERATURE);
//                mEffect.setParameter("scale", .9f);
//                break;
//
//            case 22:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_TINT);
//                mEffect.setParameter("tint", Color.MAGENTA);
//                break;
//
//            case 23:
//                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_VIGNETTE);
//                mEffect.setParameter("scale", .5f);
//                break;
//
//            default:
//                break;
//
//        }
//    }
//
//    private void applyEffect() {
//        mEffect.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1]);
//
//    }
//
//
//    private void renderResult() {
//        if (mCurrentEffect != 0) {
//            // if no effect is chosen, just render the original bitmap
//            mTexRenderer.renderTexture(mTextures[1]);
//        } else {
//            saveFrame = true;
//            // render the result of applyEffect()
//            mTexRenderer.renderTexture(mTextures[0]);
//        }
//    }
//
//
//    @Override
//    public void onDrawFrame(GL10 gl) {
//
//        if (!mInitialized) {
//            // Only need to do this once
//            mEffectContext = EffectContext.createWithCurrentGlContext();
//            mTexRenderer.init();
//            loadTextures();
//            mInitialized = true;
//
//        }
//        if (mCurrentEffect != 0) {
//            // if an effect is chosen initialize it and apply it to the texture
//            initEffect();
//            applyEffect();
//        }
//
//
//
//        renderResult();
//
//
//
//    }
//
//    private Bitmap createBitmapFromGLSurface(GL10 gl)
//            throws OutOfMemoryError {
//
//        int bitmapBuffer[] = new int[w * h];
//        int bitmapSource[] = new int[w * h];
//        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
//        intBuffer.position(0);
//
//        try {
//            gl.glReadPixels(x1, y1, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
//            int offset1, offset2;
//            for (int i = 0; i < h; i++) {
//                offset1 = i * w;
//                offset2 = (h - i - 1) * w;
//                for (int j = 0; j < w; j++) {
//                    int texturePixel = bitmapBuffer[offset1 + j];
//                    int blue = (texturePixel >> 16) & 0xff;
//                    int red = (texturePixel << 16) & 0x00ff0000;
//                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
//                    bitmapSource[offset2 + j] = pixel;
//                }
//            }
//        } catch (GLException e) {
//            return null;
//        }
//
//        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
//    }
//
//
//
//
//
//    @Override
//    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        if (mTexRenderer != null) {
//            mTexRenderer.updateViewSize(width, height);
//        }
//    }
//
//
//    @Override
//    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//    }
//
//
//    public void get_products() {
//        Log.e("cart_list", String.valueOf(Session.GetCartProducts(ExampleActivity.this).toString()));
//
//
//        JsonArray temp;
//        temp = Session.GetCartProducts(ExampleActivity.this);
//        for (int i=0;i<temp.size();i++){
//            Log.e("result",temp.toString());
//            Products products = new Products(temp.get(i).getAsJsonObject(),ExampleActivity.this);
//            productsfrom_api.add(products);
//            Log.e("_price",products.price);
//            total_cart_price = total_cart_price + (products.cart_quantity * Float.parseFloat(products.price));
//        }
//        cartPageAdapter.notifyDataSetChanged();
//
//        Float grandtotal = Float.parseFloat(shipping_price.getText().toString());
//        Log.e("price",String.valueOf(total_cart_price + 50 +"KD"));
//        total_price.setText(String.valueOf(total_cart_price + 50 + "KD"));
//
//    }
//
//
//    public void calculate_total_price(){
//
//        total_cart_price = 0.0f;
//        for (int i=0;i<productsfrom_api.size();i++){
//            total_cart_price = total_cart_price + (productsfrom_api.get(i).cart_quantity* Float.parseFloat(productsfrom_api.get(i).price));
//        }
//        grandtotal = Float.parseFloat(shipping_price.getText().toString());
//        Log.e("price",String.valueOf(total_cart_price + 50 +"KD"));
//        total_price.setText(String.valueOf(total_cart_price + 50 + "KD"));
//    }
//
//
//    public void check_coupon(){
//        String coupon_string = coupon_text.getText().toString();
//        if (coupon_string.equals("")){
//            Toast.makeText(ExampleActivity.this,"Please Enter Coupon Code",Toast.LENGTH_SHORT).show();
//            coupon_text.requestFocus();
//        }else {
//            final KProgressHUD hud = KProgressHUD.create(ExampleActivity.this)
//                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                    .setLabel("Please wait")
//                    .setCancellable(true)
//                    .setMaxProgress(100)
//                    .show();
//            Ion.with(this)
//                    .load(Session.SERVER_URL + "coupon-check.php")
//                    .setBodyParameter("coupon", coupon_string)
//                    .setBodyParameter("cart_total", String.valueOf(total_cart_price))
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            try {
//                                hud.dismiss();
//                                Log.e("coupon", result.toString());
//                                if (result.get("status").getAsString().equals("Success")) {
//                                    Toast.makeText(ExampleActivity.this, result.get("discount_value").getAsString(), Toast.LENGTH_SHORT).show();
//                                    line4.setVisibility(View.VISIBLE);
//                                    discount.setVisibility(View.VISIBLE);
//                                    line5.setVisibility(View.VISIBLE);
//                                    after_discount.setVisibility(View.VISIBLE);
//                                    discount_value.setText(result.get("discount_value").getAsString());
//                                    //  Float totalprice = Float.parseFloat(total_price.getText().toString());
//                                    Float discountprice = Float.parseFloat(discount_value.getText().toString());
//
//                                    total_discount_price.setText(String.valueOf(total_cart_price + 50 - discountprice) +""+"KD");
//                                } else {
//                                    Toast.makeText(ExampleActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (Exception e1) {
//                                e1.printStackTrace();
//                            }
//
//                        }
//                    });
//        }
//    }
//
////    950292
//
//
//
//}


//JSONObject address_object = new JSONObject();
//        JSONArray products_array = new JSONArray();
//        try {
//        if (Settings.getUserid(getActivity()).equals("-1")) {
//        address_object.put("first_name", et_fname.getText().toString());
//        address_object.put("last_name", et_lname.getText().toString());
//        Log.e("email",et_email.getText().toString());
//        address_object.put("email", et_email.getText().toString());
//        address_object.put("home_phone", et_work_ph.getText().toString());
//        address_object.put("mobile", et_home_ph.getText().toString());
//        }
//        address_object.put("area", selected_area_id);
//        address_object.put("block", e_block.getText().toString());
//        address_object.put("street", e_street.getText().toString());
//        address_object.put("building", e_building.getText().toString());
//        address_object.put("floor", e_floor.getText().toString());
//        address_object.put("flat", e_aprtment.getText().toString());
//        if (!Settings.getUserid(getActivity()).equals("-1")) {
//        address_object.put("mobile", e_mobile.getText().toString());
//        }
//        place_order_object.put("address", address_object);
//
//        place_order_object.put("comments", spl_comment.getText().toString());
//        place_order_object.put("coupon_code", coupon_code.getText().toString());
//        place_order_object.put("discount_amount", dis_amount);
//        place_order_object.put("price", String.valueOf(total));
//        place_order_object.put("delivery_charges", Settings.getDelivery_charges(getActivity()));
//        place_order_object.put("total_price", String.valueOf(total+Float.parseFloat(Settings.getDelivery_charges(getActivity()))));
//        place_order_object.put("payment_method", pay_met);
//        place_order_object.put("delivery_date", date1);
//        place_order_object.put("delivery_time", time);
//        place_order_object.put("member_id", Settings.getUserid(getActivity()));
//        place_order_object.put("restaurant_id", rest_id);
//        Log.e("cart", String.valueOf(cart_items.size()));
//        for (int i = 0; i < cart_items.size(); i++) {
//        JSONObject product = new JSONObject();
//        product.put("product_id", cart_items.get(i).products.res_id);
//        product.put("quantity", cart_items.get(i).quantity);
//        product.put("price", Float.parseFloat(cart_items.get(i).quantity)*Float.parseFloat(cart_items.get(i).products.cart_price));
//        product.put("special_request", cart_items.get(i).spl_request);
//        JSONArray addon_array = new JSONArray();
//        for (int j = 0; j < cart_items.get(i).products.groups.size(); j++) {
//        for (int k = 0; k < cart_items.get(i).products.groups.get(j).addons.size(); k++) {
//        JSONObject addon = new JSONObject();
//        if (cart_items.get(i).products.groups.get(j).addons.get(k).isselected){
//        addon.put("addon_id", cart_items.get(i).products.groups.get(j).addons.get(k).addon_id);
//        Log.e("addonId", cart_items.get(i).products.groups.get(j).addons.get(k).addon_id);
//        addon.put("price", cart_items.get(i).products.groups.get(j).addons.get(k).price);
//        Log.e("addonPrice", cart_items.get(i).products.groups.get(j).addons.get(k).price);
//        addon_array.put(addon);
//        }}
//        }
//        product.put("addons", addon_array);
//        JSONArray options_array=new JSONArray();
//        for (int l = 0; l < cart_items.get(i).products.options.size(); l++) {
//        JSONObject options = new JSONObject();
//        if (cart_items.get(i).products.options.get(l).isselected){
//        options.put("option_id", cart_items.get(i).products.options.get(l).option_id);
//        Log.e("optionId", cart_items.get(i).products.options.get(l).option_id);
//        options.put("price", cart_items.get(i).products.options.get(l).price);
//        Log.e("optionPrice", cart_items.get(i).products.options.get(l).price);
//        options_array.put(options);
//        }
//        product.put("options", options_array);
//        }
//        products_array.put(product);
//        }
//        place_order_object.put("products", products_array);



//
