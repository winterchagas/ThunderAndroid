package com.winter.thunder.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import com.winter.thunder.R;
import org.json.JSONException;
import org.json.JSONObject;
import static android.content.Context.MODE_PRIVATE;
import static com.winter.thunder.ui.DetailActivity.formId;
import static com.winter.thunder.ui.DetailActivity.form2Info;
import static com.winter.thunder.task.UserTask.FirstName;


public class FormFragment extends Fragment {

    public FormFragment() {
    }

    WebView webView;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_form, container, false);
        btn = (Button) v.findViewById(R.id.button);
        webView = (WebView) v.findViewById(R.id.webViewForm);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setDomStorageEnabled(true);
        //webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        final MyJavaScriptInterface myJavaScriptInterface
                = new MyJavaScriptInterface(getActivity());
        webView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        webView.loadUrl("file:///android_asset/ThunderForm.html");
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.d("onPageFinished", form2Info);
                webView.loadUrl("javascript:FillForm(\""+form2Info+"\")");
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:Validate()");
            }
        });
        return v;
        /*in JavaScript I can use localStorage.getItem(), localStorage.setItem().
        *
        * var myProp = localStorage.getItem("myProperty");
        *
        *  <script>
       window.localStorage.setItem("name","MyName");
    </script>
    */
        /*IN CASE THE DATA FROM HTML IS LOST WHEN THE APP IS OFFLINE
        String databasePath = this.getApplicationContext().getDir("databases", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setDatabasePath(databasePath);*/
    }

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void saveInfoDatabase(String html_dict) {
            DatabaseFormTask dataTask1 = new DatabaseFormTask();
            dataTask1.execute(html_dict);
            DatabaseHouseTask dataTask2 = new DatabaseHouseTask();
            dataTask2.execute(html_dict);
        }
    }


    public class DatabaseFormTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... html_dict) {
            try {
                JSONObject jsonObject = new JSONObject(html_dict[0]);
                SQLiteDatabase DB = getActivity().openOrCreateDatabase("DataBase", MODE_PRIVATE, null);
                String sqlCommand = new StringBuilder()
                        .append("UPDATE forms SET completed_date = \"").append(jsonObject.getString("completed_date"))
                        .append("\", violation_posted = \"").append(jsonObject.getString("violation_posted"))
                        .append("\", postings_notices = \"").append(jsonObject.getString("postings_notices"))
                        .append("\", occupancy_status = \"").append(jsonObject.getString("occupancy_status"))
                        .append("\", vacancy_posting_left = \"").append(jsonObject.getString("vacancy_posting_left"))
                        .append("\", card_left = \"").append(jsonObject.getString("card_left"))
                        .append("\", letter_delivered = \"").append(jsonObject.getString("letter_delivered"))
                        .append("\", submission_statement = \"").append(jsonObject.getString("submission_statement"))
                        //.append("\", occupancy_verified_by = \"").append(jsonObject.getString("occupancy_verified_by"))
                        .append("\", building_type = \"").append(jsonObject.getString("building_type"))
                        .append("\", stories = \"").append(jsonObject.getString("stories"))
                        .append("\", construction_type = \"").append(jsonObject.getString("construction_type"))
                        .append("\", color = \"").append(jsonObject.getString("color"))
                        .append("\", garage = \"").append(jsonObject.getString("garage"))
                        .append("\", additional_buildings = \"").append(jsonObject.getString("additional_buildings"))
                        .append("\", yard = \"").append(jsonObject.getString("yard"))
                        .append("\", roof = \"").append(jsonObject.getString("roof"))
                        .append("\", property_condition = \"").append(jsonObject.getString("property_condition"))
                        .append("\", property_damaged = \"").append(jsonObject.getString("property_damaged"))
                        .append("\", property_value = \"").append(jsonObject.getString("property_value"))
                        .append("\", for_sale = \"").append(jsonObject.getString("for_sale"))
                        .append("\", neighborhood_condition = \"").append(jsonObject.getString("neighborhood_condition"))
                        .append("\", how_many_sides = \"").append(jsonObject.getString("how_many_sides"))
                        .append("\", appear_vacant = \"").append(jsonObject.getString("appear_vacant"))
                        .append("\", safety_violations = \"").append(jsonObject.getString("safety_violations"))
                        .append("\", presently_for_sale = \"").append(jsonObject.getString("presently_for_sale"))
                        .append("\", typical_neighborhood = \"").append(jsonObject.getString("typical_neighborhood"))
                        .append("\", under_construction = \"").append(jsonObject.getString("under_construction"))
                        .append("\", roof_condition = \"").append(jsonObject.getString("roof_condition"))
                        .append("\", windows_condition = \"").append(jsonObject.getString("windows_condition"))
                        .append("\", finish_material = \"").append(jsonObject.getString("finish_material"))
                        .append("\", condition_finish_material = \"").append(jsonObject.getString("condition_finish_material"))
                        .append("\", condition_foundation = \"").append(jsonObject.getString("condition_foundation"))
                        .append("\", condition_landscape = \"").append(jsonObject.getString("condition_landscape"))
                        .append("\", relation_to_properties = \"").append(jsonObject.getString("relation_to_properties"))
                        .append("\", general_comments = \"").append(jsonObject.getString("general_comments"))
                        //.append("\", external_factors = \"").append(jsonObject.getString("external_factors"))
                        .append("\", external_factors_comments = \"").append(jsonObject.getString("external_factors_comments"))
                        .append("\", comments = \"").append(jsonObject.getString("comments"))
                        .append("\", six_damage = \"").append(jsonObject.getString("six_damage"))
                        .append("\", safety_issue = \"").append(jsonObject.getString("safety_issue"))
                        .append("\", exterior_debris = \"").append(jsonObject.getString("exterior_debris"))
                        .append("\", lawn_maintenance = \"").append(jsonObject.getString("lawn_maintenance"))
                        .append("\", property_vacant = \"").append(jsonObject.getString("property_vacant"))
                        .append("\", escalated_events = \"").append(jsonObject.getString("escalated_events"))
                        .append("\", roof_damage = \"").append(jsonObject.getString("roof_damage"))
                        .append("\", signs_vandalism = \"").append(jsonObject.getString("signs_vandalism"))
                        .append("\", unsecured_openings = \"").append(jsonObject.getString("unsecured_openings"))
                        .append("\", notice_posted = \"").append(jsonObject.getString("notice_posted"))
                        .append("\", code_of_conduct = \"").append(jsonObject.getString("code_of_conduct"))
                        .append("\", empty = \"").append("no")
                        .append("\" WHERE id = \"").append(formId).append("\"").toString();

                DB.execSQL(sqlCommand);
                DB.close();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class DatabaseHouseTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... html_dict) {
            try {
                JSONObject jsonObject = new JSONObject(html_dict[0]);
                SQLiteDatabase DB = getActivity().openOrCreateDatabase("DataBase", MODE_PRIVATE, null);
                String sqlCommand = new StringBuilder()
                        .append("UPDATE houses SET completed_date = \"").append(jsonObject.getString("completed_date"))
                        .append("\", submitted_date = \"").append(jsonObject.getString("completed_date"))
                        .append("\", submitted_by = \"").append(FirstName)
                        .append("\", status = \"").append("Ready to Upload")
                        .append("\" WHERE id = \"").append(formId).append("\"").toString();
                DB.execSQL(sqlCommand);
                DB.close();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}