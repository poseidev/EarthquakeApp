package com.example.android.quakereport;

public class Earthquake {

    private double mMagnitude = 0;
    private String mLocation = null;
    private long mTime = 0;

    public Earthquake()
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

    public void setTime(Long time)
    {
        mTime = time;
    }

    public double getMagnitude()
    {
        return mMagnitude;
    }

    public String getLocation()
    {
        return mLocation;
    }

    public Long getTime()
    {
        return mTime;
    }
}
