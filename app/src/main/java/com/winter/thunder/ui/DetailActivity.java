package com.winter.thunder.ui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.winter.thunder.R;
import com.winter.thunder.fragment.MainSliderFragment;
import static com.winter.thunder.task.UserTask.FirstName;
import static com.winter.thunder.task.UserTask.LastName;

public class DetailActivity extends AppCompatActivity {

    public static String form1Info;
    public static String form2Info;

    public Context cont = this;
    public static String formId;

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";

    FragmentManager FragmentManager;
    FragmentTransaction FragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);
        formId = extras.getString(EXTRA_QUOTE);
        Log.d("FORM ID", formId);

        FillHouseTask task1 = new FillHouseTask();
        task1.execute();

        FillFormTask task2 = new FillFormTask();
        task2.execute();

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.cinza_mais_claro));

        FragmentManager = getSupportFragmentManager();
        FragmentTransaction = FragmentManager.beginTransaction();
        FragmentTransaction.replace(R.id.containerView, new MainSliderFragment()).commit();
    }

    public class FillHouseTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... html_dict) {
            try {
                SQLiteDatabase DB = cont.openOrCreateDatabase("DataBase", MODE_PRIVATE, null);
                Cursor c = DB.rawQuery("SELECT * FROM houses WHERE id = '" + formId + "'", null);

                if (c.moveToFirst()) {
                    do {
                        form1Info = new StringBuilder()
                                .append("{'id': '").append(c.getString(0)).append("', 'client': '").append("ServiceLink")
                                .append("', 'loan': '").append("9564217893").append("', 'client_order': '").append("3124782145")
                                .append("', 'work_code': '").append(c.getString(8)).append("', 'address': '").append(c.getString(3))
                                .append("', 'owner': '").append(c.getString(18)).append("', 'start_date': '").append(c.getString(10))
                                .append("', 'due_date': '").append(c.getString(11)).append("', 'mtg_company': '")
                                .append("Specialized Loan Service")
                                .append("', 'price': '").append(c.getString(9)).append("', 'vacant': '").append(c.getString(20))
                                .append("', 'photos': '").append(c.getString(21)).append("', 'labels': '").append("Yes")
                                .append("', 'no_contact': '").append("Yes").append("', 'instructions': '").append(c.getString(22))
                                .append("'}").toString();
                    } while (c.moveToNext());
                }
                c.close();
                DB.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return form1Info;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            form1Info = result;
        }
    }


    public class FillFormTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... html_dict) {
            try {
                SQLiteDatabase DB = cont.openOrCreateDatabase("DataBase", MODE_PRIVATE, null);
                Cursor c = DB.rawQuery("SELECT * FROM forms WHERE id = '" + formId + "'", null);

                if (c.moveToFirst()) {
                    do {
                        form2Info = new StringBuilder()
                                .append("{'empty': '").append(c.getString(52))
                                .append("', 'first_name': '").append(FirstName).append("', 'last_name': '").append(LastName)
                                .append("', 'violation_posted': '").append(c.getString(3)).append("', 'postings_notices': '").append(c.getString(4))
                                .append("', 'occupancy_status': '").append(c.getString(5)).append("', 'vacancy_posting_left': '").append(c.getString(6))
                                .append("', 'card_left': '").append(c.getString(7)).append("', 'letter_delivered': '").append(c.getString(8))
                                .append("', 'occupancy_verified_by': '").append(c.getString(10)).append("', 'building_type': '").append(c.getString(11))
                                .append("', 'stories': '").append(c.getString(12)).append("', 'construction_type': '").append(c.getString(13))
                                .append("', 'color': '").append(c.getString(14)).append("', 'garage': '").append(c.getString(15))
                                .append("', 'additional_buildings': '").append(c.getString(16)).append("', 'yard': '").append(c.getString(17))
                                .append("', 'roof': '").append(c.getString(18)).append("', 'property_condition': '").append(c.getString(19))
                                .append("', 'property_damaged': '").append(c.getString(20)).append("', 'property_value': '").append(c.getString(21))
                                .append("', 'for_sale': '").append(c.getString(22)).append("', 'neighborhood_condition': '").append(c.getString(23))
                                .append("', 'how_many_sides': '").append(c.getString(24)).append("', 'appear_vacant': '").append(c.getString(25))
                                .append("', 'safety_violations': '").append(c.getString(26)).append("', 'presently_for_sale': '").append(c.getString(27))
                                .append("', 'typical_neighborhood': '").append(c.getString(28)).append("', 'under_construction': '").append(c.getString(29))
                                .append("', 'roof_condition': '").append(c.getString(30)).append("', 'windows_condition': '").append(c.getString(31))
                                .append("', 'finish_material': '").append(c.getString(32)).append("', 'condition_finish_material': '").append(c.getString(33))
                                .append("', 'condition_foundation': '").append(c.getString(34)).append("', 'condition_landscape': '").append(c.getString(35))
                                .append("', 'relation_to_properties': '").append(c.getString(36)).append("', 'general_comments': '").append(c.getString(37))
                                .append("', 'external_factors': '").append(c.getString(38)).append("', 'external_factors_comments': '").append(c.getString(39))
                                .append("', 'comments': '").append(c.getString(40)).append("', 'six_damage': '").append(c.getString(41))
                                .append("', 'safety_issue': '").append(c.getString(42)).append("', 'exterior_debris': '").append(c.getString(43))
                                .append("', 'lawn_maintenance': '").append(c.getString(44)).append("', 'property_vacant': '").append(c.getString(45))
                                .append("', 'escalated_events': '").append(c.getString(46)).append("', 'roof_damage': '").append(c.getString(47))
                                .append("', 'signs_vandalism': '").append(c.getString(48)).append("', 'unsecured_openings': '").append(c.getString(49))
                                .append("', 'notice_posted': '").append(c.getString(50)).append("', 'code_of_conduct': '").append(c.getString(51))
                                .append("'}").toString();
                    } while (c.moveToNext());
                }
                c.close();
                DB.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return form2Info;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            form2Info = result;
        }
    }
}