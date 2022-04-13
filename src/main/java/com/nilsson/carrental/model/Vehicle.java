package com.nilsson.carrental.model;

import javax.persistence.*;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private String name, model;
    private double rentalFee;

    public Vehicle() {
    }

    public Vehicle(Long vehicleId, String name, String model, double rentalFee) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.model = model;
        this.rentalFee = rentalFee;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }
}
