package com.example.carsharingtest.car;

public class Car {

    String carID;
    String carNumber;
    String carModel;

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Car(String carID, String carNumber, String carModel) {
        this.carID = carID;
        this.carNumber = carNumber;
        this.carModel = carModel;
    }
}
