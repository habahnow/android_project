package com.example.danny.android_project;

/**
 * Created by danny on 6/11/15.
 */

/**
 * Created by ashleyvo on 6/9/15.
 */
public class CurrentTemp {
    private double cTemp;
    private double minTemp;
    private double maxTemp;

    public double getcTemp() {
        return cTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setcTemp(double cTemp) {
        this.cTemp = cTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }
}
