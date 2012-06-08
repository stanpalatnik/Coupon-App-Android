package com.mobilecoupon.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: home
 * Date: 6/6/12
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkWrapper {

    private static final String API_URL =  "spalatnik.com/projects/trip/showCoupon.php";

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo =
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    public static JSONObject getProductCoupons(String username, String password, String barcode) {
        String url = API_URL + "?username=" + username + "&password=" + username + "&code=" + barcode;
        HttpGet get = new HttpGet(url);
        JSONObject object;

        HttpClient client = new DefaultHttpClient();
        try {
            HttpEntity response = client.execute(get).getEntity();
            String content = EntityUtils.toString(response);
            object = new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
           object = new JSONObject();
        }
        return object;
    }
}
