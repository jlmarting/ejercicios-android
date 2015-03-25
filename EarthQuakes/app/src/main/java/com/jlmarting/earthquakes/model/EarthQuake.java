package com.jlmarting.earthquakes.model;

import java.util.Date;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuake {
    private String _id;  //android usa _ internamente
    private Coordinate coords;
    private Date date;
    private double magnitude;
    private String place;

    public EarthQuake(String _id, Coordinate coords, Date date, double magnitude, String place) {
        this._id = _id;
        this.coords = coords;
        this.date = date;
        this.magnitude = magnitude;
        this.place = place;
    }

    @Override
    public String toString() {
        return this.getPlace();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
