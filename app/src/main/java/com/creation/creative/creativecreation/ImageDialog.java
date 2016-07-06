package com.creation.creative.creativecreation;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;




public class ImageDialog extends Dialog {

    private final Context context;
    SQLiteDatabase sqlite;
    Bitmap[] array;
    String idd;
     String name;
    byte[] imgg;
    int b;
    int result = 0;
    String table_1, table_2;
    TextView value;
    private SharedPreferences sharedPreferences;
int quantity,real_quantity=1;
int quant;
    private int price;

    public ImageDialog(Context context, int a, String table_a, String table_b) {
        super(context);
        this.context = context;
        b = a;
        table_1 = table_a;
        table_2 = table_b;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_dialog);

        setCancelable(false);
        TextView btn=(TextView)findViewById(R.id.dismiss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //Toast.makeText(getContext(),"b=",Toast.LENGTH_SHORT).show();

        UserDataHelper myDbHelper;
        myDbHelper = new UserDataHelper(getContext());


        try {
            myDbHelper.createDataBase();

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {

            myDbHelper.openDataBase();
            // sqlite = myDbHelper.getReadableDatabase();
            sqlite = myDbHelper.getWritableDatabase();
            final Cursor cursor11 = sqlite.rawQuery("SELECT * FROM "+table_1, null);

            if (cursor11.moveToFirst()) {
                //  Toast.makeText(getContext(),"id2",Toast.LENGTH_LONG).show();
                do {
                    //Toast.makeText(getContext(), "id3", Toast.LENGTH_LONG).show();
                    if (b == cursor11.getPosition()) {
                        // Toast.makeText(getContext(), "id4", Toast.LENGTH_LONG).show();


                        //  Toast.makeText(getContext(), "id = id", Toast.LENGTH_LONG).show();
                        imgg = cursor11.getBlob(4);
                        byte[] imgg1 = cursor11.getBlob(5);
                        byte[] imgg2 = cursor11.getBlob(6);

                        final Bitmap bitmap = BitmapFactory.decodeByteArray(imgg, 0, imgg.length);
                        final Bitmap bitmap1 = BitmapFactory.decodeByteArray(imgg1, 0, imgg1.length);
                        final Bitmap bitmap2 = BitmapFactory.decodeByteArray(imgg2, 0, imgg2.length);

                        ImageView imageView = (ImageView) findViewById(R.id.img_1);
                        ImageView imageView1 = (ImageView) findViewById(R.id.img_2);
                        ImageView imageView2 = (ImageView) findViewById(R.id.img_3);

                        imageView.setImageBitmap(bitmap);
                        imageView1.setImageBitmap(bitmap1);
                        imageView2.setImageBitmap(bitmap2);


                        TextView tname = (TextView) findViewById(R.id.name);
                        TextView tprice = (TextView) findViewById(R.id.price);

                         name = cursor11.getString(1);
                        price = cursor11.getInt(2);
                        String st = Integer.toString(price);
                        tname.setText(name);
                        tprice.setText(st);

                        idd = cursor11.getString(0);
                          quantity = cursor11.getInt(3);
                        String str = Integer.toString(quantity);


                        final ImageView imageView3 = (ImageView) findViewById(R.id.image);
                        imageView3.setImageBitmap(bitmap);


                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imageView3.setImageBitmap(bitmap);

                            }
                        });

                        imageView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imageView3.setImageBitmap(bitmap1);

                            }
                        });

                        imageView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imageView3.setImageBitmap(bitmap2);

                            }
                        });

                              TextView buy = (TextView) findViewById(R.id.buy_now);
                        value = (TextView) findViewById(R.id.value_add);


                        buy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Are you sureyou want to billing now!")
                                        .setCancelable(false)
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                sharedPreferences = context.getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);

                                                String state = sharedPreferences.getString("state", null);
                                                if (state.equals("login")) {

                                                    Intent intent = new Intent(context, BillingFragmen.class);
                                                    context.startActivity(intent);
                                                }
                                                else{
                                                    new android.app.AlertDialog.Builder(context).setMessage("Please Login!!!").setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(context, LoginSignup.class);
                                                           context. startActivity(intent);

                                                        }
                                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();

                                                        }
                                                    }).create().show();
                                                }
                            }
                                        
                            }).setNegativeButton("NO",  new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
dismiss();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });


                    }

                } while (cursor11.moveToNext());

            }

        } catch (SQLiteException sqle) {
            throw sqle;
        }
        TextView add_value = (TextView) findViewById(R.id.add);
        TextView remove_value = (TextView) findViewById(R.id.remove);

        add_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  addValue(quantity, idd, name, price, imgg);
                addValue(quantity,real_quantity,idd, name, price, imgg);
                real_quantity++;
            }
        });

        remove_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeValue(idd);
            }
        });

       // Dialog d = new Dialog(getContext());

    }


    public void addValue(int quan,int real_quant ,String id, String name, int price, byte[] img) {

        Cursor curs = sqlite.rawQuery("SELECT * FROM "+table_2, null);
int quantity=quan-real_quant;
        String qt = Integer.toString(real_quant);

        value.setText(qt);
     if (real_quant == 1 && real_quant<=quan) {


            ContentValues content = new ContentValues();
            content.put("item_id", idd);
            content.put("table_item_id", idd);
            content.put("quantity", quan);
            content.put("item_name", name);
            content.put("item_price", price);
            content.put("item_image", imgg);
            content.put("item_quan", real_quant);
            content.put("item_result", price);
            content.put("table_name", table_1);
            sqlite.insert(table_2, null, content);
            Log.e("hello1", 1 + "");
            Log.e("content", content + "");

       // }
        }

         else if (curs.moveToFirst() && curs != null) {
            do {
              //  Log.e("hello2",2+"");
                String itemid = curs.getString(0);
               int quant = curs.getInt(4);
                if (itemid.equals(idd) && quant != 0) {
                    if (quant < quan) {

                      //  Toast.makeText(getContext(), "before=" + quant, Toast.LENGTH_SHORT).show();
                        quant = quant + 1;
                      //  Toast.makeText(getContext(), "later=" + quant, Toast.LENGTH_SHORT).show();
                      //  result = price * quant;
                        String a = null;
                        ContentValues content = new ContentValues();
                        content.put("item_quan", quant);
                        content.put("item_result", result);
                        sqlite.update(table_2, content, "item_id" + " =?", new String[]{itemid});
                        Log.e("hello2", 2 + "");
                        String count = Integer.toString(quant);
                        value.setText(count);
                    } else if (quant == quan) {
                        Log.e("hello3",3+"");
                        new android.app.AlertDialog.Builder(context).setCancelable(false).setMessage("Item out of stock!!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                ;
                            }
                        }).create().show();
                    }

                }

            } while (curs.moveToNext());
        }
        }



    public void removeValue(String id) {
        Cursor cursor1 = sqlite.rawQuery("SELECT * FROM "+table_2, null);

        if (cursor1.moveToFirst()) {
            do {

                String itemid = cursor1.getString(0);
                if (itemid.equals(id)) {
                    int quantz = cursor1.getInt(4);
                    if (quantz >= 1) {
                        quantz = quantz - 1;
                        ContentValues content = new ContentValues();
                        content.put("item_quan", quantz);
                        sqlite.update(table_2, content, "item_id" + " =?", new String[]{itemid});
                        //Toast.makeText(getContext(), "1 item removed", Toast.LENGTH_LONG).show();
                        String count = Integer.toString(quantz);
                        value.setText(count);
                    }
                    if (quantz == 0) {
                        sqlite.delete(table_2, "item_quan =?", new String[]{Integer.toString(quantz)});
                       // Toast.makeText(getContext(), "item removed", Toast.LENGTH_LONG).show();
                        String count = Integer.toString(quantz);
                        value.setText(count);
                    }
                }
            } while (cursor1.moveToNext());
        }
    }


}




