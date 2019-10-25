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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    private RecyclerView rvSearch;
    MovieAdapter adapter;
    EditText etTitle;
    ImageButton bSearch;
    ProgressBar pbLoading;
    private ArrayList<MovieItems> list;
    static final String EXTRAS_TITLE = "EXTRAS_TITLE";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.search_movie_title));

        rvSearch = view.findViewById(R.id.rv_search);
        rvSearch.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(MovieData.getListData());
        showRecyclerList();

        etTitle = view.findViewById(R.id.et_title);
        bSearch = view.findViewById(R.id.btn_search);
        pbLoading = view.findViewById(R.id.pb_loading);

        bSearch.setOnClickListener(myListener);

        String title = etTitle.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_TITLE,title);


        getLoaderManager().initLoader(0,bundle,this);
        return view;
    }

    private void showRecyclerList() {
        rvSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieAdapter(getActivity());
        adapter.setData(list);
        rvSearch.setAdapter(adapter);

        ItemClickSupport.addTo(rvSearch).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
        String title = "";
        if(bundle != null)
            title = bundle.getString(EXTRAS_TITLE);
        if (title.isEmpty())
            title = bundle.getString(EXTRAS_TITLE);

        pbLoading.setVisibility(View.VISIBLE);
        if (pbLoading.getVisibility() == View.VISIBLE){
            rvSearch.setVisibility(View.GONE);
        }

        return new MovieLoader(getActivity(), title);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
        list=movieItems;
        pbLoading.setVisibility(View.GONE);
        if (pbLoading.getVisibility() == View.GONE){
            rvSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String title = etTitle.getText().toString();

            if (TextUtils.isEmpty(title))
                return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_TITLE, title);
            getLoaderManager().restartLoader(0, bundle, SearchFragment. this);
        }
    };
}
