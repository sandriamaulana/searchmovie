package com.example.donger.searchmovie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NowPlayingLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private ArrayList<MovieItems> mData;
    private boolean mResult;

    public NowPlayingLoader(Context context){
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading(){
        if (takeContentChanged())
            forceLoad();
        else if (mResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data){
        mData = data;
        mResult = true;
        super.deliverResult(data);
    }

    @Override
    public void onReset(){
        super.onReset();
        onStopLoading();
        if (mResult){
            onReleaseResource(mData);
            mData = null;
            mResult = false;
        }
    }

    private static final String API_KEY = "a61b992f22c37cd1bb7a24928a04bdb1";


    @Nullable
    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<MovieItems> movieItems2 = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart(){
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItems2.add(movieItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItems2;
    }
    protected void onReleaseResource(ArrayList<MovieItems> data){

    }
}
