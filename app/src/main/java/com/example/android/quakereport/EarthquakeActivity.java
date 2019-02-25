/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private TextView mEmptyStateTextview;

    private final String USGS_REQUEST_URL =
            //"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
            "https://earthquake.usgs.gov/fdsnws/event/1/query";

    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null &&
                            networkInfo.isConnected();

        mEmptyStateTextview = findViewById(R.id.noEarthquakeTextView);

        if(isConnected) {
            updateEarthquakeList();

            LoaderManager.getInstance(this).initLoader(EARTHQUAKE_LOADER_ID, null, this).forceLoad();
        }
        else {
            ProgressBar progressBar = findViewById(R.id.loadingSpinner);
            progressBar.setVisibility(View.GONE);

            mEmptyStateTextview.setText("No internet connection.");
        }

        Log.i(LOG_TAG, "initLoader");
    }

    private void updateEarthquakeList()
    {
        // Find a reference to the {@link ListView} in the layout
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);
        earthquakeListView.setEmptyView(mEmptyStateTextview);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(
                this, R.layout.earthquake_activity, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake earthquake = mAdapter.getItem(position);

                openEarthquakeURL(earthquake.getURL());
            }
        });
    }

    private void openEarthquakeURL(String url)
    {
        // Convert the String URL into a URI object (to pass into the Intent constructor)
        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settingsMinMagnitudeKey),
                getString(R.string.settingsMinMagnitudeDefault));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settingsOrderByKey),
                getString(R.string.settingsOrderByDefault)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        Log.i(LOG_TAG, "onCreateLoader");

        /*return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);*/
        return new EarthquakeLoader(EarthquakeActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        mAdapter.addAll(earthquakes);
        Log.i(LOG_TAG, "onLoadFinished");

        mEmptyStateTextview.setText(getString(R.string.textNoEarthquake));

        ProgressBar progressBar = findViewById(R.id.loadingSpinner);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

        Log.i(LOG_TAG, "onLoaderReset");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionSettings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
