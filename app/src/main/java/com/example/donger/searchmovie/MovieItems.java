package com.example.donger.searchmovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

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
import static com.example.donger.searchmovie.db.DatabaseContact.getColumnInt;
import static com.example.donger.searchmovie.db.DatabaseContact.getColumnString;


public class MovieItems implements Parcelable {
    private int id;
    private String poster;
    private String title;
    private String description;
    private String rating;
    private String popularity;
    private String language;
    private String count;
    private String release;
    private String backdrop_path;



    public MovieItems(JSONObject object){
        try{
            int id = object.getInt("id");
            String poster = object.getString("poster_path");
            String title = object.getString("title");
            String description = object.getString("overview");
            String rating = object.getString("vote_average");
            String popularity = object.getString("popularity");
            String language = object.getString("original_language");
            String count = object.getString("vote_count");
            String release = object.getString("release_date");
            String backdrop_path = object.getString("backdrop_path");

            this.id = id;
            this.poster=poster;
            this.title= title;
            this.description = description;
            this.rating = rating;
            this.popularity = popularity;
            this.language = language;
            this.count = count;
            this.release = release;
            this.backdrop_path = backdrop_path;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public MovieItems() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String Popularity) {
        this.popularity = Popularity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String genres) {
        this.count= genres;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.rating);
        dest.writeString(this.popularity);
        dest.writeString(this.language);
        dest.writeString(this.count);
        dest.writeString(this.release);
        dest.writeString(this.backdrop_path);
    }

    public MovieItems(int id, String poster, String title, String description,
                      String rating, String popularity, String language, String count, String release, String backdrop_path) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.popularity = popularity;
        this.language = language;
        this.count = count;
        this.release = release;
        this.backdrop_path = backdrop_path;
    }

    public MovieItems(Cursor cursor) {
        this.id = getColumnInt(cursor, ID);
        this.poster = getColumnString(cursor, POSTER);
        this.title = getColumnString(cursor, TITLE);
        this.description = getColumnString(cursor, DESCRIPTION);
        this.rating = getColumnString(cursor, RATING);
        this.popularity = getColumnString(cursor, POPULARITY);
        this.language = getColumnString(cursor, LANGUAGE);
        this.count = getColumnString(cursor, COUNT);
        this.release = getColumnString(cursor, ONRELEASE);
        this.backdrop_path = getColumnString(cursor, BACKDROP_PATH);
    }

    protected MovieItems(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.rating = in.readString();
        this.popularity = in.readString();
        this.language = in.readString();
        this.count = in.readString();
        this.release = in.readString();
        this.backdrop_path = in.readString();
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
