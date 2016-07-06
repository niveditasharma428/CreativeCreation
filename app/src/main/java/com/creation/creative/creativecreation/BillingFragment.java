package com.creation.creative.creativecreation;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by preet on 01-04-2016.
 */
public class BillingFragment extends Fragment {
    SQLiteDatabase sqlite;

    TextView resT,customer_name ;
    String login;

    GridView grid;

    ArrayList<Item> list = new ArrayList();
    ItemAdapter adapter;

    TextView pro;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_billing, container, false);
        grid = (GridView)rootView.findViewById(R.id.gridV);

        resT = (TextView)rootView.findViewById(R.id.result);
        customer_name = (TextView)rootView.findViewById(R.id.customer_name);
        pro = (TextView)rootView.findViewById(R.id.proceed);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlite.execSQL("DELETE FROM login_user_item_info");
                Dialog dialog=new Dialog((getContext()));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.fragment_contact_us);
                dialog.show();
            }
            });

                UserDataHelper myDbHelper;
                myDbHelper = new UserDataHelper(getContext());
                try {
                    myDbHelper.createDataBase();

                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    myDbHelper.openDataBase();
                    sqlite = myDbHelper.getWritableDatabase();
                    Cursor cursor = sqlite.rawQuery("SELECT * FROM user_info", null);
                    if (cursor.moveToFirst()) {
                        do {
                            login = cursor.getString(3);
                            if (login != null) {
                                int a = cursor.getPosition();
                                getDetails(a);
                                // login="nsdjd";
                                break;
                            }

                        } while (cursor.moveToNext());

                    }
                    if (login == null) {
                        Intent i = new Intent(getContext(), LoginSignup.class);
                        startActivity(i);

                    }

                } catch (SQLiteException sqle) {
                    throw sqle;
                }
                return rootView;
            }
            public void getDetails(int b)            {
                //Toast.makeText(getContext(),"getDetails = "+b,Toast.LENGTH_SHORT).show();
                Cursor cur = sqlite.rawQuery("SELECT * FROM user_info",null);
                if(cur.moveToFirst())
                {
                    do {
                        if(cur.getPosition()==b)
                        {
                            String login = cur.getString(3);
                            String id = cur.getString(0);
                            customer_name.setText(login);
                            method();
                        }

                    }while (cur.moveToNext());
                }


            }

    public void method()
    {
        int res = 0;
        Cursor cur = sqlite.rawQuery("SELECT * FROM login_user_item_info",null);
        if(cur.moveToFirst())
        {
            do {
                Log.e("hello strng","i m here");
                int realquantity = cur.getInt(8);
                String table_name = cur.getString(7);
                    String item_idd = cur.getString(0);
                String item_name = cur.getString(1);
                int item_price = cur.getInt(3);
                int item_quan = cur.getInt(4);
               // item_image = cur.getBlob(2);
                byte[] img = cur.getBlob(2);

                Bitmap img1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                adapter = new ItemAdapter(getContext(),list);
                grid.setAdapter(adapter);


                Item item = new Item();

                item.setName(item_name);

                item.setPrice(item_price);
                item.setQuantity(item_quan);
                item.setImage(img1);
                list.add(item);
                int result = item_price*item_quan;
                res = res+result;
               int final_quantity;
                final_quantity=realquantity-item_quan;
                Log.e("table_name",table_name);
                Cursor cursor1 = sqlite.rawQuery("SELECT * FROM " + table_name, null);
                if (cursor1.moveToFirst()) {
                    do {

                        String itemid = cursor1.getString(0);
                        if (itemid.equals(item_idd)) {
                            int quantz = cursor1.getInt(3);

                                ContentValues content = new ContentValues();
                                content.put("item_quan", final_quantity);
                                sqlite.update(table_name, content, "item_id" + " =?", new String[]{itemid});
                              //  Toast.makeText(getContext(), "1 item removed", Toast.LENGTH_LONG).show();
                              //  String count = Integer.toString(quantz);
                               // value.setText(count);
                        }
                    } while (cursor1.moveToNext());
                }
            }while (cur.moveToNext());
            String re = Integer.toString(res);
            resT.setText(re);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(getContext(), Main2Activity.class);
        startActivity(intent);
    }
}




