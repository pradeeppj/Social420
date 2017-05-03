package com.social420.social420;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by pradeeppj on 21/04/17.
 */

public class filterfour extends Fragment {

    private View rootview;
    private Context context;
    private String imagepath;
    private Uri imageuri;
    private Bitmap original;
    private File imagefile;
    private ImageView filterimage;


    public filterfour() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = (View) inflater.inflate(R.layout.filterfour, container, false);

        filterimage = (ImageView)rootview.findViewById(R.id.filtered4);

        Typeface mytypeface2 = Typeface.createFromAsset(getContext().getAssets(), "averta.otf");
        TextView mytextview2 = (TextView) rootview.findViewById(R.id.high);
        mytextview2.setTypeface(mytypeface2);





        SharedPreferences sp3= getActivity().getSharedPreferences("imagesizes",0);

        String width1 =sp3.getString("width", null);
        String height1=sp3.getString("height",null);

        int width = Integer.valueOf(width1);
        int height = Integer.valueOf(height1);



        Rect rect = new Rect(0, 0, width, height);

        Bitmap image = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        int color = Color.argb(60, 168, 103, 171);
        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawRect(rect, paint);
        canvas.drawBitmap(image, 0, 0, null);
        filterimage.setImageDrawable(new BitmapDrawable(getResources(), image));

        return rootview;
    }

}
