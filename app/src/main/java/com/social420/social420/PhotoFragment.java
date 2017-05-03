package com.social420.social420;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pradeeppj on 05/04/17.
 */

public class PhotoFragment extends Fragment {


    private View rootview;
    private Context context;
    private Uri fileUri;
    static final Integer CAMERA = 0x5;

    static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int CAMERA_REQUEST = 6768;
    private static final int READ_EXTERNAL_STORAGE = 786;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    public static final String path="";

    private Uri imageToUploadUri;
    private Uri imageuri;
    private String randomn;
    private String filepath;
    private File file;
    private Uri outputuri;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorage;
    public int i = 0;

    FirebaseDatabase database;
    DatabaseReference myRef;

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        rootview = (View) inflater.inflate(R.layout.fragment_photo, container, false);
        askForPermission(android.Manifest.permission.CAMERA, CAMERA);














        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {





            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] datanew = outputStream.toByteArray();





            String id = (myRef.push().getKey()+currentDateFormat());

            UploadTask uploadTask = mStorage.child(id).child("photo").putBytes(datanew);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    Toast.makeText(getContext(), "Failed to upload image",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    String imagestring = downloadUrl.toString();

                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Feed");


                    String id = (myRef.push().getKey()+currentDateFormat());
                    myRef.child(id).child("image").setValue(imagestring);

                    String username = "Roccodharan";
                    myRef.child(id).child("username").setValue(username);

                    String ts = String.valueOf(System.currentTimeMillis());
                    myRef.child(id).child("time").setValue(ts);

                    String likes = "";
                    myRef.child(id).child("likes").setValue(likes);

                    String comments = "";
                    myRef.child(id).child("comments").setValue(comments);


                    Toast.makeText(getContext(), "Image uploaded",
                            Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(getActivity(), social.class);
                    startActivity(in);

                }
            });







        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED){

            //Location

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraIntent, CAMERA_REQUEST);




            Toast.makeText(getActivity(), "Camera started", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }

    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }



    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                Toast.makeText(getContext(), "" + permission + " Permission Granted.", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraIntent, CAMERA_REQUEST);
            Toast.makeText(getContext(), "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
}
