package com.whosmyserver.helper;

import java.util.LinkedList;
import java.util.List;

import com.whosmyserver.model.recentData;
import com.whosmyserver.model.recentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class sqliteRecentData extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "RECENTDB";

	public sqliteRecentData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table
		String CREATE_RECENT_TABLE = "CREATE TABLE recent ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "type TEXT, "
				+ "res_id TEXT, " + "name TEXT, " + "address TEXT, "
				 + "imagePath TEXT, " + "date TEXT" + " )";

		// create books table
		db.execSQL(CREATE_RECENT_TABLE);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed
		db.execSQL("DROP TABLE IF EXISTS books");

		// create fresh user table
		this.onCreate(db);
	}

	// ---------------------------------------------------------------------

	// User table name
	private static final String TABLE_RECENT = "recent";
	
	// User Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE = "type";
	private static final String KEY_RESID = "res_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_IMAGEPATH = "imagePath";
	private static final String KEY_DATE = "date";

	private static final String[] COLUMNS = { KEY_ID, KEY_TYPE,
			KEY_RESID, KEY_NAME, KEY_ADDRESS, KEY_IMAGEPATH, KEY_DATE };

	public void addData(recentData recentdata) {
		Log.d("addUser", recentdata.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		//values.put(KEY_ID, userdata.getId()); // get Id
		values.put(KEY_TYPE, recentdata.getType()); // get username
		values.put(KEY_RESID, recentdata.getRestaurantId()); // get password
		values.put(KEY_NAME, recentdata.getName()); // get name;
		values.put(KEY_ADDRESS, recentdata.getAddress()); // get email
		values.put(KEY_IMAGEPATH, recentdata.getImagePath()); // get image url
		values.put(KEY_DATE, recentdata.getDate()); // get status

		// 3. insert
		db.insert(TABLE_RECENT, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
							// values

		// 4. close
		db.close();
	}

	public recentData getData(int id) {

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
		Cursor cursor = db.query(TABLE_RECENT, // a. table
				COLUMNS, // b. column names
				" id = ?", // c. selections
				new String[] { String.valueOf(id) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();
		
		// 4. build info object
		recentData recentinfo = new recentData();
		recentinfo.setId(cursor.getString(0));
		recentinfo.setType(cursor.getString(1));
		recentinfo.setRestaurantId(cursor.getString(2));
		recentinfo.setName(cursor.getString(3));
		recentinfo.setAddress(cursor.getString(4));
		recentinfo.setImagePath(cursor.getString(5));
		recentinfo.setDate(cursor.getString(6));

		Log.d("getRecent(" + id + ")", recentinfo.toString());

		// 5. return recentinfo
		cursor.close();
		db.close();
		return recentinfo;
	}

	
	// Get All info
	public List<recentData> getAllData() {
		List<recentData> recentinfos = new LinkedList<recentData>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_RECENT;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build user and add it to list
		recentData recentinfo = null;
		if (cursor.moveToFirst()) {
			do {
				recentinfo = new recentData();
				recentinfo.setId(cursor.getString(0));
				recentinfo.setType(cursor.getString(1));
				recentinfo.setRestaurantId(cursor.getString(2));
				recentinfo.setName(cursor.getString(3));
				recentinfo.setAddress(cursor.getString(4));
				recentinfo.setImagePath(cursor.getString(5));
				recentinfo.setDate(cursor.getString(6));
				// Add recentinfo to recentinfos
				recentinfos.add(recentinfo);
			} while (cursor.moveToNext());
		}

		Log.d("getAllrecentinfos()", recentinfos.toString());

		// return books
		cursor.close();
		db.close();
		return recentinfos;
	}

	// Updating single recentData
	public int updateData(recentData recentData1) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// String id, String type, String res_id, String name,
					// String address, String imagePath, String date

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, recentData1.getId()); // get Id
		values.put(KEY_RESID, recentData1.getRestaurantId()); // get username
		values.put(KEY_NAME, recentData1.getName()); // get name
		values.put(KEY_ADDRESS, recentData1.getAddress()); // get email
		values.put(KEY_IMAGEPATH, recentData1.getImagePath()); // get imagepath
		values.put(KEY_DATE, recentData1.getDate()); // get status

		// 3. updating row
		int i = db.update(TABLE_RECENT, // table
				values, // column/value
				KEY_ID + " = ?", // selections
				new String[] { String.valueOf(recentData1.getId()) }); // selection
																		// args

		// 4. close
		db.close();

		return i;

	}

	// Deleting single data
	public void deleteData(recentData recentdata) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. delete
		db.delete(TABLE_RECENT, KEY_ID + " = ?",
				new String[] { String.valueOf(recentdata.getId()) });

		// 3. close
		db.close();

		Log.d("deleteBook", recentdata.toString());

	}
}