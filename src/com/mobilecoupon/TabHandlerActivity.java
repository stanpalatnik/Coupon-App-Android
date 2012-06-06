package com.mobilecoupon;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHandlerActivity extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        Intent intent1;

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, LoginActivity.class);
        intent1 = new Intent().setClass(this, BarcodeLookupOptionsActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("Scan").setIndicator("Scan Barcode",
                res.getDrawable(R.drawable.ic_tab_camera))
                .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, RegisterActivity.class);
        spec = tabHost.newTabSpec("View").setIndicator("View Coupon(s)",
                res.getDrawable(R.drawable.ic_tab_view))
                .setContent(intent1);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
