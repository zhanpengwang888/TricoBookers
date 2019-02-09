package com.zhanpengwang.tricobookers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "exchange_books.db";
    private static String DATABASE_TABLE_NAME = "bookers";
    private static String TABLE_COLUMN_ID = "id";
    private static String TABLE_COLUMN_ISBN = "ISBN";
    private static String TABLE_COLUMN_AUTHORS = "authors";
    private static String TABLE_COLUMN_BOOK_NAME = "book_name";
    private static String TABLE_COLUMN_IS_REQUIRED = "is_required";
    private static String TABLE_COLUMN_STATUS = "status";
    private static String TABLE_COLUMN_DEPARTMENT = "department";
    private static String TABLE_COLUMN_COURSE = "course_number";
    private static String TABLE_COLUMN_USED_PRICE = "used_price";
    private static String TABLE_COLUMN_NEW_PRICE = "new_price";
    private static String TABLE_COLUMN_IMG_URL = "img_url";
    private static final int DICTIONARY_DATABASE_QUERY_ERROR = -2;
    private static String DATABASE_PATH;
    private SQLiteDatabase mdb;

    public DBhelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, 1);
        DATABASE_PATH = mContext.getApplicationInfo().dataDir + "/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database upon the first installation of the tricobookers app
        String queryString = "CREATE TABLE " + DATABASE_TABLE_NAME + " (id INTEGER PRIMARY KEY, "
                + TABLE_COLUMN_AUTHORS + " TEXT, " + TABLE_COLUMN_BOOK_NAME + " TEXT, "
                + TABLE_COLUMN_DEPARTMENT + " TEXT, " + TABLE_COLUMN_ISBN + " TEXT, "
                + TABLE_COLUMN_IS_REQUIRED + " TEXT, " + TABLE_COLUMN_STATUS + " TEXT, "
                + TABLE_COLUMN_COURSE + " TEXT, " + TABLE_COLUMN_NEW_PRICE + "TEXT, "
                + TABLE_COLUMN_USED_PRICE + " TEXT)";
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // update the database if needed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
    }

    private ContentValues wrapper(String bookName, String isbn, String authors, String isRequired,
                                 String status, String department, String courseNumber, String usedPrice,
                                 String newPrice, String imgURL) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_BOOK_NAME, bookName);
        contentValues.put(TABLE_COLUMN_ISBN, isbn);
        contentValues.put(TABLE_COLUMN_AUTHORS, authors);
        contentValues.put(TABLE_COLUMN_IS_REQUIRED, isRequired);
        contentValues.put(TABLE_COLUMN_STATUS, status);
        contentValues.put(TABLE_COLUMN_DEPARTMENT, department);
        contentValues.put(TABLE_COLUMN_COURSE, courseNumber);
        contentValues.put(TABLE_COLUMN_USED_PRICE, usedPrice);
        contentValues.put(TABLE_COLUMN_NEW_PRICE, newPrice);
        contentValues.put(TABLE_COLUMN_IMG_URL, imgURL);
        return contentValues;
    }

    public void insertNewRecord(String bookName, String isbn, String authors, String isRequired,
                                String status, String department, String courseNumber, String usedPrice,
                                String newPrice, String imgURL) {
        mdb = this.getWritableDatabase();
        ContentValues contentValues = wrapper(bookName, isbn, authors, isRequired, status, department,
                courseNumber, usedPrice, newPrice, imgURL);
        mdb.insertWithOnConflict(DATABASE_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private Cursor getData(int id) {
        mdb = this.getReadableDatabase();
        return mdb.rawQuery("SELECT * FROM "+DATABASE_TABLE_NAME+" WHERE id ="+id+"", null);
    }

    public StringBuilder getInformationFromOID(int id, String queryRequest) {
        Cursor cur = getData(id);
        cur.moveToFirst();
        StringBuilder sb = new StringBuilder();
        String tmp = cur.getString(cur.getColumnIndex(queryRequest));
        if (tmp != null) {
            sb.append(tmp);
        }
        cur.close();
        return sb;
    }

    public ArrayList<Integer> getIdsForQueryMatchingString(String queryText, String option, boolean isDefault) {
        ArrayList<Integer> ids = new ArrayList<>();
        if (queryText.contains("'")) {
            queryText = queryText.replaceAll("'", "''");
        }
        String queryString;
        if (isDefault) {
            // if it is default option, we search the keyword by all options
            queryString = "SELECT * FROM " + DATABASE_TABLE_NAME + " WHERE " +
                    TABLE_COLUMN_COURSE + " LIKE " + "\'%" + queryText + "%\'" + " OR " + TABLE_COLUMN_ISBN
                    + " LIKE " + "\'%" + queryText + "%\'" + " OR " + TABLE_COLUMN_DEPARTMENT + " LIKE "
                    + "\'%" + queryText + "%\'" + " OR " + TABLE_COLUMN_AUTHORS + " LIKE "
                    + "\'%" + queryText + "%\'" + " OR " + TABLE_COLUMN_BOOK_NAME + " LIKE "
                    + "\'%" + queryText + "%\'";
        } else {
            // else, search according to the search option selected by user.
            queryString = "SELECT * FROM " + DATABASE_TABLE_NAME + " WHERE " +
                    option + " LIKE " + "\'%" + queryText + "%\'";
        }
        mdb = this.getReadableDatabase();
        Cursor cur = mdb.rawQuery(queryString, null);
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            ids.add(Integer.parseInt(cur.getString(cur.getColumnIndex(TABLE_COLUMN_ID))));
            cur.moveToNext();
        }
        cur.close();
        return ids;
    }

    public static String getTableColumnIsbn() {
        return TABLE_COLUMN_ISBN;
    }

    public static String getTableColumnAuthors() {
        return TABLE_COLUMN_AUTHORS;
    }

    public static String getTableColumnBookName() {
        return TABLE_COLUMN_BOOK_NAME;
    }

    public static String getTableColumnDepartment() {
        return TABLE_COLUMN_DEPARTMENT;
    }

    public static String getTableColumnCourse() {
        return TABLE_COLUMN_COURSE;
    }
}
