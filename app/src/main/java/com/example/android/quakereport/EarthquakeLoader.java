package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);

        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();

        Log.i("EarthquakeLoader", "onStartLoading");
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        Log.i("EarthquakeLoader", "loadInBackground");

        List<Earthquake> list = QueryUtils.fetchEarthquakeData(mUrl);
        return list;
    }
}
