package com.winter.thunder.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import com.winter.thunder.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ThunderData {

    private static final int[] icons = {R.drawable.exclamation, R.drawable.hourglass,
            R.drawable.ok};

    public static List<ListItem> getListData(Context context, String status) {
        List<ListItem> data = new ArrayList<>();

        ThunderData td = new ThunderData();

        //CHANGE THE DATE COMPARISON TO CALENDAR BEFORE

        Calendar calendar;
        String todayString, todayYear, todayMonth, todayDay;
        int intTodayYear, intTodayMonth, intTodayDay;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date today = calendar.getTime();
        todayString = dateFormat.format(today);
        todayYear = todayString.substring(0,4);
        todayMonth = todayString.substring(5,7);
        todayDay = todayString.substring(8,10);
        intTodayYear = Integer.parseInt(todayYear);
        intTodayMonth = Integer.parseInt(todayMonth);
        intTodayDay = Integer.parseInt(todayDay);

        SQLiteDatabase DB = context.openOrCreateDatabase("DataBase", Activity.MODE_PRIVATE, null);
        Cursor c = DB.rawQuery("SELECT id, address, city, state, work_code, due_date FROM houses WHERE status = '" + status + "'", null);

        if (c.moveToFirst()) {
            do {
                //int addressIndex = c.getColumnIndex("address");
                ListItem item = new ListItem();
                int result = td.compareDates(c.getString(5), intTodayYear ,intTodayMonth ,intTodayDay);
                if (result == 2) {
                    item.setImageResId(icons[0]);
                } else if (result == 1) {
                    item.setImageResId(icons[1]);
                } else {
                    item.setImageResId(icons[2]);
                }
                item.setId(c.getString(0));
                item.setTitle(c.getString(1));
                item.setSubTitle(c.getString(2) + ", " + c.getString(3));
                data.add(item);
            } while (c.moveToNext());
        }
        c.close();
        DB.close();
        return data;
    }

    public int compareDates(String dateDueString, int intTodayYear, int intTodayMonth, int intTodayDay) {

        String dueYear = dateDueString.substring(0,4);
        String dueMonth = dateDueString.substring(5,7);
        String dueDay = dateDueString.substring(8,10);

        int intDueYear = Integer.parseInt(dueYear);
        int intDueMonth = Integer.parseInt(dueMonth);
        int intDueDay = Integer.parseInt(dueDay);

        if(intTodayYear > intDueYear) {
            return 2;
        }
        else if (intTodayYear == intDueYear) {
            if(intTodayMonth > intDueMonth) {
                return 2;
            }
            else if (intTodayMonth == intDueMonth){
                if(intTodayDay > intDueDay) {
                    return 2;
                }
                else if (intTodayDay == intDueDay) {
                    return 1;
                }
            }
        }
        return 0;
    }
}