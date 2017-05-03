package com.social420.social420;

/**
 * Created by pradeeppj on 11/02/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class groupsFragment extends Fragment{

    private View rootview;

    private RecyclerView mRecyclerView;
    private groups_adapter mAdapter;
    private List<Detail> mList = new ArrayList<>();


    FirebaseDatabase database1;
    DatabaseReference myRef1;

    public groupsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = (View) inflater.inflate(R.layout.groups_activity, container, false);




        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        //mAdapter = new groups_adapter(getActivity(), mList);
        //mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        database1 = FirebaseDatabase.getInstance();
        myRef1 = database1.getReference("Groups");


        /*
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        */
        // Inflate the layout for this fragment
        return rootview;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Groupsclass, groupsFragment.BlogViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Groupsclass, groupsFragment.BlogViewHolder>(
                        Groupsclass.class,
                        R.layout.item_list,
                        groupsFragment.BlogViewHolder.class,
                        myRef1)  {



                    @Override
                    protected void populateViewHolder(groupsFragment.BlogViewHolder viewHolder, Groupsclass model, int position) {

                        viewHolder.setName(model.getName());
                        viewHolder.setMembers(model.getMembers());
                        viewHolder.setStatus(model.getStatus());

                        viewHolder.setImage(getActivity(), model.getImage());

                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }



    public static class BlogViewHolder extends RecyclerView.ViewHolder  {
        View mView;
        Context ctx;
        public BlogViewHolder(final View itemView) {
            super(itemView);
            mView= itemView;


            Typeface mytypeface1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview1 = (TextView) mView.findViewById(R.id.groupname);
            mytextview1.setTypeface(mytypeface1);
            Typeface mytypeface2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Light.ttf");
            TextView mytextview2 = (TextView) mView.findViewById(R.id.noofmembers);
            mytextview2.setTypeface(mytypeface2);

            Typeface mytypeface3 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Montserrat-Regular.ttf");
            TextView mytextview3 = (TextView) mView.findViewById(R.id.join);
            mytextview3.setTypeface(mytypeface3);

            mytextview3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {

                    Intent browserIntent = new Intent(v.getContext(), chatfullscreen.class);
                    //Intent browserChooserIntent = Intent.createChooser(browserIntent , "Choose browser of your choice");
                    v.getContext().startActivity(browserIntent);


                }
            });




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



            TextView post_title1 = (TextView)mView.findViewById(R.id.groupname);
            post_title1.setText(name);

        }



        public void setMembers(String members){



            TextView post_title3 = (TextView)mView.findViewById(R.id.noofmembers);
            post_title3.setText(members);

        }

        public void setStatus(String status){



            TextView post_title3 = (TextView)mView.findViewById(R.id.join);
            post_title3.setText(status);

        }



        public void setImage(Context ctx , String image){




            CircleImageView post_image = (CircleImageView) mView.findViewById(R.id.group_pic);
            Picasso.with(ctx).load(image).placeholder(R.drawable.progress_animation).into(post_image);


        }



    }




}