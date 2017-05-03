package com.social420.social420;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class FeedAdapter extends ArrayAdapter<String> {

    Context context;


    ArrayList<OneFragment> dataset = new ArrayList<>();

    AssetManager assetManager = getContext().getAssets();
    int layoutResourceId;
    Typeface mytypeface;
    Typeface mytypeface1;
    Typeface mytypeface2;
    Typeface mytypeface3;
    Typeface mytypeface4;
    Typeface mytypeface5;
    Typeface mytypeface6;
    Typeface mytypeface7;




    FeedAdapter(Context context, String[] foods) {
        super(context,R.layout.feed_header, foods);
    }



    public void setDataset(ArrayList<OneFragment> dataset){
        this.dataset = dataset ;
    }

    @Override
    public int getItemViewType(int position) {
        int i =0;
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        int type = getItemViewType(position);

        if (convertView == null) {

            switch (type) {

                case 0:

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.feed_header, parent, false);

                    String userName = getItem(position);

                    mytypeface = Typeface.createFromAsset(assetManager, "Montserrat-Regular.ttf");
                    TextView usernametext = (TextView) convertView.findViewById(R.id.enrootuser);
                    usernametext.setTypeface(mytypeface);



                    Typeface mytypeface1 = Typeface.createFromAsset(assetManager, "Montserrat-Light.ttf");
                    TextView mytextview1 = (TextView) convertView.findViewById(R.id.timeago);
                    mytextview1.setTypeface(mytypeface1);

                    Typeface mytypeface2 = Typeface.createFromAsset(assetManager, "Montserrat-Light.ttf");
                    TextView mytextview2 = (TextView) convertView.findViewById(R.id.storename);
                    mytextview2.setTypeface(mytypeface2);

                    Typeface mytypeface3 = Typeface.createFromAsset(assetManager, "Montserrat-Light.ttf");
                    TextView mytextview3 = (TextView) convertView.findViewById(R.id.thc);
                    mytextview3.setTypeface(mytypeface3);

                    Typeface mytypeface4 = Typeface.createFromAsset(assetManager, "Montserrat-Light.ttf");
                    TextView mytextview4 = (TextView) convertView.findViewById(R.id.cbd);
                    mytextview4.setTypeface(mytypeface4);

                    Typeface mytypeface5 = Typeface.createFromAsset(assetManager, "Montserrat-Light.ttf");
                    TextView mytextview5 = (TextView) convertView.findViewById(R.id.cbn);
                    mytextview5.setTypeface(mytypeface5);

                    Typeface mytypeface6 = Typeface.createFromAsset(assetManager, "Montserrat-Light.ttf");
                    TextView mytextview6 = (TextView) convertView.findViewById(R.id.claims);
                    mytextview6.setTypeface(mytypeface6);


                    Typeface mytypeface7 = Typeface.createFromAsset(assetManager, "Montserrat-Regular.ttf");
                    TextView mytextview7 = (TextView) convertView.findViewById(R.id.get_deal);
                    mytextview7.setTypeface(mytypeface7);


                    Typeface mytypeface8 = Typeface.createFromAsset(assetManager, "BreeSerif-Regular.ttf");
                    TextView mytextview8 = (TextView) convertView.findViewById(R.id.quantity);
                    mytextview8.setTypeface(mytypeface8);

                    Typeface mytypeface9 = Typeface.createFromAsset(assetManager, "BreeSerif-Regular.ttf");
                    TextView mytextview9 = (TextView) convertView.findViewById(R.id.price);
                    mytextview9.setTypeface(mytypeface9);




                    break;

                case 1:

                    LayoutInflater inflater1 = LayoutInflater.from(getContext());
                    convertView = inflater1.inflate(R.layout.feed_header, parent, false);

                    String userName1 = getItem(position);




                    break;



                default:

                    break;


            }


        }

        return convertView;
    }
}
