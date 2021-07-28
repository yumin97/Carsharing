package com.example.carsharingtest;

public class History {

    String reservationID;
    String reservation_startDt;
    String reservation_endDt;
    String reservation_carModel;

    public History(String reservationID, String reservation_startDt, String reservation_endDt, String reservation_carModel) {
        this.reservationID = reservationID;
        this.reservation_startDt = reservation_startDt;
        this.reservation_endDt = reservation_endDt;
        this.reservation_carModel = reservation_carModel;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getReservation_startDt() {
        return reservation_startDt;
    }

    public void setReservation_startDt(String reservation_startDt) {
        this.reservation_startDt = reservation_startDt;
    }

    public String getReservation_endDt() {
        return reservation_endDt;
    }

    public void setReservation_endDt(String reservation_endDt) {
        this.reservation_endDt = reservation_endDt;
    }

    public String getReservation_carModel() {
        return reservation_carModel;
    }

    public void setReservation_carModel(String reservation_carModel) {
        this.reservation_carModel = reservation_carModel;
    }
}
