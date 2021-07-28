package com.example.carsharingtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HistoryListAdpater extends BaseAdapter {

    private Context context;
    private List<History> historyList;

    public HistoryListAdpater(Context context, List<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int i) {
        return historyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.history, null);
        TextView reservationID = (TextView) v.findViewById(R.id.reserviatonID);
        TextView reservation_startDt = (TextView) v.findViewById(R.id.reservation_startDt);
        TextView reservation_endDt = (TextView) v.findViewById(R.id.reservation_endDt);
        TextView reserviaton_carModel = (TextView) v.findViewById(R.id.reservation_carModel);

        reservationID.setText(historyList.get(i).getReservationID());
        reservation_startDt.setText(historyList.get(i).getReservation_startDt());
        reservation_endDt.setText(historyList.get(i).getReservation_endDt());
        reserviaton_carModel.setText(historyList.get(i).getReservation_carModel());

        v.setTag(historyList.get(i).getReservationID());

        return v;
    }
}
