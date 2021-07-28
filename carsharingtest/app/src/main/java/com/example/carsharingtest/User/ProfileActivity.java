package com.example.carsharingtest.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.carsharingtest.MainActivity;
import com.example.carsharingtest.R;
import com.example.carsharingtest.car.Car;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private EditText ed_userID, ed_userName, ed_userAge;
    private ImageView imageView;
    private String userjpg = MainActivity.userID+".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ed_userID = (EditText) findViewById(R.id.userID);
        ed_userName = (EditText) findViewById(R.id.userName);
        ed_userAge = (EditText) findViewById(R.id.userAge);
        imageView = (ImageView) findViewById(R.id.imageView);

        /*ed_userID.setText(MainActivity.userID);*/
        sendImageRequest();

        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("ProfileList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID, userName, userAge;
            while(count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userName = object.getString("userName");
                userAge = object.getString("userAge");
                /*Car car = new Car(carID,carNumber,carModel);*/

                ed_userID.setText(userID);
                ed_userName.setText(userName);
                ed_userAge.setText(userAge);

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendImageRequest() {
        String url = "http://192.168.1.164/image/uploads/" + userjpg;

        UserImage task = new UserImage(url,imageView);
        task.execute();
    }
}