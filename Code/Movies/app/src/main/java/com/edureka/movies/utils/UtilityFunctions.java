package com.edureka.movies.utils;

import android.app.Activity;
import android.net.ConnectivityManager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class UtilityFunctions {


    public static boolean isInternetOn(Activity a) {

        ConnectivityManager connec =
                (ConnectivityManager)a.getSystemService(a.getBaseContext().CONNECTIVITY_SERVICE);

        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            return false;
        }
        return false;
    }

    public static String movieTime(int t){
        int hours = t / 60;
        int minutes = t % 60;
        return hours +" hrs " + minutes +" min";
    }


    public static String getAllGenres(String genre){
        String s2=genre.replace("\"", "");
        String s3=s2.replace("[", "");
        String s4=s3.replace("]", "");
        return s4.replace(",",", ");
    }

    public static String formatReleaseDate(String releaseDate){
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(UtilityConstants.DATE_FORMAT);
            Date dat = sdf1.parse(releaseDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat(UtilityConstants.DATE_FORMAT_SHOWN);
            return sdf2.format(dat);
        } catch (ParseException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
}
