package com.jlmarting.earthquakes.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jlmarting.earthquakes.model.Coordinate;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EarthQuakeDB {

    private final String DATABASE = "DATABASE";

    public static final String[] ALL_COLUMNS = {
            EarthquakesProvider.Columns.ID_KEY,
            EarthquakesProvider.Columns.PLACE_KEY,
            EarthquakesProvider.Columns.MAGNITUDE_KEY,
            EarthquakesProvider.Columns.LATITUDE_KEY,
            EarthquakesProvider.Columns.LONGITUDE_KEY,
            EarthquakesProvider.Columns.DEPTH_KEY,
            EarthquakesProvider.Columns.URL_KEY,
            EarthquakesProvider.Columns.TIME_KEY
    };

    private Context context;

    public EarthQuakeDB(Context context) {
        this.context = context;
    }

    public void insertEarthQuake(EarthQuake earthquake){
        ContentValues insertValues = new ContentValues();
        insertValues.put(EarthquakesProvider.Columns.ID_KEY, earthquake.get_id());
        insertValues.put(EarthquakesProvider.Columns.PLACE_KEY, earthquake.getPlace());
        insertValues.put(EarthquakesProvider.Columns.MAGNITUDE_KEY, earthquake.getMagnitude());
        insertValues.put(EarthquakesProvider.Columns.LATITUDE_KEY, earthquake.getCoords().getLatitude());
        insertValues.put(EarthquakesProvider.Columns.LONGITUDE_KEY, earthquake.getCoords().getLongitude());
        insertValues.put(EarthquakesProvider.Columns.DEPTH_KEY, earthquake.getCoords().getDepth());
        insertValues.put(EarthquakesProvider.Columns.URL_KEY, earthquake.getUrl());
        insertValues.put(EarthquakesProvider.Columns.TIME_KEY, earthquake.getDate().getTime());

        ContentResolver cr = this.context.getContentResolver();

        cr.insert(EarthquakesProvider.CONTENT_URI,insertValues);
        // cr.notify(); mal.. esto ya lo resuelve el provider

    }

    public void insertEarthQuakes(ArrayList<EarthQuake> earthquakes){

        for(int i=0; i<earthquakes.size();i++){
            insertEarthQuake(earthquakes.get(i));
        }
    }

    public List<EarthQuake> getAll(){
        return query(null, null);
    }

    public List<EarthQuake> getAllByMagnitude(double minMagnitude) {

        String where = EarthquakesProvider.Columns.MAGNITUDE_KEY + ">=?";// + minMagnitude;
        String whereArgs[] = {String.valueOf(minMagnitude)};

        return query(where, whereArgs);
    }

    public EarthQuake getById(String id){
        EarthQuake eq = null;

        String where = EarthquakesProvider.Columns.ID_KEY + "=?";
        String whereArgs[] = {id};
        List <EarthQuake> q = query(where, whereArgs);
        if (q.size()>0){
            eq =  q.get(0);
        }
        return eq;
    }


    public List<EarthQuake> query(String where, String[] whereArgs){

        ContentResolver cr = context.getContentResolver();
        List<EarthQuake> earthQuakes = new ArrayList<>();


        String order = EarthquakesProvider.Columns.TIME_KEY + " DESC";
        Cursor cursor = cr.query(EarthquakesProvider.CONTENT_URI, ALL_COLUMNS, where, whereArgs, order);

        HashMap<String,Integer> indexes = new HashMap<>();
        for (int i=0;i < ALL_COLUMNS.length;i++ ){
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }

        ArrayList<EarthQuake> earthquakes = new ArrayList<>();
        while(cursor.moveToNext()){
            EarthQuake eq = new EarthQuake();
            eq.set_id(cursor.getString(indexes.get(EarthquakesProvider.Columns.ID_KEY)));
            eq.setPlace(cursor.getString(indexes.get(EarthquakesProvider.Columns.PLACE_KEY)));
            eq.setMagnitude(cursor.getDouble(indexes.get(EarthquakesProvider.Columns.MAGNITUDE_KEY)));
            eq.setCoords(new Coordinate(cursor.getDouble(indexes.get(EarthquakesProvider.Columns.LATITUDE_KEY)),
                                        cursor.getDouble(indexes.get(EarthquakesProvider.Columns.LONGITUDE_KEY)),
                                        cursor.getDouble(indexes.get(EarthquakesProvider.Columns.DEPTH_KEY))));
            eq.setUrl(cursor.getString(indexes.get(EarthquakesProvider.Columns.URL_KEY)));
            eq.setDate(cursor.getLong(indexes.get(EarthquakesProvider.Columns.TIME_KEY)));

            earthquakes.add(eq);
        }

        cursor.close();
        return earthquakes;
    }

}
