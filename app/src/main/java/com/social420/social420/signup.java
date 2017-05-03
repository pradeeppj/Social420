package com.social420.social420;

/**
 * Created by pradeeppj on 18/04/17.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mindorks.placeholderview.annotations.View;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class signup extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    private ImageView fb;
    private LoginButton loginButton;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ImageView loginimage;
    private EditText name;
    private Boolean propicadded ;
    private String propicurl;
    private File imagefile;
    private CircleImageView profilepic;
    private static final int RESULT_LOAD_IMG = 668;
    String imgDecodableString;
    private TextView addpic;
    private StorageReference mStorage;
    private String idnew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mStorage = FirebaseStorage.getInstance().getReference();

        name = (EditText)findViewById(R.id.name);
        TextView mytextview1 = (TextView) findViewById(R.id.letin);
        addpic = (TextView) findViewById(R.id.addpic);
        profilepic = (CircleImageView)findViewById(R.id.proooo);



        Typeface mytypeface1 = Typeface.createFromAsset(getAssets(), "Montserrat-Light.ttf");

        mytextview1.setTypeface(mytypeface1);
        name.setTypeface(mytypeface1);




        profilepic.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                imagefile = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Test.jpg");
                Uri tempuri = Uri.fromFile(imagefile);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
                galleryIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");


        mytextview1.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                if( name.getText().toString().trim().equals("")){

                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/

                    name.setError( "Your name is required!" );

                }


                else{




                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Users");

                    SharedPreferences sp1= getSharedPreferences("profileid",0);

                    String newid =sp1.getString("id", null);

                    if (newid != null){
                        myRef.child(newid).child("name").setValue(name.getText().toString().trim());

                        SharedPreferences sp=getSharedPreferences("Login", 0);
                        SharedPreferences.Editor Ed=sp.edit();
                        Ed.putString("Unm",name.getText().toString().trim());
                        Ed.putString("id",newid);
                        Ed.putString("signedup","signedup");

                        Ed.commit();
                    }
                    else{
                        String idd = (myRef.push().getKey());
                        myRef.child(idd).child("name").setValue(name.getText().toString().trim());

                        SharedPreferences sp=getSharedPreferences("Login", 0);
                        SharedPreferences.Editor Ed=sp.edit();
                        Ed.putString("Unm",name.getText().toString().trim());
                        Ed.putString("id",idd);
                        Ed.putString("signedup","signedup");

                        Ed.commit();
                    }
















                    Intent i = new Intent(getApplicationContext(), multiple_permissions.class);
                    startActivity(i);


                }





            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                addpic.setVisibility(android.view.View.GONE);

                // Set the Image in ImageView after decoding the String
                profilepic.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                profilepic.setImageURI(selectedImage);

                imagefile = new File(selectedImage.toString());

                String id = (myRef.push().getKey());

                UploadTask uploadTask = mStorage.child(id).child("photo").putFile(selectedImage);
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
                        myRef = database.getReference("Users");

                        String id = (myRef.push().getKey());

                        SharedPreferences sp=getSharedPreferences("profileid", 0);
                        SharedPreferences.Editor Ed=sp.edit();
                        Ed.putString("id",id );
                        Ed.commit();




                        myRef.child(id).child("profilepic").setValue(imagestring);








                    }
                });




















            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }





}
