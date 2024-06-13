package com.example.where_to_watch.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "WhereToWatch.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_NAME = "user_data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MAIL = "mail";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CREATED_AT = "created_at";

    private static final String TABLE_NAME_FAV = "favoris";

    private static final String COLUMN_ID_USER = "_id_user";

    private static final String COLUMN_ID_MOVIE = "_id_movie";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_CREATED_AT + " INTEGER);";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_NAME_FAV +
                " (" + COLUMN_ID_USER + " INTEGER PRIMARY KEY, " +
                COLUMN_ID_MOVIE + " INTEGER); ";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FAV);
        onCreate(db);
    }

    void newFav(int _id_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID_USER, _id_user);
        db.insert(TABLE_NAME_FAV, null, cv);
    }

    public int newUser(String mail, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MAIL, mail);
        cv.put(COLUMN_PASSWORD, password);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        String currentDateAndTime = sdf.format(new Date());
        cv.put(COLUMN_CREATED_AT, currentDateAndTime);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1)
        {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Succeful", Toast.LENGTH_SHORT).show();

        }
        return (int) result;
    }

    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        int userId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_ID);
            if (columnIndex != -1) {
                userId = cursor.getInt(columnIndex);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return userId;
    }
}

