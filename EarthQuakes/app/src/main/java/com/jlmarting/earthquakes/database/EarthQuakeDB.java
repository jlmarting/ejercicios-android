package com.jlmarting.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {

    private  SQLiteDatabase db;
    private EarthQuakeOpenHelper dbhelper;
    private EarthQuakeDB Helper;

    //columnas de la tabla//

    public static final String KEY_ID = "_id";
    public static final String KEY_PLACE = "place";
    public static final String KEY_MAGNITUDE = "magnitude";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LONG = "Long";
    public static final String KEY_URL = "url";
    public static final String KEY_DEPTH = "depth";
    public static final String KEY_TIME = "time";

    public static final String[] KEYS_ALL = {KEY_ID,KEY_PLACE,KEY_MAGNITUDE, KEY_LAT, KEY_LONG, KEY_URL,KEY_DEPTH, KEY_TIME };

    public EarthQuakeDB (Context context){

        this.dbhelper= new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = dbhelper.getWritableDatabase();
    }


    public List <EarthQuake> getAll(){
        return query(null, null);
    }

    public List <EarthQuake> getAllByMagnitude(int magnitude){
        String where = KEY_MAGNITUDE + ">=?";

        String [] whereArgs = {
                String.valueOf(magnitude)
        };
        return query(where, whereArgs);

    }


    public EarthQuake getById(String id){
        EarthQuake eq = null;

        String where = KEY_ID + "=?";
        String whereArgs[] = {id};
        List <EarthQuake> q = query(where, whereArgs);
        if (q.size()>0){
            eq =  q.get(0);
        }
        return eq;

    }

    private List<EarthQuake> query(String where, String[]  whereArgs) {

        List <EarthQuake> earthQuakes = new ArrayList<EarthQuake>();

        Cursor cursor;
        cursor = db.query(
                EarthQuakeOpenHelper.DATABASE_TABLE,
                KEYS_ALL,
                where,
                whereArgs,
                null,
                null,
                KEY_TIME + " DESC"
        );

        HashMap<String, Integer> indexes = new HashMap<>();
        for (int i=0; i< KEYS_ALL.length; i++){
            indexes.put(KEYS_ALL[i],cursor.getColumnIndex(KEYS_ALL[i]));
        }
        while (cursor.moveToNext()){

            EarthQuake earthquake=new EarthQuake();
            earthquake.set_id(cursor.getString(indexes.get(KEY_ID)));
            earthquake.setPlace(cursor.getString(indexes.get(KEY_PLACE)));
            earthquake.setMagnitude(cursor.getDouble(indexes.get(KEY_MAGNITUDE)));
            earthquake.getCoords().setLat(cursor.getDouble(indexes.get(KEY_LAT)));
            earthquake.getCoords().setLng(cursor.getDouble(indexes.get(KEY_LONG)));
            earthquake.getCoords().setDepth(cursor.getDouble(indexes.get(KEY_DEPTH)));
            earthquake.setUrl(cursor.getString(indexes.get(KEY_URL)));
            earthquake.setTime(cursor.getLong(indexes.get(KEY_TIME)));


            earthQuakes.add(earthquake);



        }



        return earthQuakes;

    }


    public void createRow(EarthQuake earthquake) {

        ContentValues newValues = new ContentValues();


        newValues.put(Helper.KEY_ID, earthquake.get_id());
        newValues.put(Helper.KEY_TIME, earthquake.getTime().getTime());
        newValues.put(Helper.KEY_PLACE, earthquake.getPlace());
        newValues.put(Helper.KEY_LONG, earthquake.getCoords().getLng());
        newValues.put(Helper.KEY_LAT, earthquake.getCoords().getLat());
        newValues.put(Helper.KEY_URL, earthquake.getUrl());
        newValues.put(Helper.KEY_MAGNITUDE, earthquake.getMagnitude());
        newValues.put(Helper.KEY_DEPTH, earthquake.getCoords().getDepth());


        try{
            db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, newValues);
        }catch(SQLiteException ex) {

        }

    }




    private class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME ="earthQuakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION  = 1;


        //private  static  final String DATABASE_CREATE= "CREATE TABLE" + DATABASE_TABLE + "_id PRIMARY KEY, place TEXT, magnitude REAL, lat REAL,long REAL, depth REAL, url TEXT, time INTEGER";
        private static final String DATABASE_CREATE = "CREATE Table " + DATABASE_TABLE +
                "(_id  TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL , Long REAL, url TEXT,depth REAL, time INTEGER)";


        public EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
