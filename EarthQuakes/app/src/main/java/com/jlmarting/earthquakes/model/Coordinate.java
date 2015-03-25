package com.jlmarting.earthquakes.model;

/**
 * Created by cursomovil on 25/03/15.
 */
public class Coordinate {
    private double lat;
    private double lon;
    private double depth;

    public Coordinate(double lat, double lon, double depth) {
        this.lat = lat;
        this.lon = lon;
        this.depth = depth;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }
}
