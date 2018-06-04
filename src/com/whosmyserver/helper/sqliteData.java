package com.whosmyserver.helper;

import java.util.LinkedList;
import java.util.List;

import com.whosmyserver.model.userData;
 
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class sqliteData extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "USERDB";
 
    public sqliteData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_USER_TABLE = "CREATE TABLE user ( " +
                "id1 INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "id TEXT, "+
                "username TEXT, "+
                "password TEXT, "+
                "name TEXT, "+
                "email TEXT, "+
                "imagePath TEXT, "+
                "status TEXT"+ " )";
 
        // create books table
        db.execSQL(CREATE_USER_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");
 
        // create fresh user table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------
 
 
    // User table name
    private static final String TABLE_USER = "user";
 
    // User Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGEPATH = "imagePath";
    private static final String KEY_STATUS = "status";

 
    private static final String[] COLUMNS = {KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_NAME,KEY_STATUS, KEY_EMAIL,KEY_IMAGEPATH};
 
    public void addUserData(userData userdata){
        Log.d("addUser", userdata.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        
        //username, password,name,school,
		//others,likes,groups,favorites,status,userpriv;
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, userdata.getId()); // get Id
        values.put(KEY_USERNAME, userdata.getUsername()); // get username
        values.put(KEY_PASSWORD, userdata.getPassword()); // get password
        values.put(KEY_NAME, userdata.getName()); // get name;
        values.put(KEY_EMAIL, userdata.getEmail()); // get email
        values.put(KEY_IMAGEPATH, userdata.getImagePath()); // get image url
        values.put(KEY_STATUS, userdata.getStatus()); // get status
 
        // 3. insert
        db.insert(TABLE_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
 
    public userData getUserData(int id){
 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_USER, // a. table
                COLUMNS, // b. column names
                " id1 = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build userinfo object
        userData userinfo = new userData();
        userinfo.setId(cursor.getString(1));
        userinfo.setUsername(cursor.getString(2));
        userinfo.setPassword(cursor.getString(3));
        userinfo.setName(cursor.getString(4));
        userinfo.setEmail(cursor.getString(5));
        userinfo.setImagePath(cursor.getString(6));
        userinfo.setStatus(cursor.getString(7));
        
        Log.d("getUser("+id+")", userinfo.toString());
 
        // 5. return userinfo
        cursor.close();
        db.close();
        return userinfo;
    }
 
    // Get All Books
    public List<userData> getAllUserData() {
        List<userData> userinfos = new LinkedList<userData>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USER;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build user and add it to list
        userData userinfo = null;
        if (cursor.moveToFirst()) {
            do {
                userinfo = new userData();
                userinfo.setId(cursor.getString(1));
                userinfo.setUsername(cursor.getString(2));
                userinfo.setPassword(cursor.getString(3));
                userinfo.setName(cursor.getString(4));
                userinfo.setEmail(cursor.getString(5));
                userinfo.setImagePath(cursor.getString(6));
                userinfo.setStatus(cursor.getString(7));
                // Add userinfo to userInfos
                userinfos.add(userinfo);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAlluserinfos()", userinfos.toString());
 
        // return books
        cursor.close();
        db.close();
        return userinfos;
    }
 
     // Updating single userdata
    public int updateUserData(userData userData1) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, userData1.getId()); // get Id
        values.put(KEY_USERNAME, userData1.getUsername()); // get username
        values.put(KEY_PASSWORD, userData1.getPassword()); // get password
        values.put(KEY_NAME, userData1.getName()); // get name
        values.put(KEY_EMAIL, userData1.getEmail()); // get email
        values.put(KEY_IMAGEPATH, userData1.getImagePath()); // get imagepath
        values.put(KEY_STATUS, userData1.getStatus()); // get status
 
        // 3. updating row
        int i = db.update(TABLE_USER, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(userData1.getId()) }); //selection args
 
        // 4. close
        db.close();
 
        return i;
 
    }
 
    // Deleting single user
    public void deleteUserData(userData userData) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_USER,
                KEY_ID+" = ?",
                new String[] { String.valueOf(userData.getId()) });
 
        // 3. close
        db.close();
 
        Log.d("deleteBook", userData.toString());
 
    }
}