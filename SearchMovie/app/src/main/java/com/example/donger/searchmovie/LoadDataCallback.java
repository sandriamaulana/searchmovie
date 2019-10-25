package com.example.donger.searchmovie;

import android.database.Cursor;


interface LoadDataCallback {

    void preExecute();

    void postExecute(Cursor movieItems);
}
