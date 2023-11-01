package com.rumalepratik.carpark;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * VehicleParkService Used to Register a New Car and Calculate Fare While Car Exiting.
 */
@Service
public class VehicleParkService {
    private static final int MAX_CAR_PARK_SPACE = 100;
    private static final int HOURLY_RATE_IN_GBP = 2;

    private final VehicleParkRepository vehicleParkRepository;

    @Autowired
    public VehicleParkService(VehicleParkRepository vehicleRepository) {
        this.vehicleParkRepository = vehicleRepository;
    }

    /**
     * Checks if Space is Available for Parking, at any given moment at max 100 Cars should be Parked, updates Entry Time.
     * @param vehicle New Vehicle Entering the Parking Space
     * @return Error Message / Success Message
     */
    @Transactional
    public synchronized String registerNewCar(VehicleModel vehicle) {
        try {
            if (((vehicleParkRepository.findAll().size() - (int) vehicleParkRepository.findAllByExitTime()) < MAX_CAR_PARK_SPACE)) {
                Vehicle vehicleEntity = VehicleModel.toVehicleEntity(vehicle);
                vehicleParkRepository.save(vehicleEntity);
                return "You can park your vehicle.";
            } else {
                throw new IllegalStateException("No Vehicle Parking Space Available.");
            }
        } catch (IllegalStateException e) {
            return e.getLocalizedMessage();
        }
    }

    /**
     * Checks if Car was already registered Parking, updates Exit Time, and Calculates Fare based on £2/hour.
     * @param spaceAllocationId Unique Allocation ID, at any given moment at max 100 Cars should be Parked.
     * @return Error Message/ Fare Message
     */
    @Transactional
    public synchronized String exitCar(Long spaceAllocationId) {
        try {
            Vehicle vehicle = vehicleParkRepository.findById(spaceAllocationId).orElseThrow(() -> new IllegalStateException(
                    "Space Allocation does not exists. Please register the car first."));
            if ((vehicle != null) && (vehicle.getEntryTime() != null) && (vehicle.getExitTime() == null)) {
                vehicle.setExitTime(LocalDateTime.now());
                vehicleParkRepository.save(vehicle);
            } else {
                throw new IllegalStateException("Issue with fetching details, please re-register.");
            }
            return "Thank for parking with us, your total fare is £" + calculateFare(Objects.requireNonNull(vehicle))+ ".";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    private int calculateFare(Vehicle vehicle) {
        double milis = ChronoUnit.MILLIS.between(vehicle.getEntryTime(), vehicle.getExitTime());
        double hours = ((milis / (1000 * 60 * 60)) % 24);
        if(hours == 0) return 2;
        return (int) Math.ceil(hours) * HOURLY_RATE_IN_GBP;
    }

    /**
     * Dummy Data Insertion Method, To Be Removed Before Committing to Production.
     */
    public void addRecords() {
        for (int i = 0; i < 100; i++) {
            if (((vehicleParkRepository.findAll().size() - (int) vehicleParkRepository.findAllByExitTime()) < MAX_CAR_PARK_SPACE)) {
                vehicleParkRepository.save(new Vehicle("ABC 123", "Dummy " + i, LocalDateTime.now(), null));
            } else {
                throw new IllegalStateException("No CarSpace Available.");
            }
        }
    }

}
