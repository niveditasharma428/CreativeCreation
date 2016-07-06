package com.creation.creative.creativecreation;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;


/**
 * Created by preet on 12-03-2016.
 */
public class ShopNowFragment extends Fragment {

    Intent i;
    ImageView necklace,earrings,Braclet,ring;
    SQLiteDatabase sqLiteDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.fragment_jwellery,container,false);
            necklace =(ImageView)rootview.findViewById(R.id.necklace);
            earrings =(ImageView)rootview.findViewById(R.id.earings);
            Braclet = (ImageView)rootview.findViewById(R.id.bracelat);
            ring = (ImageView)rootview.findViewById(R.id.ring);

        necklace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                i = new Intent(getContext(),SampleJewellary.class);
                i.putExtra("key", "Necklace");
                startActivity(i);

            }
        });
        Braclet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getContext(),SampleJewellary.class);
                i.putExtra("key","Bracelats");
                startActivity(i);


            }
        });

        ring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getContext(),SampleJewellary.class);
                i.putExtra("key","ring");
                startActivity(i);


            }
        });

        earrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getContext(), SampleJewellary.class);
                i.putExtra("key", "earing");
                startActivity(i);


            }
        });

        return rootview;
    }


}
