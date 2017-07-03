package com.winter.thunder.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.winter.thunder.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by winter on 3/19/2017.
 */
public class DatabaseHouseTask extends AsyncTask<String, Void, String> {

    private MainActivity mainActivity;

    public DatabaseHouseTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... houseList) {
        String result = "";
        String houseList1 = houseList[0];
        try {
            JSONArray arr = new JSONArray(houseList1);
            SQLiteDatabase DB = mainActivity.cont.openOrCreateDatabase("DataBase", Context.MODE_PRIVATE, null);
            DB.execSQL("DELETE FROM houses");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonPart = arr.getJSONObject(i);
                String sqlCommand = new StringBuilder()
                        .append("INSERT INTO houses (id, company_id, user_id, address, ")
                        .append("city, state, zip, county, work_code, inspect_pay, ")
                        .append("start_date, due_date, ordered_date, assigned_date, completed_date, ")
                        .append("submitted_date, follow_up_date, submitted_by, owner, lender, vacant, ")
                        .append("photo_required, instructions, notes, latitude, longitude, status) VALUES (\"")
                        .append(jsonPart.getString("id")).append("\", \"").append(jsonPart.getString("company_id"))
                        .append("\", \"").append(jsonPart.getString("user_id")).append("\", \"")
                        .append(jsonPart.getString("address")).append("\"").append(", \"").append(jsonPart.getString("city"))
                        .append("\"").append(", \"").append(jsonPart.getString("state")).append("\", ")
                        .append(jsonPart.getString("zip")).append(", \"").append(jsonPart.getString("county")).append("\", \"")
                        .append(jsonPart.getString("work_code")).append("\", ").append(jsonPart.getString("inspect_pay"))
                        .append(", \"").append(jsonPart.getString("start_date")).append("\", \"")
                        .append(jsonPart.getString("due_date")).append("\", \"").append(jsonPart.getString("ordered_date"))
                        .append("\", \"").append(jsonPart.getString("assigned_date")).append("\", \"")
                        .append(jsonPart.getString("completed_date")).append("\", \"").append(jsonPart.getString("submitted_date"))
                        .append("\", \"").append(jsonPart.getString("follow_up_date")).append("\", \"")
                        .append(jsonPart.getString("submitted_by")).append("\", \"").append(jsonPart.getString("owner"))
                        .append("\", \"").append(jsonPart.getString("lender")).append("\", \"").append(jsonPart.getString("vacant"))
                        .append("\", \"").append(jsonPart.getString("photo_required"))
                        .append("\", \"").append(jsonPart.getString("instructions")).append("\", \"")
                        .append(jsonPart.getString("notes")).append("\", ").append(jsonPart.getString("latitude"))
                        .append(", ").append(jsonPart.getString("longitude")).append(", \"").append(jsonPart.getString("status"))
                        .append("\")").toString();
                DB.execSQL(sqlCommand);
            }

            DB.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
