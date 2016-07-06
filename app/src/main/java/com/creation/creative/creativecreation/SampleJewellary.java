package com.creation.creative.creativecreation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by asus on 3/14/2016.
 */
public class SampleJewellary extends AppCompatActivity {
    Button btn ;
    GridView grid;
    ArrayList<Item> list = new ArrayList();
    MyAdapter adapter;
    SQLiteDatabase sqlite;
     String first_table = "item_info_necklace";
    String second_table = "login_user_item_info";

String table;

    String third_table = "item_info_bracelet";
    String fourth_table = "item_info_ring";
    String fifth_table = "item_info_earings";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jewellary);
        Intent intent= getIntent();
        String neck = intent.getStringExtra("key");
       /* String bras = intent.getStringExtra("key2");
        String earrin = intent.getStringExtra("key1");
*/
//       Log.e("necklace", nacklase);

        //GridView gridView = (GridView) findViewById(R.id.gridView);
        UserDataHelper myDbHelper;
            myDbHelper = new UserDataHelper(this);
        //sqlite = myDbHelper.getWritableDatabase();

            try {
                myDbHelper.createDataBase();

            } catch (IOException ioe) {
                throw new Error("Unable to create database");
            }
            try {
                myDbHelper.openDataBase();
                sqlite = myDbHelper.getWritableDatabase();


            grid = (GridView)findViewById(R.id.gridView);
                if(neck.equals("Necklace")) {
                    table=first_table;
                    list = myDbHelper.getAllItems(first_table);
                }
                if(neck.equals("Bracelats")) {
                    table=third_table;
                    list = myDbHelper.getAllItems(third_table);
                }
                if(neck.equals("ring")) {
                    table=fourth_table;
                    list = myDbHelper.getAllItems(fourth_table);
                }
                if(neck.equals("earing")) {
                    table=fifth_table;
                    list = myDbHelper.getAllItems(fifth_table);
                }

            adapter = new MyAdapter(this,list);
            grid.setAdapter(adapter);
        } catch (SQLiteException sqle) {
            throw sqle;
        }
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ImageDialog imdia = new ImageDialog(SampleJewellary.this,position,table,second_table);
                imdia.show();


            }
        });


    }


}
