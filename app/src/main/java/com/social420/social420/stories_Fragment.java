package com.social420.social420;

/**
 * Created by pradeeppj on 11/02/17.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.CallbackManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


public class stories_Fragment extends Fragment{


    private View rootview;
    private Context context;
    private Uri fileUri;
    static final Integer CAMERA = 0x5;
    static final Integer WRITE_EXST = 0x3;
    static final Integer READ_EXST = 0x4;

    static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int CAMERA_REQUEST = 6768;
    private static final int READ_EXTERNAL_STORAGE = 786;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    public static final String path="";

    private Uri imageToUploadUri;
    private Uri imageuri;
    private String randomn;
    private String filepath;
    private File imagefile;
    private Uri outputuri;
    private Uri mFileUri = null;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorage;
    public int i = 0;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    public stories_Fragment() {
        // Required empty public constructor
    }

    private RecyclerView mBlogList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef1;
    private ProgressDialog mProgress;
    private ProgressBar progressBar;
    private String stringLatitude;
    private String stringLongitude;
    private ImageView comment;

    private LinearLayoutManager layoutManager;
    //private GPSTracker gpsTracker = new GPSTracker(getActivity());




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        rootview = (View) inflater.inflate(R.layout.fragment_three, container, false);
        mProgress = new ProgressDialog(getActivity());
        mStorage = FirebaseStorage.getInstance().getReference();
        progressBar = (ProgressBar) rootview.findViewById(R.id.progressBar);

        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.facebook.samples.hellofacebook",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }




        GPSTracker gpsTracker = new GPSTracker(getActivity());

        if (gpsTracker.canGetLocation())
        {
             stringLatitude = String.valueOf(gpsTracker.getLatitude());


             stringLongitude = String.valueOf(gpsTracker.getLongitude());



        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }


        SharedPreferences sp= getActivity().getSharedPreferences("location", 0);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("lat",stringLatitude );
        Ed.putString("long",stringLongitude);
        Ed.commit();





        ImageView fab1 = (ImageView) rootview.findViewById(R.id.addphoto);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                imagefile = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Test.jpg");
                //Uri tempuri = Uri.fromFile(imagefile);


                Uri tempuri = FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imagefile);



                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
                cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                //askForPermission(Manifest.permission.CAMERA,CAMERA);


            }
        });





        layoutManager = new LinearLayoutManager(getActivity());
        mBlogList = (RecyclerView) rootview.findViewById(R.id.stories_list);
        mBlogList.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);






        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Feed");

        // Inflate the layout for this fragment
        return rootview;
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {

        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inScaled = false;

        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length, opt);
    }

    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelClass1, stories_Fragment.BlogViewHolder>(
                        ModelClass1.class,
                        R.layout.stories_header,
                        stories_Fragment.BlogViewHolder.class,
                        myRef)  {



                    @Override
                    protected void populateViewHolder(stories_Fragment.BlogViewHolder viewHolder, ModelClass1 model, int position) {

                        viewHolder.setUsername(model.getUsername());
                        viewHolder.setLikes(model.getLikes());
                        viewHolder.setComments(model.getComments());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setImage(getActivity(), model.getImage());
                        viewHolder.setPropic(getActivity(),model.getPropic());
                    }
                };
        mBlogList.setAdapter(firebaseRecyclerAdapter);


        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = firebaseRecyclerAdapter.getItemCount();
                int lastVisiblePosition =
                        layoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mBlogList.scrollToPosition(positionStart);
                }
            }
        });




    }


    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(1000);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder  {
        View mView;
        Context ctx;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView= itemView;


            final ToggleButton liketoggle = (ToggleButton)mView.findViewById(R.id.likebutton);
            liketoggle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {

                }
            });

            Typeface mytypeface1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview1 = (TextView) mView.findViewById(R.id.timeago);
            mytextview1.setTypeface(mytypeface1);
            Typeface mytypeface2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview2 = (TextView) mView.findViewById(R.id.enrootuser);
            mytextview2.setTypeface(mytypeface2);



            Typeface mytypeface3 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview3 = (TextView) mView.findViewById(R.id.likes);
            mytextview3.setTypeface(mytypeface3);

            Typeface mytypeface4 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview4 = (TextView) mView.findViewById(R.id.noofshares);
            mytextview4.setTypeface(mytypeface4);


            ImageView comment = (ImageView)mView.findViewById(R.id.imageView3);
            comment.setVisibility(View.GONE);




            ImageView profilepic = (ImageView)mView.findViewById(R.id.default_pic);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    //Uri.parse("http://www.androidsquad.space/"));
                    //Intent browserChooserIntent = Intent.createChooser(browserIntent , "Choose browser of your choice");
                    //v.getContext().startActivity(browserChooserIntent);
                }
            });
            /******** For More Android Tutorials .. Download "Master Android" Application From Play Store Free********/
        }
        public void setUsername(String username){



            TextView post_title1 = (TextView)mView.findViewById(R.id.enrootuser);
            post_title1.setText(username);

        }

        public void setTime(String time){



            TextView post_title2 = (TextView)mView.findViewById(R.id.timeago);
            Long posttime = Long.parseLong(time);
            post_title2.setText(DateUtil.elapsedTime(System.currentTimeMillis() - posttime));

        }

        public void setLikes(String likes){



            TextView post_title3 = (TextView)mView.findViewById(R.id.likes);
            post_title3.setText(likes);

        }

        public void setComments(String comments){



            TextView post_title4 = (TextView)mView.findViewById(R.id.noofshares);
            post_title4.setText(comments);

        }

        public void setImage(Context ctx , String image){




            ImageView post_image = (ImageView)mView.findViewById(R.id.fullimage);
            Picasso.with(ctx).load(image).placeholder(R.drawable.progress_animation).into(post_image);


        }

        public void setPropic(Context ctx , String image){


            CircleImageView post_image = (CircleImageView)mView.findViewById(R.id.default_pic);

            Picasso.with(ctx).load(image).placeholder(R.drawable.pro_pic).into(post_image);




        }

    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",

                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");

        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED){




           



            /*
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
            //cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);

            startActivityForResult(cameraIntent, CAMERA_REQUEST);

            */




            Toast.makeText(getActivity(), "Camera started", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }

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





            //Toast.makeText(getContext(), "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {






            String id = (myRef.push().getKey()+currentDateFormat());

            UploadTask uploadTask = mStorage.child(id).child("photo").putFile(FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imagefile));
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
                    myRef1 = database.getReference("locations");

                    SharedPreferences sp2= getActivity().getSharedPreferences("location",0);
                    String lat = sp2.getString("lat",null);
                    String longi = sp2.getString("long",null);



                    String id = (myRef.push().getKey()+currentDateFormat());


                    myRef1.child(id).child("lati").setValue(Double.parseDouble(lat));
                    myRef1.child(id).child("longi").setValue(Double.parseDouble(longi));





                    SharedPreferences sp1= getActivity().getSharedPreferences("Login",0);

                    String unm=sp1.getString("Unm", null);
                    String propic = sp1.getString("propic",null);



                    //String username = unm;
                    myRef.child(id).child("username").setValue(unm);


                    myRef.child(id).child("image").setValue(imagestring);
                    myRef.child(id).child("propic").setValue(propic);


                    String ts = String.valueOf(System.currentTimeMillis());
                    myRef.child(id).child("time").setValue(ts);

                    String likes = "";
                    myRef.child(id).child("likes").setValue(likes);

                    String comments = "";
                    myRef.child(id).child("comments").setValue(comments);

                    progressBar.findViewById(R.id.progressBar);


                    progressBar.setVisibility(View.GONE);


                    //Toast.makeText(getContext(), "Image uploaded",Toast.LENGTH_SHORT).show();
                    final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.clicksound);
                    mp.start();


                }
            });


            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    System.out.println("Upload is " + progress + "% done");
                    int currentprogress = (int) progress;


                    progressBar.setVisibility(View.VISIBLE);

                    progressBar.setProgress(currentprogress);
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            });



        }
    }



}






