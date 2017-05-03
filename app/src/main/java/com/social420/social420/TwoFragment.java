package com.social420.social420;

/**
 * Created by pradeeppj on 11/02/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


public class TwoFragment extends Fragment{


    private View rootview1;
    private Context context1;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview1 = (View) inflater.inflate(R.layout.fragment_two, container, false);

        String[] foods = {"Kushagra", "Pradeep", "Gautham","Prakash","Sunil","DD","Deepak", "Tamil"};

        ListAdapter myadapter1 = new FeedAdapter1(getContext(),foods);
        ListView mylistview1 = (ListView) rootview1.findViewById(R.id.feed_list1);
        mylistview1.setAdapter(myadapter1);


        FloatingActionButton fab = (FloatingActionButton) rootview1.findViewById(R.id.mapview);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), mapactivity.class);
                startActivity(in);
            }
        });







        return rootview1;
    }

}