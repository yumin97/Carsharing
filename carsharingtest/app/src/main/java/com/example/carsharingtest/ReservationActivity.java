package com.example.carsharingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReservationActivity extends AppCompatActivity {

    private TextView tv_userid, tv_carid;
    private EditText et_startdate, et_enddate;
    private Button btn_reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        String carID = intent.getStringExtra("carID");

        // 아이디 값 찾아주기
        tv_userid = findViewById(R.id.tv_userid);
        tv_carid = findViewById(R.id.tv_carid);
        et_startdate = findViewById(R.id.et_startdate);
        et_enddate = findViewById(R.id.et_enddate);

        tv_userid.setText(MainActivity.userID);
        tv_carid.setText(carID);


        // 예약 버튼 클릭 시 수행
        btn_reservation = findViewById(R.id.btn_reservation);
        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String startdate = et_startdate.getText().toString();
                String enddate = et_enddate.getText().toString();

//                int userAge = Integer.parseInt(et_age.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 차량 예약에 성공한 경우
                                Toast.makeText(getApplicationContext(),"차량 예약에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            } else { // 차량 예약에 실패한 경우
                                Toast.makeText(getApplicationContext(),"차량 예약에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                ReservationRequest reservationRequest = new ReservationRequest(MainActivity.userID,carID,startdate,enddate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReservationActivity.this);
                queue.add(reservationRequest);
            }
        });
    }
}