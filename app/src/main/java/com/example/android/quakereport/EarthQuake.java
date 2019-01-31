package com.example.android.quakereport;

import java.util.Date;

public class EarthQuake {

    private double mMagnitude = 0;
    private String mLocation = null;
    private Date mDate = null;

    public EarthQuake()
    {

    }

    public void setMagnitude(double magnitude)
    {
        mMagnitude = magnitude;
    }

    public void setLocation(String location)
    {
        mLocation = location;
    }

    public void setDate(Date date)
    {
        mDate = date;
    }

    public double getMagnitude()
    {
        return mMagnitude;
    }

    public String getLocation()
    {
        return mLocation;
    }

    public Date getDate()
    {
        return mDate;
    }
}
