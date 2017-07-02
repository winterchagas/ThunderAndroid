package com.winter.thunder.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.winter.thunder.R;
import com.winter.thunder.task.LoginTask;


public class LoginActivity2 extends Activity {

    Context c;
    EditText usernameEditText, passwordEditText;
    public static String username, password;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = this;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login2);

        usernameEditText = (EditText) findViewById(R.id.username_Text);
        passwordEditText = (EditText) findViewById(R.id.password_Text);
        login_button = (Button) findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText() + "";
                password = passwordEditText.getText() + "";

                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(c, "Username or Password cannot be left blank.", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginTask task = new LoginTask(LoginActivity2.this);
                    task.execute("https://thunder-api.herokuapp.com/auth");
//                    task.execute("http://10.0.2.2:5000/auth");
                }
            }
        });
    }

    public static void _(String s) {
        Log.d("LoginActivity  ",  s);
    }
}


