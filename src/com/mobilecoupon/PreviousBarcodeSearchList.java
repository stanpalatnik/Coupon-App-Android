package com.mobilecoupon;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.mobilecoupon.Models.BarcodeItem;
import com.mobilecoupon.Utilities.CustomImageListAdaptor;
import com.mobilecoupon.Utilities.SQLiteWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/5/12
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class PreviousBarcodeSearchList extends ListActivity {

    private static final int MAX_DAYS_SINCE_SEARCH = 10;
    BarcodeItem selectedValue;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteWrapper handle = SQLiteWrapper.getInstance(PreviousBarcodeSearchList.this);
        BarcodeItem[] barcodes = handle.getRecentlySearchedProducts(handle.getReadableDatabase(),MAX_DAYS_SINCE_SEARCH);
        Log.v("Count", "Count: " + barcodes.length);
        setListAdapter(new CustomImageListAdaptor(this, barcodes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        selectedValue = (BarcodeItem) getListAdapter().getItem(position);

        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("Found Coupon! Description: ").append(selectedValue.getDescription());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msgBuilder.toString()).setPositiveButton("Add Coupon", dialogClickListener)
                .setNegativeButton("Ignore", dialogClickListener).show();
    }

    private void launchIntent(Class classToStart) {
        Intent it = new Intent(PreviousBarcodeSearchList.this, classToStart);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        //@Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    SQLiteWrapper sqLiteWrapper = SQLiteWrapper.getInstance(PreviousBarcodeSearchList.this);
                    sqLiteWrapper.insertCouponCode(sqLiteWrapper.getWritableDatabase(),selectedValue.getCoupon());

                    AlertDialog.Builder builder = new AlertDialog.Builder(PreviousBarcodeSearchList.this);
                    builder.setMessage("Coupon added!").setPositiveButton("Add More", alertBoxListener)
                            .setNegativeButton("Return to Main Menu", alertBoxListener).show();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    DialogInterface.OnClickListener alertBoxListener = new DialogInterface.OnClickListener() {
        //@Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    launchIntent(TabHandlerActivity.class);
                    break;
            }
        }
    };
}
