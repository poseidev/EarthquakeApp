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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthQuake earthQuake;

        // Create a fake list of earthquake locations.
        ArrayList<EarthQuake> earthquakes = new ArrayList<>();

        earthQuake = new EarthQuake();
        earthQuake.setMagnitude(5);
        earthQuake.setLocation("San Francisco");
        earthQuake.setDate(Calendar.getInstance().getTime());
        earthquakes.add(earthQuake);

        earthQuake = new EarthQuake();
        earthQuake.setMagnitude(5);
        earthQuake.setLocation("London");
        earthQuake.setDate(Calendar.getInstance().getTime());
        earthquakes.add(earthQuake);

        earthQuake = new EarthQuake();
        earthQuake.setMagnitude(5);
        earthQuake.setLocation("Tokyo");
        earthQuake.setDate(Calendar.getInstance().getTime());
        earthquakes.add(earthQuake);

        earthQuake = new EarthQuake();
        earthQuake.setMagnitude(5);
        earthQuake.setLocation("Mexico City");
        earthQuake.setDate(Calendar.getInstance().getTime());
        earthquakes.add(earthQuake);

        earthQuake = new EarthQuake();
        earthQuake.setMagnitude(5);
        earthQuake.setLocation("Moscow");
        earthQuake.setDate(Calendar.getInstance().getTime());
        earthquakes.add(earthQuake);

        earthQuake = new EarthQuake();
        earthQuake.setMagnitude(5);
        earthQuake.setLocation("Rio de Janeiro");
        earthQuake.setDate(Calendar.getInstance().getTime());
        earthquakes.add(earthQuake);

        earthQuake = new EarthQuake();
        earthQuake.setMagnitude(5);
        earthQuake.setLocation("Paris");
        earthQuake.setDate(Calendar.getInstance().getTime());
        earthquakes.add(earthQuake);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthQuakeAdapter adapter = new EarthQuakeAdapter(
                this, R.layout.earthquake_activity, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}
