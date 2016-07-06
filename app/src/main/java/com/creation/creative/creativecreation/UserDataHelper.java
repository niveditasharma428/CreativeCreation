package com.creation.creative.creativecreation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by preet on 26-03-2016.
 */
public class UserDataHelper extends SQLiteOpenHelper {

    //private static String DB_PATH = "/data/data/com.creation.creative.creativecreation/databases/";
    private static String DB_PATH ;
    private static String DB_NAME = "Creations.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    UserDataHelper dbh;

    public UserDataHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing
        } else {
            this.getWritableDatabase();
            try {
                copyDataBase(DB_NAME);
               // Toast.makeText(myContext, "hi copied", Toast.LENGTH_LONG).show();

            } catch (IOException e) {
               // Toast.makeText(myContext, "hi catch", Toast.LENGTH_LONG).show();
                throw new Error("Error copying database");

            }
        }
    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        //boolean checkdb = false;
        try {

            //String myPath = myContext.getFilesDir().getAbsolutePath().replace("files","databases")+File.separator + DB_NAME;
            DB_PATH=myContext.getDatabasePath(DB_NAME).getPath();
           // String myPath = DB_PATH + DB_NAME;
            // File dbfile = new File(myPath);
            Log.e("db_path",DB_PATH);
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
            //Toast.makeText(myContext, "hi22i", Toast.LENGTH_SHORT).show();
            // checkdb = dbfile.exists();
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
        //return checkdb;

    }
    private void copyDataBase(String DB_NAME) throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
       // outFileName=myContext.getDatabasePath(DB_NAME).getPath();
        String outFileName/* = DB_PATH + DB_NAME*/;
        outFileName=myContext.getDatabasePath(DB_NAME).getPath();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);

        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLiteException {
        DB_PATH=myContext.getDatabasePath(DB_NAME).getPath();
        ;
        myDataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
       // Toast.makeText(myContext, "hahaah", Toast.LENGTH_LONG).show();

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }

    public ArrayList<Item> getAllItems(String table_name) {
        // String query = "SELECT * FROM " + nameid;
        // DataBaseHelper dbh;
        ArrayList<Item> items = new ArrayList<Item>();
        dbh = new UserDataHelper(myContext);
        myDataBase = dbh.getWritableDatabase();


        Cursor c = myDataBase.rawQuery("SELECT * FROM " +table_name, null);
        if (c.moveToFirst()) {
            do {


                String name1 = c.getString(1);
                byte[] img = c.getBlob(4);
                Bitmap img1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                //Drawable d = new BitmapDrawable(img1);
                Item item = new Item();

                item.setName(name1);
                item.setImage(img1);

                items.add(item);


            } while (c.moveToNext());

        }

        return items;


    }

}

