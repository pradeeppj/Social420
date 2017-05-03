package com.social420.social420;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.R.attr.bitmap;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class filter extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private String uripath;
    private Uri imageuri;
    private Bitmap originalbitmap;
    private ImageView postnw;
    FirebaseDatabase database;
    private StorageReference mStorage;
    DatabaseReference myRef;
    private File imagefile;
    DatabaseReference myRef1;
    private ProgressDialog progress;
    private EditText description;
    private File compressedImage;
    private File compressedImage1;
    private File compressedImage2;
    private File compressedImage3;
    private File compressedImage4;
    private File compressedImage5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mStorage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Feed");
        description = (EditText)findViewById(R.id.description);

        uripath = getIntent().getStringExtra("uripath");
        imageuri = Uri.parse(uripath);


        //Toast.makeText(this, uripath, Toast.LENGTH_SHORT).show();



        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);





        ImageView original = (ImageView)findViewById(R.id.originalimage);
        original.setImageURI(imageuri);

        if (imageuri != null){

            try{
                InputStream image_stream = getApplicationContext().getContentResolver().openInputStream(imageuri);
                originalbitmap = BitmapFactory.decodeStream(image_stream );

                int w = originalbitmap.getWidth();
                int h = originalbitmap.getHeight();
                String width = String.valueOf(w);
                String height = String.valueOf(h);


                SharedPreferences sp=getSharedPreferences("imagesizes", 0);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("width", width);
                Ed.putString("height",height);
                Ed.commit();

            }
            catch (FileNotFoundException e){
                // do stuff here..
                return ;
            }

        }

        postnw = (ImageView)findViewById(R.id.post);
        postnw.setEnabled(true);


        postnw.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {


                postnw.setEnabled(false);


                progress = ProgressDialog.show(filter.this, "",
                        "Sharing on Social 420", true);


                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        // do the thing that takes a long time

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {

                                int i = mViewPager.getCurrentItem();

                                Toast.makeText(getApplicationContext(), String.valueOf(i),Toast.LENGTH_SHORT).show();



                                switch (i) {
                                    case 0:



                                        int w0 = originalbitmap.getWidth();
                                        int h0 = originalbitmap.getHeight();

                                        Rect rect0 = new Rect(0, 0, w0, h0);

                                        Bitmap image0 = Bitmap.createBitmap(rect0.width(), rect0.height(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas0 = new Canvas(image0);

                                        int color0 = Color.argb(0, 153, 86, 37);
                                        Paint paint0 = new Paint();
                                        paint0.setColor(color0);

                                        canvas0.drawRect(rect0, paint0);
                                        canvas0.drawBitmap(image0, 0, 0, null);

                                        Bitmap mergedImages0 = createSingleImageFromMultipleImages(originalbitmap, image0);



                                        String id0 = (myRef.push().getKey());

                                        File imageFile0 = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "fhgfhilter1.jpg");

                                        OutputStream os0;
                                        try {
                                            os0 = new FileOutputStream(imageFile0);

                                            mergedImages0.compress(Bitmap.CompressFormat.JPEG, 80, os0);
                                            os0.flush();
                                            os0.close();
                                        } catch (Exception e) {
                                            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                                        }

                                        //Uri tempuri = Uri.fromFile(imagefile);











                                        UploadTask uploadTask = mStorage.child(id0).child("photo").putFile(FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile0));
                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                Toast.makeText(getApplicationContext(), "Failed to upload image",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                String imagestring = downloadUrl.toString();
                                                database = FirebaseDatabase.getInstance();
                                                myRef = database.getReference("Feed");
                                                myRef1 = database.getReference("locations");

                                                SharedPreferences sp2= getSharedPreferences("location",0);
                                                String lat = sp2.getString("lat",null);
                                                String longi = sp2.getString("long",null);



                                                String id = (myRef.push().getKey()+currentDateFormat());


                                                myRef1.child(id).child("lati").setValue(Double.parseDouble(lat));
                                                myRef1.child(id).child("longi").setValue(Double.parseDouble(longi));





                                                SharedPreferences sp1= getSharedPreferences("Login",0);

                                                String unm=sp1.getString("Unm", null);
                                                String propic = sp1.getString("propic",null);



                                                //String username = unm;
                                                myRef.child(id).child("username").setValue(unm);
                                                myRef.child(id).child("description").setValue(description.getText().toString().trim());
                                                myRef.child(id).child("filtername").setValue("");


                                                myRef.child(id).child("image").setValue(imagestring);
                                                myRef.child(id).child("propic").setValue(propic);


                                                String ts = String.valueOf(System.currentTimeMillis());
                                                myRef.child(id).child("time").setValue(ts);

                                                String likes = "";
                                                myRef.child(id).child("likes").setValue(likes);

                                                String comments = "";
                                                myRef.child(id).child("comments").setValue(comments);





                                                //Toast.makeText(getContext(), "Image uploaded",Toast.LENGTH_SHORT).show();
                                                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clicksound);
                                                mp.start();


                                                Intent intent = new Intent(getApplicationContext(), social.class);
                                                startActivity(intent);


                                            }
                                        });


                                        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                System.out.println("Upload is " + progress + "% done");
                                                int currentprogress = (int) progress;


                                            }
                                        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                                                System.out.println("Upload is paused");
                                            }
                                        });

                                        break;


                                    case 1:

                                        int w = originalbitmap.getWidth();
                                        int h = originalbitmap.getHeight();

                                        Rect rect = new Rect(0, 0, w, h);

                                        Bitmap image = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas = new Canvas(image);

                                        int color = Color.argb(60, 111, 197, 162);
                                        Paint paint = new Paint();
                                        paint.setColor(color);

                                        canvas.drawRect(rect, paint);
                                        canvas.drawBitmap(image, 0, 0, null);

                                        Bitmap mergedImages = createSingleImageFromMultipleImages(originalbitmap, image);




                                        File imageFile1 = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "420moment.jpg");

                                        OutputStream os;
                                        try {
                                            os = new FileOutputStream(imageFile1);

                                            mergedImages.compress(Bitmap.CompressFormat.JPEG, 80, os);
                                            os.flush();
                                            os.close();
                                        } catch (Exception e) {
                                            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                                        }



                                        String id1 = (myRef.push().getKey());

                                        UploadTask uploadTask1 = mStorage.child(id1).child("photo").putFile(FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile1));
                                        uploadTask1.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                Toast.makeText(getApplicationContext(), "Failed to upload image",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                String imagestring = downloadUrl.toString();
                                                database = FirebaseDatabase.getInstance();
                                                myRef = database.getReference("Feed");
                                                myRef1 = database.getReference("locations");

                                                SharedPreferences sp2= getSharedPreferences("location",0);
                                                String lat = sp2.getString("lat",null);
                                                String longi = sp2.getString("long",null);



                                                String id = (myRef.push().getKey()+currentDateFormat());


                                                myRef1.child(id).child("lati").setValue(Double.parseDouble(lat));
                                                myRef1.child(id).child("longi").setValue(Double.parseDouble(longi));





                                                SharedPreferences sp1= getSharedPreferences("Login",0);

                                                String unm=sp1.getString("Unm", null);
                                                String propic = sp1.getString("propic",null);



                                                //String username = unm;
                                                myRef.child(id).child("username").setValue(unm);
                                                myRef.child(id).child("description").setValue(description.getText().toString().trim());
                                                myRef.child(id).child("filtername").setValue("GET HIGH");


                                                myRef.child(id).child("image").setValue(imagestring);
                                                myRef.child(id).child("propic").setValue(propic);


                                                String ts = String.valueOf(System.currentTimeMillis());
                                                myRef.child(id).child("time").setValue(ts);

                                                String likes = "";
                                                myRef.child(id).child("likes").setValue(likes);

                                                String comments = "";
                                                myRef.child(id).child("comments").setValue(comments);





                                                //Toast.makeText(getContext(), "Image uploaded",Toast.LENGTH_SHORT).show();
                                                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clicksound);
                                                mp.start();



                                                Intent intent = new Intent(getApplicationContext(), social.class);
                                                startActivity(intent);


                                            }
                                        });


                                        uploadTask1.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                System.out.println("Upload is " + progress + "% done");
                                                int currentprogress = (int) progress;


                                            }
                                        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                                                System.out.println("Upload is paused");
                                            }
                                        });


                                        break;

                                    case 2:



                                        int w1 = originalbitmap.getWidth();
                                        int h1 = originalbitmap.getHeight();

                                        Rect rect1 = new Rect(0, 0, w1, h1);

                                        Bitmap image1 = Bitmap.createBitmap(rect1.width(), rect1.height(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas1 = new Canvas(image1);

                                        int color1 = Color.argb(60, 153, 86, 37);
                                        Paint paint1 = new Paint();
                                        paint1.setColor(color1);

                                        canvas1.drawRect(rect1, paint1);
                                        canvas1.drawBitmap(image1, 0, 0, null);

                                        Bitmap mergedImages1 = createSingleImageFromMultipleImages(originalbitmap, image1);



                                        String id2 = (myRef.push().getKey());

                                        File imageFile2 = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "Filter1.jpg");

                                        OutputStream os1;
                                        try {
                                            os1 = new FileOutputStream(imageFile2);

                                            mergedImages1.compress(Bitmap.CompressFormat.JPEG, 80, os1);
                                            os1.flush();
                                            os1.close();
                                        } catch (Exception e) {
                                            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                                        }

                                        //Uri tempuri = Uri.fromFile(imagefile);





                                        UploadTask uploadTask2 = mStorage.child(id2).child("photo").putFile(FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile2));
                                        uploadTask2.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                Toast.makeText(getApplicationContext(), "Failed to upload image",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                String imagestring = downloadUrl.toString();
                                                database = FirebaseDatabase.getInstance();
                                                myRef = database.getReference("Feed");
                                                myRef1 = database.getReference("locations");

                                                SharedPreferences sp2= getSharedPreferences("location",0);
                                                String lat = sp2.getString("lat",null);
                                                String longi = sp2.getString("long",null);



                                                String id = (myRef.push().getKey()+currentDateFormat());


                                                myRef1.child(id).child("lati").setValue(Double.parseDouble(lat));
                                                myRef1.child(id).child("longi").setValue(Double.parseDouble(longi));





                                                SharedPreferences sp1= getSharedPreferences("Login",0);

                                                String unm=sp1.getString("Unm", null);
                                                String propic = sp1.getString("propic",null);



                                                //String username = unm;
                                                myRef.child(id).child("username").setValue(unm);
                                                myRef.child(id).child("description").setValue(description.getText().toString().trim());

                                                myRef.child(id).child("filtername").setValue("4:20");


                                                myRef.child(id).child("image").setValue(imagestring);
                                                myRef.child(id).child("propic").setValue(propic);


                                                String ts = String.valueOf(System.currentTimeMillis());
                                                myRef.child(id).child("time").setValue(ts);

                                                String likes = "";
                                                myRef.child(id).child("likes").setValue(likes);

                                                String comments = "";
                                                myRef.child(id).child("comments").setValue(comments);





                                                //Toast.makeText(getContext(), "Image uploaded",Toast.LENGTH_SHORT).show();
                                                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clicksound);
                                                mp.start();

                                                Intent intent = new Intent(getApplicationContext(), social.class);
                                                startActivity(intent);


                                            }
                                        });


                                        uploadTask2.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                System.out.println("Upload is " + progress + "% done");
                                                int currentprogress = (int) progress;


                                            }
                                        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                                                System.out.println("Upload is paused");
                                            }
                                        });



                                        break;

                                    case 3:





                                        int w2 = originalbitmap.getWidth();
                                        int h2 = originalbitmap.getHeight();

                                        Rect rect2 = new Rect(0, 0, w2, h2);

                                        Bitmap image2 = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas2 = new Canvas(image2);

                                        int color2 = Color.argb(60, 71, 159, 216);
                                        Paint paint2 = new Paint();
                                        paint2.setColor(color2);

                                        canvas2.drawRect(rect2, paint2);
                                        canvas2.drawBitmap(image2, 0, 0, null);

                                        Bitmap mergedImages2 = createSingleImageFromMultipleImages(originalbitmap, image2);

                                        File imageFile3 = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "filter123.jpg");

                                        OutputStream os2;
                                        try {
                                            os2 = new FileOutputStream(imageFile3);

                                            mergedImages2.compress(Bitmap.CompressFormat.JPEG, 80, os2);
                                            os2.flush();
                                            os2.close();
                                        } catch (Exception e) {
                                            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                                        }




                                        String id3 = (myRef.push().getKey());

                                        UploadTask uploadTask3 = mStorage.child(id3).child("photo").putFile(FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile3));
                                        uploadTask3.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                Toast.makeText(getApplicationContext(), "Failed to upload image",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                String imagestring = downloadUrl.toString();
                                                database = FirebaseDatabase.getInstance();
                                                myRef = database.getReference("Feed");
                                                myRef1 = database.getReference("locations");

                                                SharedPreferences sp2= getSharedPreferences("location",0);
                                                String lat = sp2.getString("lat",null);
                                                String longi = sp2.getString("long",null);



                                                String id = (myRef.push().getKey()+currentDateFormat());


                                                myRef1.child(id).child("lati").setValue(Double.parseDouble(lat));
                                                myRef1.child(id).child("longi").setValue(Double.parseDouble(longi));





                                                SharedPreferences sp1= getSharedPreferences("Login",0);

                                                String unm=sp1.getString("Unm", null);
                                                String propic = sp1.getString("propic",null);



                                                //String username = unm;
                                                myRef.child(id).child("username").setValue(unm);
                                                myRef.child(id).child("description").setValue(description.getText().toString().trim());
                                                myRef.child(id).child("filtername").setValue("HIGH THERE");


                                                myRef.child(id).child("image").setValue(imagestring);
                                                myRef.child(id).child("propic").setValue(propic);


                                                String ts = String.valueOf(System.currentTimeMillis());
                                                myRef.child(id).child("time").setValue(ts);

                                                String likes = "";
                                                myRef.child(id).child("likes").setValue(likes);

                                                String comments = "";
                                                myRef.child(id).child("comments").setValue(comments);





                                                //Toast.makeText(getContext(), "Image uploaded",Toast.LENGTH_SHORT).show();
                                                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clicksound);
                                                mp.start();

                                                Intent intent = new Intent(getApplicationContext(), social.class);
                                                startActivity(intent);


                                            }
                                        });


                                        uploadTask3.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                System.out.println("Upload is " + progress + "% done");
                                                int currentprogress = (int) progress;


                                            }
                                        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                                                System.out.println("Upload is paused");
                                            }
                                        });


                                        break;

                                    case 4:



                                        int w3 = originalbitmap.getWidth();
                                        int h3 = originalbitmap.getHeight();

                                        Rect rect3 = new Rect(0, 0, w3, h3);

                                        Bitmap image3 = Bitmap.createBitmap(rect3.width(), rect3.height(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas3 = new Canvas(image3);

                                        int color3 = Color.argb(60, 168, 103, 171);
                                        Paint paint3 = new Paint();
                                        paint3.setColor(color3);

                                        canvas3.drawRect(rect3, paint3);
                                        canvas3.drawBitmap(image3, 0, 0, null);

                                        Bitmap mergedImages3 = createSingleImageFromMultipleImages(originalbitmap, image3);

                                        File imageFile4 = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "difekek.jpg");

                                        OutputStream os3;
                                        try {
                                            os3 = new FileOutputStream(imageFile4);

                                            mergedImages3.compress(Bitmap.CompressFormat.JPEG, 80, os3);
                                            os3.flush();
                                            os3.close();
                                        } catch (Exception e) {
                                            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                                        }



                                        String id4 = (myRef.push().getKey());

                                        UploadTask uploadTask4 = mStorage.child(id4).child("photo").putFile(FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile4));
                                        uploadTask4.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                Toast.makeText(getApplicationContext(), "Failed to upload image",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                String imagestring = downloadUrl.toString();
                                                database = FirebaseDatabase.getInstance();
                                                myRef = database.getReference("Feed");
                                                myRef1 = database.getReference("locations");

                                                SharedPreferences sp2= getSharedPreferences("location",0);
                                                String lat = sp2.getString("lat",null);
                                                String longi = sp2.getString("long",null);



                                                String id = (myRef.push().getKey()+currentDateFormat());


                                                myRef1.child(id).child("lati").setValue(Double.parseDouble(lat));
                                                myRef1.child(id).child("longi").setValue(Double.parseDouble(longi));





                                                SharedPreferences sp1= getSharedPreferences("Login",0);

                                                String unm=sp1.getString("Unm", null);
                                                String propic = sp1.getString("propic",null);



                                                //String username = unm;
                                                myRef.child(id).child("username").setValue(unm);
                                                myRef.child(id).child("description").setValue(description.getText().toString().trim());
                                                myRef.child(id).child("filtername").setValue("IT'S MY LIFE");


                                                myRef.child(id).child("image").setValue(imagestring);
                                                myRef.child(id).child("propic").setValue(propic);


                                                String ts = String.valueOf(System.currentTimeMillis());
                                                myRef.child(id).child("time").setValue(ts);

                                                String likes = "";
                                                myRef.child(id).child("likes").setValue(likes);

                                                String comments = "";
                                                myRef.child(id).child("comments").setValue(comments);





                                                //Toast.makeText(getContext(), "Image uploaded",Toast.LENGTH_SHORT).show();
                                                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clicksound);
                                                mp.start();


                                                Intent intent = new Intent(getApplicationContext(), social.class);
                                                startActivity(intent);


                                            }
                                        });


                                        uploadTask4.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                System.out.println("Upload is " + progress + "% done");
                                                int currentprogress = (int) progress;


                                            }
                                        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                                                System.out.println("Upload is paused");
                                            }
                                        });


                                        break;

                                    case 5:



                                        int w4 = originalbitmap.getWidth();
                                        int h4 = originalbitmap.getHeight();

                                        Rect rect4 = new Rect(0, 0, w4, h4);

                                        Bitmap image4 = Bitmap.createBitmap(rect4.width(), rect4.height(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas4 = new Canvas(image4);

                                        int color4 = Color.argb(60, 1, 1, 1);
                                        Paint paint4 = new Paint();
                                        paint4.setColor(color4);

                                        canvas4.drawRect(rect4, paint4);
                                        canvas4.drawBitmap(image4, 0, 0, null);

                                        Bitmap mergedImages4 = createSingleImageFromMultipleImages(originalbitmap, image4);

                                        File imageFile5 = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "awecec.jpg");

                                        OutputStream os4;
                                        try {
                                            os4 = new FileOutputStream(imageFile5);

                                            mergedImages4.compress(Bitmap.CompressFormat.JPEG, 80, os4);
                                            os4.flush();
                                            os4.close();
                                        } catch (Exception e) {
                                            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                                        }



                                        String id5 = (myRef.push().getKey());

                                        UploadTask uploadTask5 = mStorage.child(id5).child("photo").putFile(FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile5));
                                        uploadTask5.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                Toast.makeText(getApplicationContext(), "Failed to upload image",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                String imagestring = downloadUrl.toString();
                                                database = FirebaseDatabase.getInstance();
                                                myRef = database.getReference("Feed");
                                                myRef1 = database.getReference("locations");

                                                SharedPreferences sp2= getSharedPreferences("location",0);
                                                String lat = sp2.getString("lat",null);
                                                String longi = sp2.getString("long",null);



                                                String id = (myRef.push().getKey()+currentDateFormat());


                                                myRef1.child(id).child("lati").setValue(Double.parseDouble(lat));
                                                myRef1.child(id).child("longi").setValue(Double.parseDouble(longi));





                                                SharedPreferences sp1= getSharedPreferences("Login",0);

                                                String unm=sp1.getString("Unm", null);
                                                String propic = sp1.getString("propic",null);



                                                //String username = unm;
                                                myRef.child(id).child("username").setValue(unm);
                                                myRef.child(id).child("description").setValue(description.getText().toString().trim());
                                                myRef.child(id).child("filtername").setValue("My 420 Moment");


                                                myRef.child(id).child("image").setValue(imagestring);
                                                myRef.child(id).child("propic").setValue(propic);


                                                String ts = String.valueOf(System.currentTimeMillis());
                                                myRef.child(id).child("time").setValue(ts);

                                                String likes = "";
                                                myRef.child(id).child("likes").setValue(likes);

                                                String comments = "";
                                                myRef.child(id).child("comments").setValue(comments);





                                                //Toast.makeText(getContext(), "Image uploaded",Toast.LENGTH_SHORT).show();
                                                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clicksound);
                                                mp.start();


                                                Intent intent = new Intent(getApplicationContext(), social.class);
                                                startActivity(intent);


                                            }
                                        });


                                        uploadTask5.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                System.out.println("Upload is " + progress + "% done");
                                                int currentprogress = (int) progress;


                                            }
                                        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                                                System.out.println("Upload is paused");
                                            }
                                        });


                                        break;
                                }

                            }
                        });
                    }
                }).start();










            }
        });



        //Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    private void setupViewPager(ViewPager viewPager) {
        filter.ViewPagerAdapter adapter = new filter.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new nofilter(), "0");
        adapter.addFragment(new filterone(), "1");
        adapter.addFragment(new filtertwo(), "2");
        adapter.addFragment(new filterthree(), "3");
        adapter.addFragment(new filterfour(), "4");

        //adapter.addFragment(new groupsFragment(), "Groups");
        //adapter.addFragment(new dateFragment(), "Meet");
        //adapter.addFragment(new chatFragment(), "Chats");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {


            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }





    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage){

        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, 0f, 0f, null);
        return result;
    }
}
