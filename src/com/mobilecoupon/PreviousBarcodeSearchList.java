package com.mobilecoupon;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.mobilecoupon.Models.ListItem;
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

    private SQLiteWrapper handle = SQLiteWrapper.getInstance(this);
    private static final int MAX_DAYS_SINCE_SEARCH = 10;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListItem[] barcodes = handle.getRecentlySearchedProducts(handle.getReadableDatabase(),MAX_DAYS_SINCE_SEARCH);
        setListAdapter(new CustomImageListAdaptor(this, barcodes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
    }
}
