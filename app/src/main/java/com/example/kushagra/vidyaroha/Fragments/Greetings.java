package com.example.kushagra.vidyaroha.Fragments;

/**
 * Created by Kushagra Saxena on 12/08/2017.
 */


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushagra.vidyaroha.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class abc implements Runnable {
    abc() {
    }

    public void run() {

    }
}

public class Greetings extends Fragment {
    TextView textview, toastText;
    Toast customtoast;

    public static String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.greet, container, false);
        textview = (TextView) rootView.findViewById(R.id.greetText);

        String day = get_count_of_days( getCurrentDate(),getInstalledDateTime());
        String greet = "Hello user , you have entered into the app for the " + day + " time today";
        textview.setText(greet);

        View customToastroot = inflater.inflate(R.layout.custom_toast, null);
        toastText = (TextView) customToastroot.findViewById(R.id.textView1);
        toastText.setText("Today is your " + day + " day");
        customtoast = new Toast(getContext());
        customtoast.setView(customToastroot);
        customtoast.setGravity(81, 0, 100);
        customtoast.setDuration(Toast.LENGTH_SHORT);
        customtoast.show();
        new Handler().postDelayed(new abc(), 2000);
//        Toast.makeText(getContext(),"Today is your "+day+" day",Toast.LENGTH_SHORT).show();


        return rootView;
    }

    private String getInstalledDateTime() {
        long installed = 0;
        try {
            installed = getContext()
                    .getPackageManager()
                    .getPackageInfo(getContext().getPackageName(), 0)
                    .firstInstallTime
            ;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        // get date time in custom format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date(installed));
    }

    public String get_count_of_days(String Created_date_String, String Expire_date_String) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        Date Created_convertedDate = null, Expire_CovertedDate = null, todayWithZeroTime = null;
        try {
            Created_convertedDate = dateFormat.parse(Created_date_String);
            Expire_CovertedDate = dateFormat.parse(Expire_date_String);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int c_year = 0, c_month = 0, c_day = 0;

        if (Created_convertedDate.after(todayWithZeroTime)) {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(Created_convertedDate);
            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(todayWithZeroTime);
            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);
        }


        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(Expire_CovertedDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff =  date1.getTimeInMillis()-date2.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ("" + ((int) dayCount ));
    }


}