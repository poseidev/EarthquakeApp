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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthquakeAsyncTask asyncTask = new EarthquakeAsyncTask();
        asyncTask.execute();
    }

    private void updateEarthquakeList(List<Earthquake> earthquakes)
    {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthQuakeAdapter adapter = new EarthQuakeAdapter(
                this, R.layout.earthquake_activity, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Earthquake earthquake = adapter.getItem(position);
                openEarthquakeURL(earthquake.getURL());
            }
        });
    }

    private void openEarthquakeURL(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse(url));

        startActivity(intent);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
        @Override
        protected List<Earthquake> doInBackground(String... string) {
            URL url = QueryUtils.createURL(USGS_REQUEST_URL);

            String jsonResponse = "";

            try
            {
                jsonResponse = QueryUtils.makeHttpRequest(url);
            }
            catch (IOException e)
            {
                Log.e("doInBackground", e.getMessage());
            }

            List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(jsonResponse);

            return earthquakes;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            if(earthquakes == null) {
                return;
            }

            updateEarthquakeList(earthquakes);
        }
    }
}
