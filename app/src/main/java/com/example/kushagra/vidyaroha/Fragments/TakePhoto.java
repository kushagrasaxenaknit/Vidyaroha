package com.example.kushagra.vidyaroha.Fragments;

/**
 * Created by Kushagra Saxena on 12/08/2017.
 */


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PointerIconCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kushagra.vidyaroha.R;
import com.example.kushagra.vidyaroha.SelectImageActivity;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.PermissionCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class TakePhoto extends Fragment  implements PermissionCallback {

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;
    // The URI of the image selected to detect.
    private Uri mImageUri;

    // The image selected to detect.
    private Bitmap mBitmap;
    private Button button_take_a_photo1,button_take_a_photo2;
    boolean imageFromOne=false;
    View rootView;

    Adapter galleryadapter1,galleryadapter2;

    RecyclerView recyclerView1,recyclerView2;
    ArrayList<File> urls1,urls2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         rootView = inflater.inflate(R.layout.picture, container, false);
        this.recyclerView1 = (RecyclerView) rootView.findViewById(R.id.recyclerView1);
        this.recyclerView2 = (RecyclerView) rootView.findViewById(R.id.recyclerView2);

        this.urls1 = new ArrayList();
        this.urls2 = new ArrayList();
        listAllFiles(Environment.getExternalStorageDirectory().toString() + "/one");
        setAdapterDude();
        listAllFiles2(Environment.getExternalStorageDirectory().toString() + "/two");
        setAdapterDude2();
        button_take_a_photo1 = (Button)rootView.findViewById(R.id.button_take_a_photo1);
        button_take_a_photo2 = (Button)rootView.findViewById(R.id.button_take_a_photo2);
        button_take_a_photo1.setEnabled(false);
        button_take_a_photo2.setEnabled(false);
        button_take_a_photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getContext(), SelectImageActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_IMAGE);
                imageFromOne=true;
            }
        });
        button_take_a_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageFromOne=false;
                Intent intent;
                intent = new Intent(getContext(), SelectImageActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_IMAGE);
            }
        });
        return rootView;
    }



    // Called when image selection is done.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    // If image is selected successfully, set the image URI and bitmap.
                    mImageUri = data.getData();

                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                            mImageUri,getContext().getContentResolver());
                    if (mBitmap != null) {
                        // save the image
                        SaveImage(mBitmap,1);



                    }
                }
                break;
            default:
                break;
        }
    }

    private File SaveImage(Bitmap bm, int option) {
        String path="";
        if(imageFromOne)
        {
            path="/one";
        }
        else
        {
            path="/two";
        }
        File myDir = new File(Environment.getExternalStorageDirectory().toString() +path);
        myDir.mkdirs();
        File file = new File(myDir, "Image-" + System.currentTimeMillis() + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            if (option != 1) {
                return file;
            }

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onPermissionsGranted(int requestCode) {
        if (requestCode == PointerIconCompat.TYPE_COPY ) {
            button_take_a_photo1.setEnabled(true);
            button_take_a_photo2.setEnabled(true);
        }
    }

    public void onPermissionsDenied(int requestCode) {
        if (requestCode == PointerIconCompat.TYPE_COPY) {
            button_take_a_photo1.setEnabled(false);
            button_take_a_photo2.setEnabled(false);
        }
    }


    public void onResume() {
        super.onResume();
        listAllFiles(Environment.getExternalStorageDirectory().toString() + "/one");
        setAdapterDude();
        listAllFiles2(Environment.getExternalStorageDirectory().toString() + "/two");
        setAdapterDude2();
        new AskPermission.Builder((Fragment) this).setPermissions("android.permission.CAMERA",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE").setCallback(this).request(PointerIconCompat.TYPE_COPY);
    }
    private void listAllFiles(String path) {

        File[] files = new File(path).listFiles();
        if (files != null) {

            for (File f : files) {
                this.urls1.add(f);
            }
        }
    }

    public void setAdapterDude() {
        if (this.urls1.size() > 0) {


            this.recyclerView1.setHasFixedSize(true);
            this.recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
            this.recyclerView1.setItemAnimator(new DefaultItemAnimator());
            this.recyclerView1.setNestedScrollingEnabled(false);
            this.galleryadapter1 = new GalleryAdapter(this.urls1, getActivity());
            this.recyclerView1.setAdapter(this.galleryadapter1);
            return;
        }
    }
    private void listAllFiles2(String path) {

        File[] files = new File(path).listFiles();
        if (files != null) {

            for (File f : files) {
                this.urls2.add(f);
            }
        }
    }

    public void setAdapterDude2() {
        if (this.urls2.size() > 0) {


            this.recyclerView2.setHasFixedSize(true);
            this.recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
            this.recyclerView2.setItemAnimator(new DefaultItemAnimator());
            this.recyclerView2.setNestedScrollingEnabled(false);
            this.galleryadapter2 = new GalleryAdapter(this.urls2, getActivity());
            this.recyclerView2.setAdapter(this.galleryadapter2);
            return;
        }

    }
}