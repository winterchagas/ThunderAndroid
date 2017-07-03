package com.winter.thunder.task;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UserTask extends AsyncTask<String, Void, Void> {

    public static  String UserName = "";
    public static  String UserId = "";
    public static  String FirstName = "";
    public static  String LastName = "";

    URL url;
    HttpURLConnection urlConnection = null;

    @Override
    protected Void doInBackground(String... urls) {

        String token = urls[1];
        String result = "";
        try {
            url = new URL(urls[0]); //first url passed to the method
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Authorization", "JWT " + token);
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] parts = result.split("\"");
        FirstName = parts[3];
        LastName = parts[7];
        UserId = parts[11];
        UserName = parts[15];
        return null;
    }

}

