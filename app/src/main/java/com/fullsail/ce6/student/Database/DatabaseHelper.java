package com.fullsail.ce6.student.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fullsail.ce6.student.Articles;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "database.db";
    private static final int VERSION = 1;
    private static final String CREATE_LIST = "CREATE TABLE IF NOT EXISTS " +
            Articles.DATA_TABLE + " (" +
            Articles.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Articles.Title + " TEXT, " +
            Articles.BODY + " TEXT, " +
            Articles.URI_THUMBNAIL + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String name, String image_url,String body)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues values =new ContentValues();

        String myfinalstring = name.replace("'","''");
        values.put(Articles.Title,myfinalstring);

        values.put(Articles.URI_THUMBNAIL,image_url);

        String myfinalstring2 = body.replace("'","''");
        values.put(Articles.BODY, myfinalstring2);

        sqLiteDatabase.insert(Articles.DATA_TABLE,null,values);

    }

    public ArrayList fetchData()

    {

        ArrayList<String>stringArrayList=new ArrayList<String>();

        String fetchdata="select * from emp";

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);

        if(cursor.moveToFirst()){

            do

            {

                stringArrayList.add(cursor.getString(0));

                stringArrayList.add(cursor.getString(1));

                stringArrayList.add(cursor.getString(2));

            } while (cursor.moveToNext());

        }

        return stringArrayList;

    }

}

