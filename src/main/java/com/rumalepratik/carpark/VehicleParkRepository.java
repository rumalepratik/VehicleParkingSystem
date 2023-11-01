package com.rumalepratik.carpark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleParkRepository extends JpaRepository<Vehicle, Long> {

    /**
     *
     * @return Count of Vehicles which have left the Parking Space, used for calculating total available space.
     */
    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.exitTime IS NOT NULL")
    long findAllByExitTime();

}
