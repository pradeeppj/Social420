package com.social420.social420;

/**
 * Created by pradeeppj on 11/02/17.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class OneFragment extends Fragment{

    private View rootview;
    private Context context;





    public OneFragment() {


    }

    private RecyclerView mBlogList;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = (View) inflater.inflate(R.layout.fragment_one, container, false);

        //String[] foods = {"Kushagra", "Pradeep", "Gautham","Prakash","Sunil"};

        //ListAdapter myadapter = new FeedAdapter(getActivity(),foods);
        //ListView mylistview = (ListView) rootview.findViewById(R.id.feed_list);
        //mylistview.setAdapter(myadapter);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mBlogList = (RecyclerView) rootview.findViewById(R.id.feed_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(layoutManager);






        //GPSTracker gpsTracker = new GPSTracker(getActivity());

        //if (gpsTracker.getIsGPSTrackingEnabled())

        /*
        {



            Location loc1 = new Location("");
            loc1.setLatitude(gpsTracker.latitude);
            loc1.setLongitude(gpsTracker.longitude); //takes double value

            Location loc2 = new Location("");
            loc2.setLatitude(12.9279232);
            loc2.setLongitude(77.62710779999998);

            float distanceInMeters = loc1.distanceTo(loc2);

            //long distance;
            //distance = (long) (distanceInMeters*0.000621371);

            String finaldist = String.valueOf(distanceInMeters);



                /*
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Data");

            String id = myRef.push().getKey();

            Distance distan = new Distance(id, finaldist);

            myRef.child(id).setValue(distan);

            */




            //myRef.child("Post 1").child("dist").setValue("1");



            /*
            String country = gpsTracker.getCountryName(this);
            textview = (TextView)findViewById(R.id.fieldCountry);
            textview.setText(country);

            String city = gpsTracker.getLocality(this);
            textview = (TextView)findViewById(R.id.fieldCity);
            textview.setText(city);

            String postalCode = gpsTracker.getPostalCode(this);
            textview = (TextView)findViewById(R.id.fieldPostalCode);
            textview.setText(postalCode);

            String addressLine = gpsTracker.getAddressLine(this);
            textview = (TextView)findViewById(R.id.fieldAddressLine);
            textview.setText(addressLine);
            */


        //}



        //else
        //{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            //gpsTracker.showSettingsAlert();
        //}




        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Data");




        return rootview;
    }


    @Override
    public void onStart() {
        super.onStart();



        FirebaseRecyclerAdapter<ModelClass, BlogViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelClass, BlogViewHolder>(
                        ModelClass.class,
                        R.layout.feed_header,
                        BlogViewHolder.class,
                        myRef)  {



                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, ModelClass model,int position) {

                        viewHolder.setThc(model.getThc());
                        viewHolder.setCbd(model.getCbd());
                        viewHolder.setCbn(model.getCbn());
                        viewHolder.setDist(model.getDist());

                        viewHolder.setClaims(model.getClaims());
                        viewHolder.setStorename(model.getStorename());
                        viewHolder.setQuantity(model.getQuantity());
                        viewHolder.setPrice(model.getPrice());
                        viewHolder.setName(model.getName());
                        viewHolder.setImage(getActivity(), model.getImage());
                    }
                };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder  {
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView= itemView;



            Typeface mytypeface1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview1 = (TextView) mView.findViewById(R.id.price);
            mytextview1.setTypeface(mytypeface1);
            Typeface mytypeface2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview2 = (TextView) mView.findViewById(R.id.enrootuser);
            mytextview2.setTypeface(mytypeface2);

            Typeface mytypeface3 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview3 = (TextView) mView.findViewById(R.id.quantity);
            mytextview3.setTypeface(mytypeface3);

            Typeface mytypeface4 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview4 = (TextView) mView.findViewById(R.id.storename);
            mytextview4.setTypeface(mytypeface4);


            Typeface mytypeface5 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview5 = (TextView) mView.findViewById(R.id.thc);
            mytextview5.setTypeface(mytypeface5);

            Typeface mytypeface6 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview6 = (TextView) mView.findViewById(R.id.cbd);
            mytextview6.setTypeface(mytypeface6);

            Typeface mytypeface7 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview7 = (TextView) mView.findViewById(R.id.cbn);
            mytextview7.setTypeface(mytypeface7);

            Typeface mytypeface8 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview8 = (TextView) mView.findViewById(R.id.claims);
            mytextview8.setTypeface(mytypeface8);



            Typeface mytypeface9 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview9 = (TextView) mView.findViewById(R.id.dist);
            mytextview9.setTypeface(mytypeface9);

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
        public void setName(String name){



            TextView post_title1 = (TextView)mView.findViewById(R.id.enrootuser);
            post_title1.setText(name);

        }

        public void setPrice(String price){



            TextView post_title2 = (TextView)mView.findViewById(R.id.price);
            post_title2.setText(price);

        }

        public void setQuantity(String quantity){



            TextView post_title3 = (TextView)mView.findViewById(R.id.quantity);
            post_title3.setText(quantity);

        }

        public void setStorename(String storename){



            TextView post_title4 = (TextView)mView.findViewById(R.id.storename);
            post_title4.setText(storename);

        }



        public void setThc(String thc){



            TextView post_title5 = (TextView)mView.findViewById(R.id.thc);
            post_title5.setText("THC : " + thc);

        }


        public void setCbd(String cbd){



            TextView post_title6 = (TextView)mView.findViewById(R.id.cbd);
            post_title6.setText("CBD : " + cbd);

        }

        public void setCbn(String cbn){



            TextView post_title7 = (TextView)mView.findViewById(R.id.cbn);
            post_title7.setText("CBN : "+ cbn);

        }

        public void setClaims(String claims){



            TextView post_title8 = (TextView)mView.findViewById(R.id.claims);
            post_title8.setText(claims);

        }

        public void setDist(String dist){



            TextView post_title9 = (TextView)mView.findViewById(R.id.dist);

            post_title9.setText(dist);

        }




        public void setImage(Context ctx , String image){
            ImageView post_image = (ImageView)mView.findViewById(R.id.default_pic);
            // We Need TO pass Context
            Picasso.with(ctx).load(image).into(post_image);
        }


    }


}