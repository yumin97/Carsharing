package com.example.carsharingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.carsharingtest.User.ProfileActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    // 유저 아이디를 전역변수를 지정합니다.
    public static String userID;
    public String car = "http://192.168.1.164/carList.php";
    public String history = "http://192.168.1.164/HistoryList.php?userID=";
    public String profile = "http://192.168.1.164/userProfile.php?userID=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = getIntent().getStringExtra("userID");
    }

    public void onMyProfile(View view) {
        BackgroundTask task = new BackgroundTask();
        task.execute(profile);
    }


    public void onReservation(View view) {
        BackgroundTask task = new BackgroundTask();
        task.execute(car);
    }


    public void onimageRegister(View view) {
        Intent image = new Intent(MainActivity.this, imageActivity.class);
        startActivity(image);
    }

    public void onLogout(View view) {
        Intent Login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(Login);
        finish();
    }

    public void onHistory(View view) {
        BackgroundTask task = new BackgroundTask();
        task.execute(history);
    }

    private class BackgroundTask extends AsyncTask<String, Void, String> {
        String target;
        int i;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                if (urls[0].equals(car)) {
                    i = 1;
                    target = car;
                }
                else if (urls[0].equals(history)) {
                    i = 2;
                    target = history + userID;
                }
                else if (urls[0].equals(profile)) {
                    i = 3;
                    target = profile + userID;
                }

                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferdReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferdReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {

            switch (i) {
                case 1:
                    Intent car = new Intent(MainActivity.this, com.example.carsharingtest.car.CarReservation.class);
                    car.putExtra("carList", result);
                    startActivity(car);
                    break;
                case 2:
                    Intent History = new Intent(MainActivity.this, HistoryActivity.class);
                    History.putExtra("HistoryList", result);
                    startActivity(History);
                    break;
                case 3:
                    Intent myProfile = new Intent(MainActivity.this, ProfileActivity.class);
                    myProfile.putExtra("ProfileList", result);
                    startActivity(myProfile);
                    break;
            }

        }
    }
}