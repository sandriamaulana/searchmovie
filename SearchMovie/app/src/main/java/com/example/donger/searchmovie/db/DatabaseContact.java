package com.example.donger.searchmovie.db;


import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContact {

    public static final String AUTHORITY = "com.example.donger.searchmovie";
    private static final String SCHEME = "content";

   public static String TABLE_FAVORITE = "table_favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
        public static String POSTER = "poster";
        public static String DESCRIPTION = "description";
        public static String RATING = "rating";
        public static String POPULARITY = "popularity";
        public static String LANGUAGE = "language";
        public static String COUNT = "count";
        public static String ONRELEASE = "onrelease";
        public static String BACKDROP_PATH = "backdrop_path";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
