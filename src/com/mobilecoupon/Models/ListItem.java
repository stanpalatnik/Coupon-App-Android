package com.mobilecoupon.Models;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/5/12
 * Time: 7:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListItem {
    private String title;
    private String description;
    private int icon;

    public ListItem(String title, String description, int icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
