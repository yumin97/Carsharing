package com.example.carsharingtest.car;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.carsharingtest.R;
import com.example.carsharingtest.ReservationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarReservation extends AppCompatActivity {
    private ListView list;
    private CarListAdapter adapter;
    private List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reservation);

        Intent intent = getIntent();

        list = (ListView)findViewById(R.id.carList);
        carList = new ArrayList<Car>();

        adapter = new CarListAdapter(getApplicationContext(), carList);
        list.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("carList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String carID, carNumber, carModel;
            while(count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                carID = object.getString("carID");
                carNumber = object.getString("carNumber");
                carModel = object.getString("carModel");
                Car car = new Car(carID,carNumber,carModel);
                carList.add(car);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}