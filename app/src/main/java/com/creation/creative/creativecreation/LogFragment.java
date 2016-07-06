package com.creation.creative.creativecreation;


import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by preet on 03-04-2016.
 */
public class LogFragment extends Fragment {

UserDataHelper dataHelper;
    SQLiteDatabase sqLiteDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        String login_user;
        dataHelper = new UserDataHelper(getContext());
        try {
            // dataHelper.getReadableDatabase();
            dataHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {


            sqLiteDatabase = dataHelper.getWritableDatabase();
            dataHelper.openDataBase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_info", null);

            if (cursor.moveToFirst()) {
                do {
                    login_user = cursor.getString(3);

                    if(login_user != null) {
                        if (login_user != null) {

                        }
                    }

                } while (cursor.moveToNext());

                    if(login_user==null)
                    {
                       // Toast.makeText(getContext(),"login nhi h",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LoginSignup.class);
                        startActivity(intent);


                    }





            }
        }catch (SQLiteException sqle) {
            throw sqle;

        }


        super.onCreate(savedInstanceState);
    }


}