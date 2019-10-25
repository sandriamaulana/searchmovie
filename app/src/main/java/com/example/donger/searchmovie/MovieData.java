package com.example.donger.searchmovie;

import java.util.ArrayList;

public class MovieData {
    static MovieItems movieItems = new MovieItems();
    public static String[][] data = new String[][]{
            {movieItems.getPoster(), movieItems.getTitle(), movieItems.getDescription(), movieItems.getRating(), movieItems.getPopularity(),
                    movieItems.getLanguage(), movieItems.getCount(), movieItems.getRelease()}
    };

    public static ArrayList<MovieItems> getListData() {
        MovieItems movieItemsList;
        ArrayList<MovieItems> list = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            movieItemsList = new MovieItems();
            movieItemsList.setPoster(data[i][0]);
            movieItemsList.setTitle(data[i][1]);
            movieItemsList.setDescription(data[i][2]);
            movieItemsList.setRating(data[i][3]);
            movieItemsList.setPopularity(data[i][4]);
            movieItemsList.setLanguage(data[i][5]);
            movieItemsList.setCount(data[i][6]);
            movieItemsList.setRelease(data[i][7]);

            list.add(movieItemsList);
        }
        return list;
    }

}
