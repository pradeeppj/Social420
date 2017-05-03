package com.social420.social420;

/**
 * Created by pradeeppj on 11/02/17.
 */

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class discover_fragment extends Fragment implements OnMapReadyCallback {


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



        private RecyclerView mBlogList;
        FirebaseDatabase database;
        DatabaseReference myRef1;
        private ProgressDialog mProgress;
        private ProgressBar progressBar;
        private ChildEventListener mChildEventListener;
        private GoogleMap map;

        private LinearLayoutManager layoutManager;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootview = inflater.inflate(R.layout.fragment_discover, container, false);

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            SupportMapFragment fragment = new SupportMapFragment();
            transaction.add(R.id.mapView, fragment);
            transaction.commit();

            fragment.getMapAsync(this);

            database = FirebaseDatabase.getInstance();
            myRef1 = database.getReference("locations");

            return rootview;
        }

        @Override
        public void onMapReady(GoogleMap map) {






            //get marker info from Firebase Database and add to map
            addMarkersToMap(map);

            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);




        }

        @Override
        public void onStop(){
            if(mChildEventListener != null)
                myRef1.removeEventListener(mChildEventListener);
            super.onStop();
        }



        private void addMarkersToMap(GoogleMap map){

            final GoogleMap mapnew = map;

            mChildEventListener = myRef1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    FirebaseMarker marker = dataSnapshot.getValue(FirebaseMarker.class);

                    double latitude = marker.getLatitude();
                    double longitude = marker.getLongitude();

                    LatLng location = new LatLng(latitude,longitude);
                    mapnew.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }











