package com.jlmarting.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jlmarting.earthquakes.MainActivity;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {

    private final SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public EarthQuakeDB(Context context) {
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null,
                EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    public void insert(EarthQuake earthQuake) {

       /*
       No usar este método. Utilizar ContentValues

       String query = "INSERT INTO " + EarthQuakeOpenHelper.DATABASE_TABLE
                + "('_id', 'place', 'magnitude', 'url', 'lat', 'long', 'time') VALUES ('" +
                earthQuake.get_id().toString() + "', '" +
                earthQuake.getPlace().toString() + "', '" +
                earthQuake.getMagnitude() + "', '" +
                //depth??
                earthQuake.getUrl().toString() + "', '" +
                earthQuake.getCoords().getLat() + "', '" +
                earthQuake.getCoords().getLon() + "', '" +
                earthQuake.getDate() + "')";

        Log.d("DB-insert", query.toString());

        this.db.execSQL(query);
        */

        ContentValues values = new ContentValues();
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_ID, earthQuake.get_id());
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_PLACE, earthQuake.getPlace());
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_MAGNITUDE,
                String.valueOf(earthQuake.getMagnitude()));
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_URL, earthQuake.getUrl().toString());
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_LONG, earthQuake.getCoords().getLon());
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_LAT, earthQuake.getCoords().getLat());
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_DEPTH, earthQuake.getCoords().getDepth());
        values.put(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_TIME, earthQuake.getDate().toString());


        try {
            db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, values);
        } catch (SQLiteException e) {
            Log.d("SQLite-Error", "SQLite error");
        }
    }

    public EarthQuake selectById(String id){
        EarthQuake ea = new EarthQuake();
        String selectQuery = "SELECT * FROM "
                + EarthQuakeOpenHelper.DATABASE_TABLE
                +" WHERE _id=?";
        String[] args = {id};
        Cursor cursor = db.rawQuery(selectQuery,args);
        if (cursor.moveToFirst()) {
            ea.set_id(cursor.getString(
                    cursor.getColumnIndex(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_ID)));
            ea.setMagnitude(cursor.getDouble(
                    cursor.getColumnIndex(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_MAGNITUDE)
            ));
            ea.setPlace(cursor.getString(
                    cursor.getColumnIndex(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_PLACE)
            ));
        }
        return ea;
    }

    /*
    En este caso podría ser interesante trabajar con List que es un interfaz.
    De esta forma podemos llamar a este método y usar otros tipos.
     */
    public ArrayList<EarthQuake> selectAll() {

        ArrayList<EarthQuake> earthQuakes = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + EarthQuakeOpenHelper.DATABASE_TABLE;
            Log.d("EADB", query.toString());
            Cursor cursor = this.db.rawQuery(query, null);
            Log.d("EADB",cursor.toString());

             /* Esta forma de crear la query sería más correcta
            Cursor cursor = db.query(){
                EarthQuakeOpenHelper.DATABASE_TABLE,
                ALL_COLUMNS,
                null, //claúsula where
                null,
                null //GROUP BY
            }
            */

            /*
            Calling moveToFirst() does two things: it allows you to test whether
            the query returned an empty set (by testing the return value) and it moves
            the cursor to the first result (when the set is not empty).
            Note that to guard against an empty return set, the code you posted should be
            testing the return value (which it is not doing).
            http://bit.ly/1EV3NVP
             */

           // if (cursor.moveToFirst() ){
              // Log.d("EADB", "Empty database");
             //   return earthQuakes;
           // }

            cursor.moveToFirst();
            while (cursor.moveToNext()){
                EarthQuake earthQuake = new EarthQuake();

                earthQuake
                        .set_id(cursor.
                                getString(cursor
                                        .getColumnIndex(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_ID)));

                earthQuake
                        .setPlace(cursor.
                                getString(cursor
                                        .getColumnIndex(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_PLACE)));

                earthQuake
                        .setMagnitude(cursor
                                .getDouble(cursor
                                        .getColumnIndex(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_MAGNITUDE)));

                earthQuake
                        .setDate(cursor
                                .getInt(cursor
                                        .getColumnIndex(EarthQuakeOpenHelper.EARTHQUAKES_FIELD_TIME)));

                earthQuakes.add(earthQuake);

                Log.d("EADB", "ID:"
                        + earthQuake.get_id().toString()
                        + " - MAG:" + earthQuake.getMagnitude()
                        + " - PLACE:" + earthQuake.getPlace());

            }
        }
        catch (SQLiteException e){
            Log.d("EADBx", e.getMessage());
        }
        catch (android.database.CursorIndexOutOfBoundsException e)        {
            Log.d("EADBx", e.getMessage());
        }
        return earthQuakes;
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE
                + "(_id TEXT PRIMARY_KEY, " +
                "place TEXT, magnitude REAL, " +
                "depth REAL, url TEXT," +
                "lat REAL, long REAL, time INTEGER)";
        private static final int DATABASE_VERSION = 1;
        /*
        '_id', 'place', 'magnitude', 'url', 'lat', 'long', 'time'
         */
        private static final String EARTHQUAKES_FIELD_ID = "_id";
        private static final String EARTHQUAKES_FIELD_PLACE = "place";
        private static final String EARTHQUAKES_FIELD_MAGNITUDE = "magnitude";
        private static final String EARTHQUAKES_FIELD_URL = "url";
        private static final String EARTHQUAKES_FIELD_LAT = "lat";
        private static final String EARTHQUAKES_FIELD_LONG = "long";
        private static final String EARTHQUAKES_FIELD_DEPTH = "depth";
        private static final String EARTHQUAKES_FIELD_TIME = "time";

        private EarthQuakeOpenHelper(Context context, String name,
                                     SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
