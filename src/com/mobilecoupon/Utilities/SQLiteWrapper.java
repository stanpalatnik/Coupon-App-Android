package com.mobilecoupon.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.mobilecoupon.Models.BarcodeItem;
import com.mobilecoupon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/5/12
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */

public class SQLiteWrapper extends SQLiteOpenHelper {

    public static final String MYDATABASE_NAME = "Coupon";
    public static final int MYDATABASE_VERSION = 2;

    //database tables
    private static final String BARCODE_DATABASE = "scannedBarcodes";
    private static final String COMBINED_COUPON_DATADASE ="currentCoupons";

    //database rows
    private static final String BARCODE_DESCRIPTION = "description";
    private static final String BARCODE_SCANNED_CODE = "barcode";
    private static final String BARCODE_COUPON_CODE = "coupon";
    private static final String BARCODE_TIMESTAMP = "timestamp";

    private static SQLiteWrapper mInstance;
    public static SQLiteWrapper getInstance(Context context) {
        if (mInstance == null) {
            Log.v("Instance", "Getting SQLWrapper context: " + context.toString());
            mInstance = new SQLiteWrapper(context.getApplicationContext());
        }
        return mInstance;
    }
    private SQLiteWrapper(Context context) {
        super(context, MYDATABASE_NAME, null,MYDATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        try
        {
            database.execSQL("CREATE TABLE " + BARCODE_DATABASE +"(barcode INTEGER NOT NULL, coupon INTEGER NOT NULL, " +
                    "description TEXT DEFAULT NULL, timestamp DATE DEFAULT (datetime('now','localtime')))");
            database.execSQL("CREATE TABLE " + COMBINED_COUPON_DATADASE + "(coupon TEXT NOT NULL)");
        }
        catch(Exception e)
        {
            Log.v("SQL_ERROR", "Error creating database: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + BARCODE_DATABASE);
        database.execSQL("DROP TABLE IF EXISTS " + COMBINED_COUPON_DATADASE);
        onCreate(database);
    }

    public boolean insertBarcode(SQLiteDatabase database, String barcode, String coupon, String description) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ").append(BARCODE_DATABASE).append("(barcode,coupon,description) VALUES (")
                .append(barcode).append(", ").append(coupon).append(", ").append('"').append(description).append('"').append(");");
        database.execSQL(stringBuilder.toString());
        return true;
    }

    public boolean insertCouponCode(SQLiteDatabase database, String couponCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ").append(COMBINED_COUPON_DATADASE).append("(coupon) VALUES (")
                .append(couponCode).append(");");
        database.execSQL(stringBuilder.toString());
        return true;
    }

    public BarcodeItem[] getRecentlySearchedProducts(SQLiteDatabase database, int days) {
        String[] columns = new String[]{BARCODE_SCANNED_CODE, BARCODE_COUPON_CODE, BARCODE_DESCRIPTION};
        String WHERE = "(julianday(Date('now')) - julianday(timestamp)) < ?";
        String[] args = {""+days};

        Cursor mCursor = database.query(BARCODE_DATABASE,columns, WHERE,args,null,null,BARCODE_TIMESTAMP);
        mCursor.moveToFirst();
        List<BarcodeItem> items = new ArrayList<BarcodeItem>();
        int numCount = mCursor.getCount();
        for(int i = 0; i < numCount; i++)
        {
            BarcodeItem tmpItem = new BarcodeItem(mCursor.getString(0), mCursor.getString(2), mCursor.getString(1), R.drawable.ic_barcode);
            items.add(tmpItem);
            mCursor.moveToNext();
        }
        mCursor.close();
        return items.toArray(new BarcodeItem[items.size()]);
    }
}