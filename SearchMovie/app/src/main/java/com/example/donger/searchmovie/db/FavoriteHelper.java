package com.example.donger.searchmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.donger.searchmovie.MovieItems;

import java.util.ArrayList;

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

public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }

}
