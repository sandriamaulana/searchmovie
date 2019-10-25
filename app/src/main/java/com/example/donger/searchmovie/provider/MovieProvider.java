package com.example.donger.searchmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.donger.searchmovie.MainActivity;
import com.example.donger.searchmovie.db.FavoriteHelper;

import static com.example.donger.searchmovie.db.DatabaseContact.AUTHORITY;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.CONTENT_URI;
import static com.example.donger.searchmovie.db.DatabaseContact.TABLE_FAVORITE;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteHelper favoriteHelper;

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, MOVIE);

        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE + "/#", MOVIE_ID);
    }
    @Override
    public boolean onCreate() {
        favoriteHelper = favoriteHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        favoriteHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        favoriteHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = favoriteHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        favoriteHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        favoriteHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = favoriteHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

       return updated;
    }
}
