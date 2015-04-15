package com.jlmarting.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jlmarting.earthquakes.MainActivity;
import com.jlmarting.earthquakes.model.Coordinate;
import com.jlmarting.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by javi-vaquero on 27/03/15.
 */
public class EarthQuakeDB {

    private final String DATABASE = "DATABASE";

    public static final String ID_KEY = "_id";
    public static final String PLACE_KEY = "place";
    public static final String MAGNITUDE_KEY = "magnitude";
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String DEPTH_KEY = "depth";
    public static final String URL_KEY = "url";
    public static final String TIME_KEY = "time";

    public static final String[] ALL_COLUMNS = {
                             ID_KEY,
                             PLACE_KEY,
                             MAGNITUDE_KEY,
                             LATITUDE_KEY,
                             LONGITUDE_KEY,
                             DEPTH_KEY,
                             URL_KEY,
                             TIME_KEY
    };

    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    public EarthQuakeDB(Context context) {
        helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        db = helper.getWritableDatabase();
    }

    public void insertEarthQuake(EarthQuake earthquake){

        ContentValues insertValues = new ContentValues();
        insertValues.put(ID_KEY, earthquake.get_id());
        insertValues.put(PLACE_KEY, earthquake.getPlace());
        insertValues.put(MAGNITUDE_KEY, earthquake.getMagnitude());
        insertValues.put(LATITUDE_KEY, earthquake.getCoords().getLatitude());
        insertValues.put(LONGITUDE_KEY, earthquake.getCoords().getLongitude());
        insertValues.put(DEPTH_KEY, earthquake.getCoords().getDepth());
        insertValues.put(URL_KEY, earthquake.getUrl());
        insertValues.put(TIME_KEY, earthquake.getDate().getTime());

        try{
            db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, insertValues);
        }catch(Exception ex){
            Log.d(DATABASE, "Error inserting earthquake " + earthquake.get_id() + ": " + ex.getMessage());
        }



    }
/*
    public void insertEarthQuakes(ArrayList<EarthQuake> earthquakes){

        for(int i=0; i<earthquakes.size();i++){
            insertEarthQuake(earthquakes.get(i));
        }
    }
*/

    public List<EarthQuake> getAll(){
        return query(null, null);
    }

    public List<EarthQuake> getAllByMagnitude(double minMagnitude) {
        String where = MAGNITUDE_KEY + ">=?";// + minMagnitude;
        String whereArgs[] = {String.valueOf(minMagnitude)};

        return query(where, whereArgs);

    }

    public EarthQuake getById(String id){
        EarthQuake eq = null;

        String where = ID_KEY + "=?";
        String whereArgs[] = {id};
        List <EarthQuake> q = query(where, whereArgs);
        if (q.size()>0){
            eq =  q.get(0);
        }
        return eq;

    }

    public List<EarthQuake> query(String where, String[] whereArgs){

        String order = TIME_KEY + " DESC";
        Cursor cursor = db.query(EarthQuakeOpenHelper.DATABASE_TABLE, ALL_COLUMNS, where, whereArgs, null, null, order);

        HashMap<String,Integer> indexes = new HashMap<>();
        for (int i=0;i < ALL_COLUMNS.length;i++ ){
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }

        ArrayList<EarthQuake> earthquakes = new ArrayList<>();
        while(cursor.moveToNext()){
            EarthQuake eq = new EarthQuake();
            eq.set_id(cursor.getString(indexes.get(ID_KEY)));
            eq.setPlace(cursor.getString(indexes.get(PLACE_KEY)));
            eq.setMagnitude(cursor.getDouble(indexes.get(MAGNITUDE_KEY)));
            eq.setCoords(new Coordinate(cursor.getDouble(indexes.get(LATITUDE_KEY)),
                                        cursor.getDouble(indexes.get(LONGITUDE_KEY)),
                                        cursor.getDouble(indexes.get(DEPTH_KEY))));
            eq.setUrl(cursor.getString(indexes.get(URL_KEY)));
            eq.setDate(cursor.getLong(indexes.get(TIME_KEY)));

            earthquakes.add(eq);
        }

        cursor.close();
        return earthquakes;
    }
/*
    public ArrayList<EarthQuake> getEarthQuakesFilteredByMagnitude (double minMagnitude){

        String where = MAGNITUDE_KEY + ">=?";// + minMagnitude;
        String whereArgs[] = {String.valueOf(minMagnitude)};
        String groupBy = null;
        String having = null;
        String order = TIME_KEY + " DESC";

        Cursor cursor = db.query(EarthQuakeOpenHelper.DATABASE_TABLE, ALL_COLUMNS, where, whereArgs, groupBy, having, order);

        int ID_INDEX = cursor.getColumnIndexOrThrow(ID_KEY);
        int PLACE_INDEX = cursor.getColumnIndexOrThrow(PLACE_KEY);
        int MAGNITUDE_INDEX = cursor.getColumnIndexOrThrow(MAGNITUDE_KEY);
        int LATITUDE_INDEX = cursor.getColumnIndexOrThrow(LATITUDE_KEY);
        int LONGITUDE_INDEX = cursor.getColumnIndexOrThrow(LONGITUDE_KEY);
        int DEPTH_INDEX = cursor.getColumnIndexOrThrow(DEPTH_KEY);
        int URL_INDEX = cursor.getColumnIndexOrThrow(URL_KEY);
        int TIME_INDEX = cursor.getColumnIndexOrThrow(TIME_KEY);

        ArrayList<EarthQuake> earthquakes = new ArrayList<>();


        while(cursor.moveToNext()){
            String eID = cursor.getString(ID_INDEX);
            String ePlace = cursor.getString(PLACE_INDEX);
            Double eMagnitude = cursor.getDouble(MAGNITUDE_INDEX);
            Double eLatitude = cursor.getDouble(LATITUDE_INDEX);
            Double eLongitude = cursor.getDouble(LONGITUDE_INDEX);
            Double eDepth = cursor.getDouble(DEPTH_INDEX);
            String eUrl = cursor.getString(URL_INDEX);
            long eTime = cursor.getLong(TIME_INDEX);

            EarthQuake eq = new EarthQuake();
            eq.set_id(eID);
            eq.setPlace(ePlace);
            eq.setMagnitude(eMagnitude);
            eq.setCoords(new Coordinate(eLatitude, eLongitude, eDepth));
            eq.setUrl(eUrl);
            eq.setDate(eTime);

            earthquakes.add(eq);
        }

        cursor.close();
        return earthquakes;
    }
*/
    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper{

        private	static final String	DATABASE_NAME = "earthquakes.db";
        private	static final String	DATABASE_TABLE = "EARTHQUAKES";
        private	static final int DATABASE_VERSION = 1;



        //	SQL	Statement	to	create	a	new	database.
        private	static final String	DATABASE_CREATE	= "CREATE TABLE " + DATABASE_TABLE +
                                        "(" + ID_KEY + " TEXT PRIMARY KEY, " +
                                            PLACE_KEY + " TEXT, " +
                                            MAGNITUDE_KEY + " REAL, " +
                                            LATITUDE_KEY + " REAL, " +
                                            LONGITUDE_KEY + " REAL, " +
                                            DEPTH_KEY + " REAL, " +
                                            URL_KEY + " TEXT, " +
                                            TIME_KEY + " INTEGER);";

        private EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }
}
