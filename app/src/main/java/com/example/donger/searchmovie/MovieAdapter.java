package com.example.donger.searchmovie;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private Activity activity;

    public MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieItems> getmData() {
        return mData;
    }

    public void setData(ArrayList<MovieItems> items) {
        this.mData.clear();
        this.mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(MovieItems movieItems) {
        this.mData.add(movieItems);
        notifyItemInserted(mData.size() - 1);
    }


    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_items, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, int position) {
        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .override(250, 250);
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w185"+getmData().get(position).getPoster())
                .apply(myOptions)
                .into(viewHolder.imgMovie);
        viewHolder.tvTitle.setText(getmData().get(position).getTitle());
        viewHolder.tvDescription.setText(getmData().get(position).getDescription());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView tvTitle, tvDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_poster_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);

        }
    }
}
