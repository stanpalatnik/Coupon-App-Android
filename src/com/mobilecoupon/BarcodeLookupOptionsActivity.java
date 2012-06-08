package com.mobilecoupon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.biggu.barcodescanner.client.android.Intents;
import com.mobilecoupon.Models.ListItem;
import com.mobilecoupon.Utilities.CustomImageListAdaptor;
import com.mobilecoupon.Utilities.NetworkWrapper;
import org.json.JSONException;
import org.json.JSONObject;


public class BarcodeLookupOptionsActivity extends ListActivity {
    private static final int SCANNER_REQUEST_CODE = 0;
    private static final String SCAN_BARCODE = "Scan Barcode";
    private static final String MANUAL_SEARCH = "Manual Barcode Search";
    private static final String PAST_SEARCHES = "View Past Searches";

    static final ListItem[] LIST_ITEMS = new ListItem[]
            {
                    new ListItem(SCAN_BARCODE, "Scan a product barcode with your camera.", R.drawable.ic_camera_transparent),
                    new ListItem(MANUAL_SEARCH, "Manually enter a barcode via textbox.", R.drawable.ic_search),
                    new ListItem(PAST_SEARCHES, "View previous barcode searches to save time on repeat items.", R.drawable.ic_notepad)
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new CustomImageListAdaptor(this, LIST_ITEMS));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        ListItem selectedValue = (ListItem) getListAdapter().getItem(position);

        if (selectedValue.getTitle().equals(SCAN_BARCODE)) {
            Intent intent = new Intent(v.getContext(), com.mobilecoupon.CameraScanActivity.class);
            intent.putExtra(Intents.Preferences.ENABLE_BEEP, true);
            intent.putExtra(Intents.Preferences.ENABLE_VIBRATE, true);

            ((Activity) v.getContext()).startActivityForResult(intent, SCANNER_REQUEST_CODE);
        } else if (selectedValue.getTitle().equals(MANUAL_SEARCH)) {
            launchIntent(ManualBarcodeSearch.class);
        } else if (selectedValue.getTitle().equals(PAST_SEARCHES)) {
            launchIntent(PreviousBarcodeSearchList.class);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == SCANNER_REQUEST_CODE) {

            Bundle extras = data.getExtras();
            String result = extras.getString("SCAN_RESULT");
            if (result != null) {
                boolean hasInternet = NetworkWrapper.isConnected(BarcodeLookupOptionsActivity.this);
                if (!hasInternet) {
                    //show dialog saying we have no access
                } else {
                    String coupon;
                    //find coupons for this barcode
                    SharedPreferences settings = getSharedPreferences("Login",
                            Activity.MODE_PRIVATE);
                    String userName = settings.getString("userName", "");
                    String password = settings.getString("userName", "");

                    JSONObject response = NetworkWrapper.getProductCoupons(userName, password, result);
                    try {
                        coupon = response.getString("Coupon");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        coupon = "";
                    }
                    if (coupon.length() > 0) {
                        try {
                            String description = response.getString("Description");

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                //@Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            launchIntent(CameraScanActivity.class);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            break;
                                    }
                                }
                            };

                            StringBuilder msgBuilder = new StringBuilder();
                            msgBuilder.append("Found Coupon! Description: ").append(description);
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(msgBuilder.toString()).setPositiveButton("Add Coupon", dialogClickListener)
                                    .setNegativeButton("Ignore", dialogClickListener).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        //Say  no coupon has been found
                    }
                }
            }
        }
    }

    private void launchIntent(Class classToStart) {
        Intent it = new Intent(BarcodeLookupOptionsActivity.this, classToStart);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }


}