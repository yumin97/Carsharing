package com.example.carsharingtest.car;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.carsharingtest.MainActivity;
import com.example.carsharingtest.R;
import com.example.carsharingtest.ReservationActivity;
import com.example.carsharingtest.User.ProfileActivity;

import java.util.List;

public class CarListAdapter extends BaseAdapter {
    private Context context;
    private List<Car> carList;

    public CarListAdapter(Context context, List<Car> carList){
        this.context = context;
        this.carList = carList;
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int i) {
        return carList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.car, null);
        TextView carID = (TextView) v.findViewById(R.id.carID);
        TextView carNumber = (TextView) v.findViewById(R.id.carNumber);
        TextView carModel = (TextView) v.findViewById(R.id.carModel);

        carID.setText(carList.get(i).getCarID());
        carNumber.setText(carList.get(i).getCarNumber());
        carModel.setText(carList.get(i).getCarModel());

        v.setTag(carList.get(i).getCarID());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent reservation = new Intent(v.getContext(),ReservationActivity.class);
                reservation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                reservation.putExtra("carID", carList.get(i).getCarID());
                v.getContext().startActivity(reservation);
            }
        });

        return v;
    }
}
