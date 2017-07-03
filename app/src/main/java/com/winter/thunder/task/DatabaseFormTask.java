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
public class DatabaseFormTask extends AsyncTask<String, Void, String> {

    private Context context;

    public DatabaseFormTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... formList) {
        String result = "";
        String formList1 = formList[0];
        try {
            JSONArray arr = new JSONArray(formList1);
            SQLiteDatabase DB = context.openOrCreateDatabase("DataBase", Context.MODE_PRIVATE, null);
            DB.execSQL("DELETE FROM forms");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonPart = arr.getJSONObject(i);

                String sqlCommand = new StringBuilder()
                        .append("INSERT INTO forms (id, user_id, completed_date, violation_posted, ")
                        .append("postings_notices, occupancy_status, vacancy_posting_left, card_left, ")
                        .append("letter_delivered, submission_statement, occupancy_verified_by, ")
                        .append("building_type, stories, construction_type, color, garage, additional_buildings, ")
                        .append("yard, roof, property_condition, property_damaged, property_value, for_sale, ")
                        .append("neighborhood_condition, how_many_sides, appear_vacant, safety_violations, ")
                        .append("presently_for_sale, typical_neighborhood, under_construction, roof_condition, ")
                        .append("windows_condition, finish_material, condition_finish_material, condition_foundation, ")
                        .append("condition_landscape, relation_to_properties, general_comments, external_factors, ")
                        .append("external_factors_comments, comments, six_damage, safety_issue, exterior_debris, ")
                        .append("lawn_maintenance, property_vacant, escalated_events, roof_damage, signs_vandalism, ")
                        .append("unsecured_openings, notice_posted, code_of_conduct, empty) VALUES (\"")
                        .append(jsonPart.getString("id")).append("\", \"").append(jsonPart.getString("user_id")).append("\", \"")
                        .append(jsonPart.getString("completed_date")).append("\", \"").append(jsonPart.getString("violation_posted"))
                        .append("\", \"").append(jsonPart.getString("postings_notices")).append("\", \"")
                        .append(jsonPart.getString("occupancy_status")).append("\", \"").append(jsonPart.getString("vacancy_posting_left"))
                        .append("\", \"").append(jsonPart.getString("card_left")).append("\", \"").append(jsonPart.getString("letter_delivered"))
                        .append("\", \"").append(jsonPart.getString("submission_statement")).append("\", \"")
                        .append(jsonPart.getString("occupancy_verified_by")).append("\", \"").append(jsonPart.getString("building_type"))
                        .append("\", \"").append(jsonPart.getString("stories")).append("\", \"").append(jsonPart.getString("construction_type"))
                        .append("\", \"").append(jsonPart.getString("color")).append("\", \"").append(jsonPart.getString("garage"))
                        .append("\", \"").append(jsonPart.getString("additional_buildings")).append("\", \"").append(jsonPart.getString("yard"))
                        .append("\", \"").append(jsonPart.getString("roof")).append("\", \"").append(jsonPart.getString("property_condition"))
                        .append("\", \"").append(jsonPart.getString("property_damaged")).append("\", \"").append(jsonPart.getString("property_value"))
                        .append("\", \"").append(jsonPart.getString("for_sale")).append("\", \"").append(jsonPart.getString("neighborhood_condition"))
                        .append("\", \"").append(jsonPart.getString("how_many_sides")).append("\", \"").append(jsonPart.getString("appear_vacant"))
                        .append("\", \"").append(jsonPart.getString("safety_violations")).append("\", \"")
                        .append(jsonPart.getString("presently_for_sale")).append("\", \"").append(jsonPart.getString("typical_neighborhood"))
                        .append("\", \"").append(jsonPart.getString("under_construction")).append("\", \"")
                        .append(jsonPart.getString("roof_condition")).append("\", \"").append(jsonPart.getString("windows_condition"))
                        .append("\", \"").append(jsonPart.getString("finish_material")).append("\", \"")
                        .append(jsonPart.getString("condition_finish_material")).append("\", \"")
                        .append(jsonPart.getString("condition_foundation")).append("\", \"").append(jsonPart.getString("condition_landscape"))
                        .append("\", \"").append(jsonPart.getString("relation_to_properties")).append("\", \"")
                        .append(jsonPart.getString("general_comments")).append("\", \"").append(jsonPart.getString("external_factors"))
                        .append("\", \"").append(jsonPart.getString("external_factors_comments")).append("\", \"")
                        .append(jsonPart.getString("comments")).append("\", \"").append(jsonPart.getString("six_damage"))
                        .append("\", \"").append(jsonPart.getString("safety_issue")).append("\", \"").append(jsonPart.getString("exterior_debris"))
                        .append("\", \"").append(jsonPart.getString("lawn_maintenance")).append("\", \"").append(jsonPart.getString("property_vacant"))
                        .append("\", \"").append(jsonPart.getString("escalated_events")).append("\", \"").append(jsonPart.getString("roof_damage"))
                        .append("\", \"").append(jsonPart.getString("signs_vandalism")).append("\", \"")
                        .append(jsonPart.getString("unsecured_openings")).append("\", \"").append(jsonPart.getString("notice_posted"))
                        .append("\", \"").append(jsonPart.getString("code_of_conduct")).append("\", \"")
                        .append(jsonPart.getString("empty")).append("\")").toString();
                MainActivity._("SQL COMMAND:" + sqlCommand);
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
