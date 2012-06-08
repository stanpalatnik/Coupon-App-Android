package com.mobilecoupon;

import com.biggu.barcodescanner.client.android.CaptureActivity;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/5/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CameraScanActivity extends CaptureActivity {
    @Override
    public int get_R_id_preview_view() {

        return R.id.preview_view;
    }

    @Override
    public int get_R_id_viewfinder_view() {

        return R.id.viewfinder_view;
    }

    @Override
    public int get_R_layout_scanner() {

        return R.layout.camera_scanner;
    }

    @Override
    public int get_R_raw_beep() {

        return R.raw.beep;
    }
}
