package com.example.donger.searchmovie;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    private RecyclerView rvUpComing;
    MovieAdapter adapter;
    ProgressBar pbLoading;
    private ArrayList<MovieItems> list;

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.up_coming_title));

        rvUpComing = view.findViewById(R.id.rv_up_coming);
        rvUpComing.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(MovieData.getListData());
        showRecyclerList();

        pbLoading = view.findViewById(R.id.pb_loading);

        getLoaderManager().initLoader(0,null,this);
        return view;
    }

    private void showRecyclerList() {
        rvUpComing.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieAdapter(getActivity());
        adapter.setData(list);
        rvUpComing.setAdapter(adapter);

        ItemClickSupport.addTo(rvUpComing).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MovieItems movie = list.get(position);
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                detailIntent.putExtra("EXTRA_MOVIE",movie);
                startActivity(detailIntent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int i, @Nullable Bundle bundle) {
        pbLoading.setVisibility(View.VISIBLE);
        if (pbLoading.getVisibility() == View.VISIBLE){
            rvUpComing.setVisibility(View.GONE);
        }
        return new UpComingLoader(getActivity());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
        list=movieItems;
        pbLoading.setVisibility(View.GONE);
        if (pbLoading.getVisibility() == View.GONE){
            rvUpComing.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }
}
