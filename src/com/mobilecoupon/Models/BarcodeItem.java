package com.mobilecoupon.Models;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/7/12
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class BarcodeItem extends ListItem {
    private String coupon;

    public BarcodeItem(String title, String description, String coupon, int icon) {
        super(title, description, icon);
        this.coupon = coupon;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
