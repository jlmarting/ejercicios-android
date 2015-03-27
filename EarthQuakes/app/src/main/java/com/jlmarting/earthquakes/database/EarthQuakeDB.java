package com.jlmarting.earthquakes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {

    private final SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public EarthQuakeDB(Context context){
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null,EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String  DATABASE_NAME ="earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;

        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE
                                        + "(_id TEXT PRIMARY_KEY, " +
                                        "place TEXT, magnitude REAL, " +
                                        "depth REAL, url TEXT," +
                                        "lat REAL, long REAL, time INTEGER)";

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
