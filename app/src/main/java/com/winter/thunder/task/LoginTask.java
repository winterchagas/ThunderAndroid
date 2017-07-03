package com.winter.thunder.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.winter.thunder.ui.LoginActivity2;
import com.winter.thunder.ui.MainActivity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LoginTask extends AsyncTask<String, Void, String> {
    private Activity activity;
    public static String TOKEN = "";

    public LoginTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]); //first url passed to the method
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", LoginActivity2.username));
            params.add(new BasicNameValuePair("password", LoginActivity2.password));

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Log.d("query",getQuery(params));
            writer.write(getQuery(params));  //<--- sending data.
            writer.flush();

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
            String access_token = jsonObject.getString("access_token");
            Log.i("ACCESS TOKEN:", access_token);
            TOKEN = access_token;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UserTask task1 = new UserTask();
        task1.execute("https://thunder-api.herokuapp.com/register_user", TOKEN);
//      task1.execute("http://10.0.2.2:5000/register_user", TOKEN);
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        result.append("{ ");
        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append(" ,");

            result.append("\"");
            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("\"");
            result.append(": ");
            result.append("\"");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            result.append("\"");
        }
        result.append(" }");
        return result.toString();
    }
}



























