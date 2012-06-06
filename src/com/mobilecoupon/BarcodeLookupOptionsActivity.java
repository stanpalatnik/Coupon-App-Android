package com.mobilecoupon;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.mobilecoupon.Models.ListItem;
import com.mobilecoupon.Utilities.CustomImageListAdaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarcodeLookupOptionsActivity extends ListActivity {

    static final ListItem[] LIST_ITEMS = new ListItem[]
            {
                    new ListItem("Scan Barcode", "Scan a product barcode with your camera.",R.drawable.ic_camera_transparent),
                    new ListItem("Manual Barcode Search", "Manually enter a barcode via textbox.",R.drawable.ic_search),
                    new ListItem("View Past Searches", "View previous barcode searches to save time on repeat items.",R.drawable.ic_notepad)
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new CustomImageListAdaptor(this, LIST_ITEMS));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);


    }
}