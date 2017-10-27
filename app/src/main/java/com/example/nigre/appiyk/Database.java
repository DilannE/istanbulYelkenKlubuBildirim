package com.example.nigre.appiyk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nigre on 09.04.2017.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="database";
    public static final int DATABASE_VERSİON=1;
    public static final String DATABASE_TABLE="homepage";
    public static final String ROW_LINK="link";
    public static final String ROW_TITLE="title";


    public Database(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSİON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DATABASE_TABLE + "("
                + Database.ROW_LINK + " TEXT, "
                + Database.ROW_TITLE + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
      db.execSQL("DROP TABLE IF EXİSTS "+ DATABASE_TABLE);
        onCreate(db);
    }



    public void veriEkle(String title){

        SQLiteDatabase db = getWritableDatabase();//bu vt yazılabilir hale getir.
        ContentValues cv = new ContentValues();
        cv.put(ROW_TITLE,title.trim());
        db.insert(DATABASE_TABLE,null,cv);
        db.close();

    }



}
