package com.jlmarting.earthquakes.model;

/**
 * Created by javi-vaquero on 25/03/15.
 */
public class Coordinate {
    private double lat;
    private double lng;
    private double depth;

    public Coordinate(double lat, double lng, double depth) {
        this.lat = lat;
        this.lng = lng;
        this.depth = depth;
    }

    public double getLatitude() {
        return lat;
    }

    public void setLatitude(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return lng;
    }

    public void setLongitude(double lng) {
        this.lng = lng;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return  "lat=" + lat +
                ", lng=" + lng +
                ", depth=" + depth;
    }
}
