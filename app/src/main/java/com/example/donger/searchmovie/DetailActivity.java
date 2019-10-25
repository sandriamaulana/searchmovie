package com.example.donger.searchmovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.donger.searchmovie.db.FavoriteHelper;

import java.util.ArrayList;

import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.BACKDROP_PATH;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.CONTENT_URI;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.COUNT;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.DESCRIPTION;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.ID;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.LANGUAGE;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.POPULARITY;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.ONRELEASE;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.POSTER;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.RATING;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity {
    ImageView imgPoster;
    TextView tvTitle, tvDescription, tvRating, tvPopularity, tvLanguage, tvCount, tvRelease;

    public static String EXTRA_MOVIE= "EXTRA_MOVIE";

    private FavoriteHelper favoriteHelper;
    private Boolean isFavorite = false;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.detail_title);

        setContentView(R.layout.activity_detail);

        MovieItems movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        imgPoster = findViewById(R.id.img_poster_detail);
        tvTitle = findViewById(R.id.tv_title_detail);
        tvDescription = findViewById(R.id.tv_description_detail);
        tvRating = findViewById(R.id.tv_rating);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvLanguage = findViewById(R.id.tv_language);
        tvCount = findViewById(R.id.tv_count);
        tvRelease = findViewById(R.id.tv_release);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185"+movie.getPoster())
                .into(imgPoster);
        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getDescription());
        tvRating.setText(movie.getRating());
        tvPopularity.setText(movie.getPopularity());
        tvLanguage.setText(movie.getLanguage());
        tvCount.setText(movie.getCount());
        tvRelease.setText(movie.getRelease());
        CekMovies();

    }

    private void CekMovies(){
        MovieItems movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + movie.getId()),
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()){
            isFavorite=true;
        }
        else{
            isFavorite = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        if (isFavorite)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected));
        else
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        this.menu=menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MovieItems movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (item.getItemId() == R.id.favorite){
            if(isFavorite){

                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                Toast.makeText(this, movie.getTitle()+" UnFavorted", Toast.LENGTH_SHORT).show();
                getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movie.getId()), null, null);
                isFavorite=false;
            }
            else{
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected));
                ContentValues values = new ContentValues();
                values.put(ID, movie.getId());
                values.put(POSTER, movie.getPoster());
                values.put(TITLE, movie.getTitle());
                values.put(DESCRIPTION, movie.getDescription());
                values.put(RATING, movie.getRating());
                values.put(POPULARITY, movie.getPopularity());
                values.put(LANGUAGE, movie.getLanguage());
                values.put(COUNT, movie.getCount());
                values.put(ONRELEASE, movie.getRelease());
                values.put(BACKDROP_PATH, movie.getBackdrop_path());

                isFavorite=true;
                Toast.makeText(this, movie.getTitle()+" Favorited", Toast.LENGTH_SHORT).show();
                getContentResolver().insert(CONTENT_URI, values);



            }
        }
        return super.onOptionsItemSelected(item);
    }
}
