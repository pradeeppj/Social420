package com.social420.social420;

/**
 * Created by pradeeppj on 11/02/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


public class chatFragment extends Fragment{

    private View rootview1;
    private Context context1;

    public chatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview1 = (View) inflater.inflate(R.layout.chat_fragment, container, false);

        String[] foods = {"Kushagra", "Pradeep", "Gautham","Prakash","Sunil","DD","Deepak", "Tamil"};

        ListAdapter myadapter1 = new chatadapter(getContext(),foods);
        ListView mylistview1 = (ListView) rootview1.findViewById(R.id.feed_list_chat);
        mylistview1.setAdapter(myadapter1);










        return rootview1;
    }

}