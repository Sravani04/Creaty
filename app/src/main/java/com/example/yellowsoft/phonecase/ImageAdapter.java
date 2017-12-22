package com.example.yellowsoft.phonecase;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.AbstractCollection;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 5/12/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SimpleViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<MyImage> images;
    CaseLayoutEffectActivity activity;
    final int THUMBSIZE = 400;
    int position;



    public  class SimpleViewHolder extends RecyclerView.ViewHolder  {
         ImageView imgIcon;
         TextView description;

        public SimpleViewHolder(View view) {
            super(view);
            imgIcon = (ImageView) view.findViewById(R.id.item_img_icon);



        }


    }


    public ImageAdapter(Context context, ArrayList<MyImage> images, CaseLayoutEffectActivity activity) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.images = images;
        this.activity = activity;


    }

    public MyImage getItem(int position) {
        return images != null ? images.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ImageAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageAdapter.SimpleViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ImageAdapter.SimpleViewHolder holder, final int position) {

//        holder.imgIcon.setImageBitmap(ThumbnailUtils
//                .extractThumbnail(BitmapFactory.decodeFile(String.valueOf(images.get(position).getPath())),
//                        THUMBSIZE, THUMBSIZE));


        final Uri targetUri = Uri.parse(images.get(position).getPath().toString());
        holder.imgIcon.setImageURI(targetUri);
        activity.image_position = position;
        holder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("coming","iscoming");
                try {
//                    Log.e("position", String.valueOf(getItemId(position)));
//                    Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(String.valueOf(images.get(position).getPath())), THUMBSIZE, THUMBSIZE);
//                    activity.bottom_left.setBackground(new BitmapDrawable(bitmap));
                    activity.image_position = position;
                    File f = new File(activity.getRealPathFromURI(targetUri));
                    Drawable d = Drawable.createFromPath(f.getPath());
                    activity.bottom_left.setBackground(d);

//                    notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
//                Toast.makeText(context, "pic" + getItem(position) + " selected",
//                        Toast.LENGTH_SHORT).show();
}
        });





//        holder.imgIcon.setOnDragListener(activity.new MyDragListener());
//        holder.imgIcon.setOnTouchListener(activity.new MyTouchListener());
//        holder.imgIcon.setVisibility(View.VISIBLE);

    }

    public void getImagePostion(int position){

    }







    @Override
    public int getItemCount() {
        return images.size();
    }
}

