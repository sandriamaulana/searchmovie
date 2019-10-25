package com.example.donger.searchmovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.BACKDROP_PATH;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.COUNT;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.DESCRIPTION;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.ID;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.POPULARITY;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.LANGUAGE;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.ONRELEASE;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.POSTER;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.RATING;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.TITLE;
import static com.example.donger.searchmovie.db.DatabaseContact.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbfavorite";

    private static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_FAVORITE = "create table "+TABLE_FAVORITE+
            " ("+ID+" integer primary key not null, " +
            TITLE+" text not null, " +
            POSTER+" text not null, " +
            DESCRIPTION+" text not null, " +
            RATING+" text not null, " +
            POPULARITY+" text not null, " +
            LANGUAGE+" text not null, " +
            COUNT+" text not null, "    +
            ONRELEASE+" text not null, "    +
            BACKDROP_PATH+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITE);
        onCreate(db);
    }
}
