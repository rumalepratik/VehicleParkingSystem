package com.rumalepratik.carpark;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity Class of the Vehicle with Car Registration Number, Owner Name, Entry Time and Exit Time.
 */
@Data
@Table
@Entity
public class Vehicle {

    @Id
    @SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_sequence")
    private long spaceAllocationId;

    private String carRegNum;
    private String ownerName;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Vehicle(long spaceAllocationId, String carRegNum, String ownerName, LocalDateTime entryTime, LocalDateTime exitTime) {
        this.spaceAllocationId = spaceAllocationId;
        this.carRegNum = carRegNum;
        this.ownerName = ownerName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public Vehicle() {
    }

    public Vehicle(String carRegNum, String ownerName, LocalDateTime entryTime, LocalDateTime exitTime) {
        this.carRegNum = carRegNum;
        this.ownerName = ownerName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + spaceAllocationId +
                ", carRegNum='" + carRegNum + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return spaceAllocationId == vehicle.spaceAllocationId && Objects.equals(carRegNum, vehicle.carRegNum) && Objects.equals(ownerName, vehicle.ownerName) && Objects.equals(entryTime, vehicle.entryTime) && Objects.equals(exitTime, vehicle.exitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spaceAllocationId, carRegNum, ownerName, entryTime, exitTime);
    }
}
