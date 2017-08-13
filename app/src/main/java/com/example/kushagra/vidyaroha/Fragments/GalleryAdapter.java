package com.example.kushagra.vidyaroha.Fragments;

/**
 * Created by Kushagra Saxena on 13/08/2017.
 */


        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.DialogInterface.OnClickListener;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AlertDialog.Builder;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.RecyclerView.Adapter;
        import android.support.v7.widget.RecyclerView.ViewHolder;
        import android.text.Html;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnLongClickListener;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;
        import com.bumptech.glide.request.animation.GlideAnimation;
        import com.bumptech.glide.request.target.SimpleTarget;
        import com.example.kushagra.vidyaroha.R;

        import java.io.File;
        import java.util.ArrayList;

public class GalleryAdapter extends Adapter<GalleryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<File> thumburl;

    class MyViewHolder extends ViewHolder {
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public GalleryAdapter(ArrayList<File> thumburl, Context context) {
        this.thumburl = thumburl;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardgalleryview, parent, false));
    }

    public void onBindViewHolder(final MyViewHolder holder, int listPosition) {
        final ImageView image = holder.image;
        Glide.with(this.context).load((File) this.thumburl.get(listPosition)).asBitmap().crossFade().diskCacheStrategy(DiskCacheStrategy.RESULT).into(new SimpleTarget<Bitmap>(300, 300) {
            public void onResourceReady(final Bitmap resource, GlideAnimation glideAnimation) {
                image.post(new Runnable() {
                    public void run() {
                        image.setImageBitmap(resource);
                    }
                });
            }
        });


    }

    public int getItemCount() {
        return this.thumburl.size();
    }
}
