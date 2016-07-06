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
public class SampleCard extends AppCompatActivity {
    Button btn ;
    GridView grid;
    ArrayList<Item> list = new ArrayList();
    MyAdapter adapter;
    SQLiteDatabase sqlite;
    String first_table = "item_info_birth_card";
    String second_table = "login_user_item_info";

    String table;

    String third_table = "item_info_ani_card";
    String fourth_table = "item_info_festive_card";
    String fifth_table = "item_info_thanku_card";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jewellary);
        Intent intent= getIntent();
        String neck = intent.getStringExtra("key");
           UserDataHelper myDbHelper;
        myDbHelper = new UserDataHelper(this);
               try {
            myDbHelper.createDataBase();

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
            sqlite = myDbHelper.getWritableDatabase();

            grid = (GridView)findViewById(R.id.gridView);
            if(neck.equals("birth")) {
                table=first_table;
                list = myDbHelper.getAllItems(first_table);
            }
            if(neck.equals("best")) {
                table=third_table;
                list = myDbHelper.getAllItems(third_table);
            }
            if(neck.equals("fest")) {
                table=fourth_table;
                list = myDbHelper.getAllItems(fourth_table);
            }
            if(neck.equals("val")) {
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

                ImageDialog imdia = new ImageDialog(SampleCard.this,position,table,second_table);
                imdia.show();


            }
        });


    }


}
