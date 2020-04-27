package com.example.simple_http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        loadFromWeb("https://www.oamk.fi");
    }
    protected void loadFromWeb(String ulrString){
        try {
            URL url=new URL(ulrString);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String htmlText = Htmlview.fromStream(in);
            TextView text = findViewById(R.id.textField);
            text.setMovementMethod(new ScrollingMovementMethod());
            text.setText(htmlText);
        }
        catch (Exception e) {e.printStackTrace();}
    }
}