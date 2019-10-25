package com.example.donger.searchmovie;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.donger.searchmovie.db.FavoriteHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.donger.searchmovie.MappingHelper.mapCursorToArrayList;
import static com.example.donger.searchmovie.db.DatabaseContact.FavoriteColumns.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoadDataCallback {
    private RecyclerView rvFavorite;
    private MovieAdapter adapter;

    private ArrayList<MovieItems> list = new ArrayList<>();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private static HandlerThread handlerThread;
    private DataObserver myObserver;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.menu_favorite));

        rvFavorite = view.findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorite.setHasFixedSize(true);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getActivity());
        getActivity().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        adapter = new MovieAdapter(getActivity());

        rvFavorite.setAdapter(adapter);


        if (savedInstanceState == null) {
            new LoadDataAsync(getActivity()  , this).execute();
        } else {
            ArrayList<MovieItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }

        onClick();

        return  view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getmData());
    }

    private void onClick() {
        ItemClickSupport.addTo(rvFavorite).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MovieItems movie = list.get(position);
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                detailIntent.putExtra("EXTRA_MOVIE",movie);
                startActivity(detailIntent);
            }
        });
    }



    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(Cursor movieItems) {
        ArrayList<MovieItems> listMovie = mapCursorToArrayList(movieItems);
        list = listMovie;
        if (listMovie.size() > 0) {
            adapter.setData(listMovie);
        } else {
            Toast.makeText(getContext(),"No Data Found", Toast.LENGTH_SHORT).show();
            adapter.setData(new ArrayList<MovieItems>());

        }


    }



    private static class LoadDataAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadDataCallback> weakCallback;

        private LoadDataAsync(Context context, LoadDataCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {

            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movieItems) {
            super.onPostExecute(movieItems);
            weakCallback.get().postExecute(movieItems);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        new LoadDataAsync(getContext(), this).execute();
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

    }


}
