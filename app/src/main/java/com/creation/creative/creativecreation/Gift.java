package com.creation.creative.creativecreation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by preet on 06-04-2016.
 */
public class Gift extends Fragment {

    Intent i;
    ImageView birth,best,fest,val;
    SQLiteDatabase sqLiteDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_gift,container,false);
        birth =(ImageView)rootview.findViewById(R.id.birthday);
        best =(ImageView)rootview.findViewById(R.id.Bestwishes);
        fest = (ImageView)rootview.findViewById(R.id.Festive);
        val = (ImageView)rootview.findViewById(R.id.Valentines);

        birth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                i = new Intent(getContext(),SampleBirthday.class);
                i.putExtra("key", "birth");
                startActivity(i);

            }
        });
        best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getContext(),SampleBirthday.class);
                i.putExtra("key","best");
                startActivity(i);


            }
        });

        fest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getContext(),SampleBirthday.class);
                i.putExtra("key","fest");
                startActivity(i);


            }
        });

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getContext(), SampleBirthday.class);
                i.putExtra("key", "val");
                startActivity(i);


            }
        });

        return rootview;
    }



}
