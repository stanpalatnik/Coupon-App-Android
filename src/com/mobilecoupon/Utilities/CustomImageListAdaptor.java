package com.mobilecoupon.Utilities;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/5/12
 * Time: 7:11 PM
 * To change this template use File | Settings | File Templates.
 */
import android.content.Context;
import android.graphics.Color;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobilecoupon.Models.ListItem;
import com.mobilecoupon.R;

import java.util.ArrayList;
import java.util.List;

public class CustomImageListAdaptor extends ArrayAdapter<ListItem> {
    private final Context context;
    private final ListItem[] values;

    public CustomImageListAdaptor(Context context, ListItem[] values) {
        super(context, R.layout.list_row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.title);
        TextView textView1 = (TextView) rowView.findViewById(R.id.description);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
        textView.setText(values[position].getTitle());
        textView1.setText(values[position].getDescription());
        imageView.setImageResource(values[position].getIcon());


        return rowView;
    }
}
