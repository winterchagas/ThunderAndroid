package com.winter.thunder.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.winter.thunder.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class LoginActivity extends Activity {

    Context c;
    EditText usernameEditText, passwordEditText;
    String username, password;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _("Login Activity born.");
        c = this;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        usernameEditText    = (EditText) findViewById(R.id.username_Text);
        passwordEditText    = (EditText) findViewById(R.id.password_Text);
        login_button        = (Button) findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _("Login button hit.");
                username = usernameEditText.getText()+"";
                password = passwordEditText.getText()+"";
                _("username: "+username);
                _("password: "+password);

                if (username.length() == 0 || password.length() == 0){
                    Toast.makeText(c, "Username or Password cannot be left blank.", Toast.LENGTH_SHORT).show();
                }

                Networking n = new Networking();
                n.execute("http://127.0.0.1:5000/auth", Networking.NETWORK_STATE_REGISTER);
            }
        });


    }
    public class Networking extends AsyncTask{

        public static final int NETWORK_STATE_REGISTER=1;
        @Override
        protected Object doInBackground(Object[] params) {
            _("doInBackground.");
            getJson((String) params[0], (Integer) params[1]);
            return null;
        }
    }

    private void getJson(String url, int state){
        HttpClient httpClient = new DefaultHttpClient();
        _("doing post to url now");

        HttpPost request = new HttpPost(url);
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        boolean valid = false;

        switch (state) {
            case Networking.NETWORK_STATE_REGISTER:
                postParameters.add(new BasicNameValuePair("username",username));
                postParameters.add(new BasicNameValuePair("password",password));
                valid = true;
                break;
            default:
                _("Warning, unknown state.");

        }

        if (valid){
            _("Valid!");

            BufferedReader bufferedReader = null;
            StringBuffer stringBuffer = new StringBuffer("");

            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
                request.setEntity(entity);
                HttpResponse response = httpClient.execute(request);

                bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = "";
                String LineSeparator = System.getProperty("line.separator");
                while ((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line + LineSeparator);
                }
                bufferedReader.close();
            }
            catch (Exception e) {
                _("Error doing networking. " + e.getMessage());
                e.printStackTrace();
            }

            _("result: " + stringBuffer);
        }
        else {
            _("Valid was not true, will do nothing.");
        }
    }


    private void _(String s){
        Log.d("MyApp  ", " LoginActivity  " + s);
    }
}


