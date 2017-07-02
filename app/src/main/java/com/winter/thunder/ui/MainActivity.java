package com.winter.thunder.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.winter.thunder.R;
import com.winter.thunder.task.DatabaseFormTask;
import com.winter.thunder.task.DatabaseHouseTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.winter.thunder.task.LoginTask.TOKEN;

public class MainActivity extends AppCompatActivity {
    static String houseList = "", formList = "";
    public static String itemChosen = "", uploadIDs = "";
    public Context cont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _(DateFormat.getDateTimeInstance().format(new Date()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.cinza_mais_claro));
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#2a2b2b")));

        ListView listview1 = (ListView) findViewById(R.id.listview1);

        // Try using String Array here
        final ArrayList<String> categories = new ArrayList<String>();
        //categories.add("Routes");
        categories.add("Open");
        categories.add("Follow-up Needed");
        categories.add("Ready to Upload");
        categories.add("Uploaded");
        categories.add("Cannot Upload");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
//        ArrayAdapter<String> arrayAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, categories);
        listview1.setAdapter(arrayAdapter);

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.i("person Tapped:", categories.get(position));
                itemChosen = ((TextView) view).getText().toString();
                toOpenActivity(view);
            }
        });

        createDataBase();
    }

//    public class CustomAdapter extends ArrayAdapter<String> {
//
//        public CustomAdapter(Context context, int resource, List<String> objects) {
//            super(context, resource, objects);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            TextView newView = (TextView) super.getView(position, convertView, parent);
//            //Do stuff to your text view
//
//
//            return newView;
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.download:
                DownloadHouseTask task = new DownloadHouseTask();
                task.execute("https://thunder-api.herokuapp.com/house_list");
//                task.execute("http://10.0.2.2:5000/house_list");

                DownloadFormTask dataTask1 = new DownloadFormTask();
                dataTask1.execute("https://thunder-api.herokuapp.com/form_list");
//                dataTask1.execute("http://10.0.2.2:5000/form_list");
                return true;

            case R.id.upload:
                UploadHouseTask task3 = new UploadHouseTask();
                task3.execute("https://thunder-api.herokuapp.com/house_list");
//                task3.execute("http://10.0.2.2:5000/house_list");

                UploadFormTask task4 = new UploadFormTask();
                task4.execute("https://thunder-api.herokuapp.com/form_list");
//                task4.execute("http://10.0.2.2:5000/form_list");
                return true;
            case R.id.search:
                return true;
            case R.id.restore:
                return true;
            case R.id.logout:
                return true;
            case R.id.feedback:
                return true;
            default:
                return false;
        }
    }


    public void toOpenActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), HouseListActivity.class);
        startActivity(intent);
    }


    public void createDataBase() {
        try {
            this.deleteDatabase("DataBase");
            SQLiteDatabase DB = this.openOrCreateDatabase("DataBase", MODE_PRIVATE, null);
            //eventsDB.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INTEGER(3), id INTEGER PRIMARY KEY)");
            DB.execSQL("CREATE TABLE IF NOT EXISTS houses (id TEXT PRIMARY KEY, company_id TEXT, user_id TEXT, address text, city text, state text, zip int, county text, work_code text, inspect_pay real, start_date text, due_date text, ordered_date text , assigned_date text, completed_date text, submitted_date text, follow_up_date text , submitted_by text, owner text, lender text, vacant text, photo_required text, instructions text, notes text, latitude real, longitude real, status text)");
            DB.execSQL("CREATE TABLE IF NOT EXISTS forms (id TEXT PRIMARY KEY, user_id TEXT, completed_date text, violation_posted text, postings_notices text, occupancy_status text, vacancy_posting_left text, card_left text, letter_delivered text, submission_statement text, occupancy_verified_by text, building_type text, stories text , construction_type text, color text, garage text , additional_buildings text, yard text, roof text, property_condition text, property_damaged text, property_value text, for_sale text, neighborhood_condition text, how_many_sides text, appear_vacant text, safety_violations text, presently_for_sale text, typical_neighborhood text, under_construction text, roof_condition text, windows_condition text, finish_material text, condition_finish_material text, condition_foundation text, condition_landscape text, relation_to_properties text, general_comments text, external_factors text, external_factors_comments text, comments text, six_damage text, safety_issue text, exterior_debris text, lawn_maintenance text, property_vacant text, escalated_events text, roof_damage text, signs_vandalism text, unsecured_openings text, notice_posted text, code_of_conduct text, empty text)");
            _("Database created.");
            DB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class DownloadHouseTask extends AsyncTask<String, Void, String> {
        //<String, Void, String>  pass String, void does nothing while task in progress, return a String

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]); //first url passed to the method
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "JWT " + TOKEN);
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read(); //content from the server
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override  //called once dobackground is completed, get the result from it as parameter
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                houseList = jsonObject.getString("items");
                Log.d("HOUSE LIST", houseList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            DatabaseHouseTask dataTask1 = new DatabaseHouseTask(MainActivity.this);
            dataTask1.execute(houseList);
        }
    }


    public class DownloadFormTask extends AsyncTask<String, Void, String> {
        //<String, Void, String>  pass String, void does nothing while task in progress, return a String

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]); //first url passed to the method
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "JWT " + TOKEN);
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read(); //content from the server
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                formList = jsonObject.getString("items");
                Log.d("FORMLIST", formList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            DatabaseFormTask dataTask2 = new DatabaseFormTask(MainActivity.this);
            dataTask2.execute(formList);
        }
    }


    public class UploadHouseTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            Log.d("INSIDE BACKGROUND", "=)");
            try {
                url = new URL(urls[0]); //first url passed to the method
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "JWT " + TOKEN);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoInput(true);
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                SQLiteDatabase DB = cont.openOrCreateDatabase("DataBase", Activity.MODE_PRIVATE, null);
                Cursor c = DB.rawQuery("SELECT id, completed_date, submitted_date, submitted_by, status FROM houses WHERE status = 'Ready to Upload'", null);
                String singleHouse = "";
                String allHouses = "{\"houseList\": ";

                if (c.moveToFirst()) {
                    do {
                        singleHouse = new StringBuilder()
                                .append("{\"id\": \"").append(c.getString(0)).append("\", \"completed_date\": \"")
                                .append(c.getString(1)).append("\", \"submitted_date\": \"").append(c.getString(2))
                                .append("\", \"submitted_by\": \"").append(c.getString(3))
                                .append("\", \"status\": \"").append("Done")
                                .append("\"}").toString();
                        /*allHouses = allHouses + singleHouse;*/
                        uploadIDs = c.getString(0);

                        /*String sqlCommand = new StringBuilder()
                                .append("UPDATE houses SET status = \"").append("Uploaded")
                                .append("\" WHERE id = \"").append(c.getString(0)).append("\"").toString();
                        DB.execSQL(sqlCommand);*/

                    } while (c.moveToNext());
                }
                c.close();
                DB.close();

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                String allhouses2 = allHouses.substring(0, allHouses.length() - 1);
                Log.d("ALL HOuSES", singleHouse);
                writer.write(singleHouse);
                writer.flush();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read(); //content from the server
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                _(result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    public class UploadFormTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            Log.d("INSIDE BACKGROUND", "=)");
            try {
                url = new URL(urls[0]); //first url passed to the method
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "JWT " + TOKEN);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoInput(true);
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                SQLiteDatabase DB = cont.openOrCreateDatabase("DataBase", Activity.MODE_PRIVATE, null);
                Cursor c = DB.rawQuery("SELECT * FROM forms WHERE id = '" + uploadIDs + "'", null);
                _("ID FORM:"+uploadIDs);
                String singleForm = "";
                /*String allHouses = "{\"houseList\": ";*/

                if (c.moveToFirst()) {
                    do {
                        singleForm = new StringBuilder()
                                .append("{\"empty\": \"").append(c.getString(52)).append("\", \"id\": \"").append(c.getString(0))
                                //.append("', 'first_name': '").append(FirstName).append("', 'last_name': '").append(LastName)
                                .append("\", \"violation_posted\": \"").append(c.getString(3)).append("\", \"postings_notices\": '").append(c.getString(4))
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
                        /*allHouses = allHouses + singleHouse;*/
                        _("INFO FORM: "+singleForm);
                    } while (c.moveToNext());
                }
                c.close();
                DB.close();

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                /*String allhouses2 = allHouses.substring(0, allHouses.length() - 1);
                Log.d("ALL HOuSES", allhouses2);*/
                String replacedForm = singleForm.replace('\'', '\"');
                _(replacedForm);
                writer.write(replacedForm);
                writer.flush();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read(); //content from the server
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                _(result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static void _(String s) {
        Log.d("MainActivity: ", s);
    }
}
