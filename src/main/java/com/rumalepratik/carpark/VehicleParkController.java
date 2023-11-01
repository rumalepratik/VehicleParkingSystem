package com.rumalepratik.carpark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Class with a main Put and Post Request.
 */
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleParkController {

    private final VehicleParkService vehicleParkService;

    @Autowired
    public VehicleParkController(VehicleParkService vehicleParkService) {
        this.vehicleParkService = vehicleParkService;
    }

    /**
     * To Be Removed Before Committing the code for Production.
     */
    @GetMapping("/addDummyRecords")
    public void addRecords() {
        vehicleParkService.addRecords();
    }

    @PostMapping("/registerNewVehicle")
    public ResponseEntity<String> registerNewCar(@RequestBody VehicleModel vehicle) {
        return new ResponseEntity<>(vehicleParkService.registerNewCar(vehicle), HttpStatus.OK);
    }

    @PutMapping(path = "/exit/{spaceAllocationId}")
    public ResponseEntity<String> exitCarPark(@PathVariable("spaceAllocationId") Long spaceAllocationId) {
        return new ResponseEntity<>(vehicleParkService.exitCar(spaceAllocationId), HttpStatus.OK);
    }

}
