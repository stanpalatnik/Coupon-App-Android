package com.mobilecoupon.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/5/12
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */

class SQLiteWrapper extends SQLiteOpenHelper {

    public static final String MYDATABASE_NAME = "Coupon";
    public static final int MYDATABASE_VERSION = 1;

    private static SQLiteWrapper mInstance;
    public static SQLiteWrapper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SQLiteWrapper(context.getApplicationContext());
        }
        return mInstance;
    }
    private SQLiteWrapper(Context context) {
        super(context, MYDATABASE_NAME, null,MYDATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE scannedBarcodes(barcode INTEGER NOT NULL, coupon INTEGER NOT NULL)");
        database.execSQL("CREATE TABLE couponImages(barcode TEXT NOT NULL, couponList TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS scannedBarcodes");
        database.execSQL("DROP TABLE IF EXISTS couponImages");
        onCreate(database);
    }
}