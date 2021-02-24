package com.example.my_movie_app_test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.my_movie_app_test.models.Image;
import com.example.my_movie_app_test.models.Movie;
import com.example.my_movie_app_test.models.Rating;
import com.example.my_movie_app_test.models.Show;

import java.util.ArrayList;
import java.util.List;

public class MovieDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "MovieDB";
    private static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";
    public static String ITEM_TITLE = "itemTitle";
    public static String ITEM_IMAGE = "itemImage";
    public static String TYPE = "type";
    public static String RATING = "rating";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " TEXT," + ITEM_TITLE + " TEXT,"
            + ITEM_IMAGE + " TEXT," + TYPE + " TEXT," + RATING + " TEXT)";

    public MovieDB(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // insert data into database
    public void insertIntoTheDatabase(String item_title, Image item_image, int id, String type, double rating) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, item_title);
        if (item_image.getOriginal() != null) {
            cv.put(ITEM_IMAGE, item_image.getMedium());
        } else if (item_image.getMedium() != null) {
            cv.put(ITEM_IMAGE, item_image.getOriginal());
        }
        cv.put(KEY_ID, id);
        cv.put(TYPE, type);
        cv.put(RATING, rating);
        db.insert(TABLE_NAME, null, cv);
    }

    // remove line from database
    public void removeFromTheDatabase(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    public List<Movie> read_all_data() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null, null);
        try {
            List<Movie> movieList = new ArrayList<>();
            //favourite list
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(MovieDB.ITEM_TITLE));
                int id = cursor.getInt(cursor.getColumnIndex(MovieDB.KEY_ID));
                String image = cursor.getString(cursor.getColumnIndex(MovieDB.ITEM_IMAGE));
                String type = cursor.getString(cursor.getColumnIndex(MovieDB.TYPE));
                double rating = cursor.getDouble(cursor.getColumnIndex(MovieDB.RATING));

                Show show = new Show(type, title, new Image(image, image), new Rating(rating), id);
                Movie movie = new Movie(show);
                movie.setLiked(true);
                movieList.add(movie);
            }
            return movieList;
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }


}
