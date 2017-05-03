package com.social420.social420;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatfullscreen extends AppCompatActivity {


    private RecyclerView mBlogList;
    FirebaseDatabase jdatabase;
    DatabaseReference jmyRef;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatfullscreen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Casual Smokers");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        layoutManager = new LinearLayoutManager(this);
        mBlogList = (RecyclerView) findViewById(R.id.chatlist);
        mBlogList.setHasFixedSize(true);
        //layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);

        jdatabase = FirebaseDatabase.getInstance();
        jmyRef = jdatabase.getReference("chatroom");

        ImageView send = (ImageView)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("chatroom");

                EditText messagesend = (EditText)findViewById(R.id.entermessage);
                String sendmessage = messagesend.getText().toString();


                String id = (myRef.push().getKey());


                SharedPreferences sp1= v.getContext().getSharedPreferences("Login",0);

                String unm=sp1.getString("Unm", null);
                String propic = sp1.getString("propic",null);



                //String username = unm;
                myRef.child(id).child("message").setValue(sendmessage);
                String ts = String.valueOf(System.currentTimeMillis());
                myRef.child(id).child("time").setValue(ts);



                myRef.child(id).child("propic").setValue(propic);
                messagesend.setText(null);

                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.clicksound);
                mp.start();








            }
        });






    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    FirebaseRecyclerAdapter firebaseRecyclerAdapter;


    @Override
    public void onStart() {
        super.onStart();


         firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<chatfullclass, chatfullscreen.BlogViewHolder>(
                        chatfullclass.class,
                        R.layout.chatlayout,
                        chatfullscreen.BlogViewHolder.class,
                        jmyRef)  {



                    @Override
                    protected void populateViewHolder(chatfullscreen.BlogViewHolder viewHolder, chatfullclass model, int position) {



                        viewHolder.setMessage(model.getMessage());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setImage(getApplicationContext(), model.getImage());
                        viewHolder.setPropic(getApplicationContext(),model.getPropic());
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

    public static class BlogViewHolder extends RecyclerView.ViewHolder  {
        View mView;
        Context ctx;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView= itemView;



            Typeface mytypeface1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview1 = (TextView) mView.findViewById(R.id.time);
            mytextview1.setTypeface(mytypeface1);
            Typeface mytypeface2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview2 = (TextView) mView.findViewById(R.id.message);
            mytextview2.setTypeface(mytypeface2);

















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
        public void setMessage(String message){



            TextView post_title1 = (TextView)mView.findViewById(R.id.message);
            post_title1.setText(message);



        }

        public void setTime(String time){



            TextView post_title2 = (TextView)mView.findViewById(R.id.time);
            Long posttime = Long.parseLong(time);

            String timenw = DateUtil.elapsedTime(System.currentTimeMillis() - posttime);
            post_title2.setText(timenw);


        }





        public void setImage(Context ctx , String image){






            ImageView post_image = (ImageView)mView.findViewById(R.id.image_media);




            if(image!=null){

                Picasso.with(ctx).load(image).into(post_image);
            }
            else {


                post_image.setVisibility(View.GONE);
                Picasso.with(ctx).load(R.drawable.noimage).into(post_image);

            }





        }

        public void setPropic(Context ctx , String image){


            CircleImageView post_image = (CircleImageView)mView.findViewById(R.id.propicnee);

            Picasso.with(ctx).load(image).placeholder(R.drawable.pro_pic).into(post_image);






        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




}
