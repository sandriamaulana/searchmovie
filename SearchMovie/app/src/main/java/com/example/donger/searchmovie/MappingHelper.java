package com.example.donger.searchmovie;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.BACKDROP_PATH;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.COUNT;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.DESCRIPTION;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.ID;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.LANGUAGE;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.ONRELEASE;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.POPULARITY;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.POSTER;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.RATING;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.TITLE;

public class MappingHelper {

    public static ArrayList<MovieItems> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<MovieItems> list = new ArrayList<>();
        while (movieCursor.moveToNext()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(ID));
            String poster = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTER));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE));
            String description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DESCRIPTION));
            String rating = movieCursor.getString(movieCursor.getColumnIndexOrThrow(RATING));
            String popularity = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POPULARITY));
            String language = movieCursor.getString(movieCursor.getColumnIndexOrThrow(LANGUAGE));
            String count = movieCursor.getString(movieCursor.getColumnIndexOrThrow(COUNT));
            String release = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ONRELEASE));
            String backdrop_path = movieCursor.getString(movieCursor.getColumnIndexOrThrow(BACKDROP_PATH));
            list.add(new MovieItems(id, poster, title, description, rating, popularity, language, count, release, backdrop_path));
        }
        return list;
    }
}
