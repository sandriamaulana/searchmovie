package com.example.donger.searchmovie.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.donger.searchmovie.MovieItems;
import com.example.donger.searchmovie.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.CONTENT_URI;

public class FavoriteFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    private Cursor cursor;

    FavoriteFactory(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieItems items = getItems(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_items);
        Bitmap posterWidget;
        try {
            posterWidget = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w780"+items.getBackdrop_path())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageWidget, posterWidget);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageWidget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private MovieItems getItems(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("error");
        }

        return new MovieItems(cursor);
    }
}
