package com.rumalepratik.carpark;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *  DTO of the Vehicle with Car Registration Number, Owner Name, Entry Time and Exit Time.
 */
@Data
public class VehicleModel {

    private String carRegNum;
    private String ownerName;

    public VehicleModel(String carRegNum, String ownerName) {
        this.carRegNum = carRegNum;
        this.ownerName = ownerName;
    }

    public VehicleModel() {
    }

    public static Vehicle toVehicleEntity(VehicleModel car) {
        return new Vehicle(car.getCarRegNum(), car.getOwnerName(), LocalDateTime.now(), null);
    }
}
