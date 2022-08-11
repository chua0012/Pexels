package com.cst2335.pexels;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PexelsOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CocktailDB";
    public static final int version = 1;
    public static final String TABLE_NAME = "CocktailInfo";
    public static final String PIC_NAME = "PicName";
    public static final String WIDTH = "Width";
    public static final String HEIGHT = "Height";
    public static final String AUTHOR_NAME = "Author";

    public PexelsOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PIC_NAME + " TEXT,"
                + WIDTH + " TEXT,"
                + HEIGHT + "TEXT,"
                + AUTHOR_NAME+ " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}
