package com.creation.creative.creativecreation;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout frame1;
    UserDataHelper dataHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Creative Creations");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        dataHelper = new UserDataHelper(Main2Activity.this);
        try {
            // dataHelper.getWritableDatabase();
            dataHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            //dataHelper = new UserDataHelper(LoginSignup.this);

            sqLiteDatabase = dataHelper.getWritableDatabase();
            dataHelper.openDataBase();
            //boolean hasUserFound = false;
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_info", null);

            if (cursor.moveToFirst()) {
                do {
                    String user = cursor.getString(3);
                    if (user != null) {
                        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
                        Menu menu = nav.getMenu();
                        MenuItem item = menu.findItem(R.id.log);
                        item.setTitle("Logout");
                    }
                } while (cursor.moveToNext());
            }

            SharedPreferences sharedPreferences = getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);
            String state = sharedPreferences.getString("state", null);

            if (state == null || state.equals("logout")||state.equals("skip")) {
                NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
                Menu menu = nav.getMenu();
                MenuItem item = menu.findItem(R.id.log);
                item.setTitle("Login");
            }
        } catch (SQLiteException sqle) {
            throw sqle;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
              int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment f = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.jewels)
        {
            f = new ShopNowFragment();
        }
        else if (id == R.id.gifts) {
            f = new Gift();

        }
        else if (id == R.id.cards) {
            f = new Card();
        }
        else if (id == R.id.bill) {
            sharedPreferences = getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);

            String state = sharedPreferences.getString("state", null);

            if (state.equals("login")) {
                f = new BillingFragment();
            }
            else{
                new android.app.AlertDialog.Builder(Main2Activity.this).setMessage("Please Login!!!").setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Main2Activity.this, LoginSignup.class);
                        startActivity(intent);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                }).create().show();
            }
          // f = new BillingFragment();
        }
        else if(id==R.id.contact){
            f=new ContactUs();
        }
        else if (id == R.id.log) {
           // f = new LogFragment();
            sharedPreferences = getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);

            String state = sharedPreferences.getString("state", null);

            if (state.equals("logout") ||state.equals("skip") ){
                Intent intent = new Intent(Main2Activity.this, LoginSignup.class);
                startActivity(intent);
            }
            if (state.equals("login")) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("state", "logout");
                editor.commit();
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_info", null);

                if (cursor.moveToFirst()) {
                    do {

                                String id1 = cursor.getString(0);
                                String pass1 = cursor.getString(1);


                               String login = cursor.getString(3);
                                if (login!=null)
                                {
                                    Log.e("id1",id1);
                                    Log.e("pass1",pass1);
                                    Log.e("login",login);
                                    int a =  cursor.getPosition();
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.putNull("user_login");
                                    sqLiteDatabase.update("user_info", contentValues, "user_id" + " = ?", new String[]{login});
                                    Log.e("login customer", login + "");
                                    sqLiteDatabase.execSQL("DELETE FROM login_user_item_info"); break;
                                }
                    } while (cursor.moveToNext());
                }
               Intent intent = new Intent(Main2Activity.this, LoginSignup.class);
                startActivity(intent);

            }

           // sharedPreferences=getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);

        }
        if (f!=null){
            frame1 = (FrameLayout)findViewById(R.id.frame);
            frame1.setBackgroundColor(Color.WHITE);
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame,f).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        }


    }

    private AlertDialog AskOption() {
        // TODO Auto-generated method stub
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        sqLiteDatabase.execSQL("DELETE FROM login_user_item_info");

                        moveTaskToBack(true);
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }


}

