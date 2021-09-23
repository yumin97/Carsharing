package com.example.carsharingtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.carsharingtest.History;
import com.example.carsharingtest.HistoryListAdpater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView list;
    private HistoryListAdpater adapter;
    private List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();

        list = (ListView) findViewById(R.id.historyList);
        historyList = new ArrayList<History>();

        adapter = new HistoryListAdpater(getApplicationContext(), historyList);
        list.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("HistoryList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String reservationID, reservation_startDt, reservation_endDt, reservation_carModel;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                reservationID = object.getString("reservationID");
                reservation_startDt = object.getString("reservation_startDt");
                reservation_endDt = object.getString("reservation_endDt");
                reservation_carModel = object.getString("reservation_carModel");
                History history = new History(reservationID, reservation_startDt, reservation_endDt, reservation_carModel);
                historyList.add(history);
                count++;
            }
            if(count == 0){
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                dialog = builder.setMessage("조회된 예약이 없습니다.")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}