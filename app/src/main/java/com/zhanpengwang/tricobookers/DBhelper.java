package com.zhanpengwang.tricobookers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
    private static String TABEL_COLUMN_SEMESTER = "semester";
    private static String TABLE_COLUMN_DEPARTMENT = "department";
    private static String TABLE_COLUMN_COURSE = "course_number";
    private static String TABLE_COLUMN_USED_PRICE = "used_price";
    private static String TABLE_COLUMN_NEW_PRICE = "new_price";
    private static String TABLE_COLUMN_IMG_URL = "img_url";
    private static String TABLE_COLUMN_BOOK_COURSE = "book_course";
    private static final int DICTIONARY_DATABASE_QUERY_ERROR = -2;
    private static String DATABASE_PATH;

    private static String DATABASE_SELLERS_TABLE_NAME = "sellers";
    private static String SELLERS_TABLE_COLUMN_USERNAME = "username";
    private static String SELLERS_TABLE_COLUMN_ISBN = "isbn";
    private static String SELLERS_TABLE_COLUMN_CONTACTS = "phone_number";
    private static String SELLERS_TABLE_COLUMN_PRICE = "price";

    private SQLiteDatabase mdb;

    public DBhelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, 1);
        DATABASE_PATH = mContext.getApplicationInfo().dataDir + "/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database upon the first installation of the tricobookers app
        String queryString = "CREATE TABLE " + DATABASE_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_COLUMN_AUTHORS + " TEXT, " + TABLE_COLUMN_BOOK_NAME + " TEXT, "
                + TABLE_COLUMN_DEPARTMENT + " TEXT, " + TABLE_COLUMN_ISBN + " TEXT, "
                + TABLE_COLUMN_IS_REQUIRED + " TEXT, " + TABEL_COLUMN_SEMESTER + " TEXT, "
                + TABLE_COLUMN_COURSE + " TEXT, " + TABLE_COLUMN_NEW_PRICE + " TEXT, "
                + TABLE_COLUMN_USED_PRICE + " TEXT, " + TABLE_COLUMN_IMG_URL + " TEXT, "
                + TABLE_COLUMN_BOOK_COURSE + " TEXT, UNIQUE(book_course) ON CONFLICT REPLACE)";
        db.execSQL(queryString);

        // Create another database for sellers
        String queryStringSellers = "CREATE TABLE " + DATABASE_SELLERS_TABLE_NAME + " (id INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, " + SELLERS_TABLE_COLUMN_USERNAME + " TEXT, " + SELLERS_TABLE_COLUMN_ISBN + " TEXT, "
                + SELLERS_TABLE_COLUMN_CONTACTS + " TEXT, " + SELLERS_TABLE_COLUMN_PRICE + " TEXT)";
        db.execSQL(queryStringSellers);
    }

    public long getCount(String databaseName) {
        mdb = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(mdb, databaseName);
        return count;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // update the database if needed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
    }

    private ContentValues wrapper(String bookName, String authors, String department, String isbn,
                                  String courseNumber, String isRequired, String usedPrice,
                                  String newPrice, String semester, String imgURL) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_BOOK_NAME, bookName);
        contentValues.put(TABLE_COLUMN_ISBN, isbn);
        contentValues.put(TABLE_COLUMN_AUTHORS, authors);
        contentValues.put(TABLE_COLUMN_IS_REQUIRED, isRequired);
        contentValues.put(TABLE_COLUMN_DEPARTMENT, department);
        contentValues.put(TABLE_COLUMN_COURSE, courseNumber);
        contentValues.put(TABLE_COLUMN_USED_PRICE, usedPrice);
        contentValues.put(TABLE_COLUMN_NEW_PRICE, newPrice);
        contentValues.put(TABLE_COLUMN_IMG_URL, imgURL);
        contentValues.put(TABEL_COLUMN_SEMESTER, semester);
        String bookCourse = bookName + " " + courseNumber;
        contentValues.put(TABLE_COLUMN_BOOK_COURSE, bookCourse);
        return contentValues;
    }

    public void insertNewSellers(String userName, String isbn, String price, String contact) {
        mdb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SELLERS_TABLE_COLUMN_USERNAME, userName);
        contentValues.put(SELLERS_TABLE_COLUMN_ISBN, isbn);
        contentValues.put(SELLERS_TABLE_COLUMN_PRICE, price);
        contentValues.put(SELLERS_TABLE_COLUMN_CONTACTS, contact);
        mdb.insertWithOnConflict(DATABASE_SELLERS_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insertNewRecord(String bookName, String authors, String department, String isbn,
                                String courseNumber, String isRequired, String usedPrice,
                                String newPrice, String semester, String imgURL) {
        mdb = this.getWritableDatabase();
        ContentValues contentValues = wrapper(bookName, authors, department, isbn, courseNumber,
                isRequired, usedPrice, newPrice, semester, imgURL);
        mdb.insertWithOnConflict(DATABASE_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private Cursor getData(int id) {
        mdb = this.getReadableDatabase();
        return mdb.rawQuery("SELECT * FROM "+DATABASE_TABLE_NAME+" WHERE id ="+id+"", null);
    }

    public ArrayList<Sellers> getAListOfSellers(String isbn) {
        ArrayList<Sellers> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + DATABASE_SELLERS_TABLE_NAME + " WHERE " + SELLERS_TABLE_COLUMN_ISBN +
                "=" + isbn;
        mdb = this.getReadableDatabase();
        Cursor cursor = mdb.rawQuery(queryString, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String username = cursor.getString(cursor.getColumnIndex(SELLERS_TABLE_COLUMN_USERNAME));
            String price = cursor.getString(cursor.getColumnIndex(SELLERS_TABLE_COLUMN_PRICE));
            String contact = cursor.getString(cursor.getColumnIndex(SELLERS_TABLE_COLUMN_CONTACTS));
            Sellers sellers = new Sellers(username, price, contact);
            list.add(sellers);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
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

    public String getInformationByBookCouse(String query, String queryRequest) {
        ArrayList<Integer> id = getIdsForQueryMatchingString(query, TABLE_COLUMN_BOOK_COURSE, false);
        if (id.size() > 0) {
            return getInformationFromOID(id.get(0), queryRequest).toString();
        }
        return "";
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

    public static String getTableColumnBookCourse() {
        return TABLE_COLUMN_BOOK_COURSE;
    }

    public static String getTableColumnImgUrl() {
        return TABLE_COLUMN_IMG_URL;
    }

    public static String getDatabaseTableName() {
        return DATABASE_TABLE_NAME;
    }
}
