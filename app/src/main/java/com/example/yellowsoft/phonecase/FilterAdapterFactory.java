package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.IntBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yellowsoft on 24/10/17.
 */

public class FilterAdapterFactory  extends RecyclerView.Adapter<FilterAdapterFactory.FilterHolder>{

    private String itemData[] = {
            "No Effect",
            "Autofix",
            "BlackAndWhite",
            "Brightness",
            "Contrast",
            "CrossProcess",
            "Documentary",
            "Duotone",
            "Fillight",
            "FishEye",
            "Flipert",
            "Fliphor",
            "Grain",
            "Grayscale",
            "Lomoish",
            "Negative",
            "Posterize",
            "Rotate",
            "Saturate",
            "Sepia",
            "Sharpen",
            "Temperature",
            "TintEffect",
            "Vignette"};
    private Context mContext;
    CaseLayoutEffectActivity activity;



    public FilterAdapterFactory(Context mContext, CaseLayoutEffectActivity activity) {
        super();
        this.mContext = mContext;
        this.activity= activity;
    }

    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.image_effect_item, parent, false);
        FilterHolder viewHolder = new FilterHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilterHolder holder, int position) {
        String val = itemData[position];

        ColorGenerator generator = ColorGenerator.MATERIAL;
        // generate random color
        int color1 = generator.getRandomColor();


        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(2)
                .fontSize(23)
                .endConfig()
                .rect();

        TextDrawable drawable = builder.build(val.substring(0,5), color1);
//        holder.imFilter.setBackground(drawable);
        holder.imFilter.setImageBitmap(activity.mBitmap);
        holder.setCurrentEffect(position);
        holder.mEffectView.requestRender();
        holder.mInitialized = false;
        holder.mEffectView.invalidate();

       // activity.mBitmap.
        holder.name.setText(val.substring(0,5));
        Log.e("bitmap", String.valueOf(activity.mBitmap));
        Log.e("name",val.substring(0,5));
        Log.e("color", String.valueOf(generator.getRandomColor()));

    }

    @Override
    public int getItemCount() {
        return itemData.length;
    }



    public class FilterHolder extends RecyclerView.ViewHolder implements GLSurfaceView.Renderer {
        public ImageView imFilter;
        public ImageView image;
        public TextView name;
        private TextureRenderer mTexRenderer = new TextureRenderer();
        private boolean mInitialized = false;
        private EffectContext mEffectContext;
        private int mCurrentEffect;
        private int[] mTextures = new int[2];
        private Effect mEffect;
        private int mImageWidth;
        private int mImageHeight;
        volatile boolean saveFrame;
        private GLSurfaceView mEffectView;
        private  Bitmap mBitmap;
        GL10 gl;


        public void setCurrentEffect(int effect) {
            mCurrentEffect = effect;
        }

        public FilterHolder(View itemView) {
            super(itemView);
            imFilter = (ImageView) itemView.findViewById(R.id.effectsviewimage_item);
            name = (TextView) itemView.findViewById(R.id.name);
            mEffectView = (GLSurfaceView) itemView.findViewById(R.id.effectsview);
            mEffectView.setEGLContextClientVersion(2);
            mEffectView.setRenderer(this);
            mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            mEffectView.setZOrderOnTop(false);
            mCurrentEffect = 0;

        }


        public void loadTextures() {
            // Generate textures
            GLES20.glGenTextures(2, mTextures, 0);

            // Load input bitmap
            Bitmap bitmap = getBitmapFromView(activity.bottom_left);
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
            try {
                mEffect.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void renderResult() {
            try {

                if (mCurrentEffect != 0) {
                    // if no effect is chosen, just render the original bitmap
                    mTexRenderer.renderTexture(mTextures[1]);
                } else {
                    saveFrame = true;
                    // render the result of applyEffect()
                    mTexRenderer.renderTexture(mTextures[0]);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int width, int height) {
            if (mTexRenderer != null) {
                mTexRenderer.updateViewSize(width, height);
            }
        }



        @Override
        public void onDrawFrame(GL10 gl10) {
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

               // saveBitmap(takeScreenshot(gl));

            }
        }


        public boolean onOptionsItemSelected(MenuItem item) {
            setCurrentEffect(item.getItemId());
            mEffectView.requestRender();
            return true;
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
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {

//stuff that updates ui
                    imFilter.setBackground(new BitmapDrawable(mBitmap));


                }
            });


            return mBitmap;
        }






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










}

