package com.social420.social420;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.Random;

public class social_profile extends AppCompatActivity {

    ImageView selectedImage;
    private TagView tagView;
    private TextView editText;
    private Random random;
    ImageView slide;

    private CallbackManager mCallbackManager;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private Uri uri;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_new);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        AssetManager assetManager = this.getAssets();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView togglen = (ImageView) findViewById(R.id.togglenew);

        togglen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(social_profile.this, social.class);
                startActivity(in);
            }
        });



        /*

        Gallery gallery = (Gallery) findViewById(R.id.gallery);

        selectedImage=(ImageView)findViewById(R.id.slide);
        gallery.setSpacing(1);
        final GalleryImageAdapter galleryImageAdapter= new GalleryImageAdapter(this);
        gallery.setAdapter(galleryImageAdapter);
        gallery.setSelection(1);



        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // show the selected Image
                selectedImage.setImageResource(galleryImageAdapter.mImageIds[position]);
            }
        });

        */



        //findViewById(R.id.tv_add).setOnClickListener(this);

        Typeface mytypeface1 = Typeface.createFromAsset(assetManager, "Montserrat-Regular.ttf");




        SharedPreferences sp1= this.getSharedPreferences("Login",0);

        String unm=sp1.getString("Unm", null);
        String propicbig = sp1.getString("propicbig",null);

        TextView editText = (TextView) findViewById(R.id.profile_name);

        editText.setTypeface(mytypeface1);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        SharedPreferences sp3= getSharedPreferences("profileid",0);

        String newid1 =sp3.getString("id", null);

        if (newid1 != null){

            myRef.child(newid1).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String name = (String) (snapshot.getValue());

                    TextView editText1 = (TextView) findViewById(R.id.profile_name);
                    editText1.setText(name);

                }
                @Override public void onCancelled(DatabaseError error) {

                }
            });
        }
        else{

            editText.setText(unm);

        }


        //editText.setText(unm, TextView.BufferType.EDITABLE);





        ImageView post_image = (ImageView) findViewById(R.id.slide);

        if (propicbig.contains("https")){
            Picasso.with(getApplicationContext()).load(propicbig).placeholder(R.mipmap.imageload).into(post_image);

        }
        else {


            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Users");
            SharedPreferences sp2= getSharedPreferences("profileid",0);

            String newid =sp2.getString("id", null);

            if (newid != null){

                myRef.child(newid).child("profilepic").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String image = (String) (snapshot.getValue());
                        ImageView post_image = (ImageView) findViewById(R.id.slide);
                        Picasso.with(getApplicationContext()).load(image).placeholder(R.mipmap.imageload).into(post_image);//prints "Do you have data? You'll love Firebase."
                    }
                    @Override public void onCancelled(DatabaseError error) {

                    }
                });
            }
            else{
                ImageView post_image1 = (ImageView) findViewById(R.id.slide);
                post_image1.setImageResource(R.mipmap.imageload);
            }









        }


        /*

        TextView tvadd = (TextView) findViewById(R.id.tv_add);
        tvadd.setVisibility(View.GONE);

        editText = (EditText) findViewById(R.id.edit_tag);
        editText.setVisibility(View.GONE);


        tagView = (TagView) this.findViewById(R.id.tagview);
        //SET LISTENER
        tagView.setOnTagClickListener(new OnTagClickListener() {

            @Override
            public void onTagClick(int position, Tag tag) {

            }
        });
        tagView.setOnTagDeleteListener(new OnTagDeleteListener() {

            @Override
            public void onTagDeleted(int position, Tag tag) {

            }
        });
        //ADD TAG


        String[] colors = this.getResources().getStringArray(R.array.colors);

        Tag tag = new Tag("Border");
        tag = new Tag("Custom Background");
        tag.tagTextColor = Color.parseColor(colors[0]);
        tag.background = this.getResources().getDrawable(R.drawable.bg_tag);
        tagView.addTag(tag);

        */

















    }



}
