package helper;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "btp";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AGE = "age";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_WORKERTRADE = "workertrade";
    private static final String KEY_SMOKINGHABIT = "smokinghabit";
    private static final String KEY_ALCOHOLHABIT = "alcoholhabit";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_USERNAME + " TEXT UNIQUE," + KEY_AGE + " INTEGER," + KEY_HEIGHT + " INTEGER," +KEY_WEIGHT + " INTEGER," +KEY_WORKERTRADE + " INTEGER," +KEY_SMOKINGHABIT + " INTEGER," +KEY_ALCOHOLHABIT+ " INTEGER," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String username, String age, String height, String weight, String workertrade, String smokinghabit, String alcoholhabit, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_USERNAME, username); // Username
        values.put(KEY_AGE, age); // Age
        values.put(KEY_HEIGHT, height); // Height
        values.put(KEY_WEIGHT, weight); // Weight
        values.put(KEY_WORKERTRADE, workertrade); // Workertrade
        values.put(KEY_SMOKINGHABIT, smokinghabit); // Smokinghabit
        values.put(KEY_ALCOHOLHABIT, alcoholhabit); // Alcoholhabit
        values.put(KEY_UID, uid); // Username
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("username", cursor.getString(2));
            user.put("age", cursor.getString(3));
            user.put("height", cursor.getString(4));
            user.put("weight", cursor.getString(5));
            user.put("workertrade", cursor.getString(6));
            user.put("smokinghabit", cursor.getString(7));
            user.put("alcoholhabit", cursor.getString(8));
            user.put("uid", cursor.getString(9));
            user.put("created_at", cursor.getString(10));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}