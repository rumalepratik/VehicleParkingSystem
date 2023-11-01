package com.rumalepratik.carpark;


import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleParkServiceTest {

    @MockBean
    private VehicleParkRepository vehicleParkRepository;

    @Autowired
    private VehicleParkService vehicleParkService;

    @Test
    public void givenVehile_whenRegisterVehicle_thenParkSuccessful(){
        //Test Data
        VehicleModel vehicle = new VehicleModel("ABC 123", "Pratik");

        Mockito.when(vehicleParkRepository.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(vehicleParkRepository.findAllByExitTime()).thenReturn(0L);
        Mockito.when(vehicleParkRepository.save(any())).thenReturn(new Vehicle());
        String returnValue = vehicleParkService.registerNewCar(vehicle);
        Assertions.assertEquals("You can park your vehicle.",returnValue);
    }

    @Test
    public void givenVehile_whenRegisterVehicle_ParkingFull_thenParkUnsuccessful(){
        //Test Data
        VehicleModel vehicle = new VehicleModel("ABC 123", "Pratik");
        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            vehicleList.add(new Vehicle());
        }
        Mockito.when(vehicleParkRepository.findAll()).thenReturn(vehicleList);
        Mockito.when(vehicleParkRepository.findAllByExitTime()).thenReturn(0L);
        Mockito.when(vehicleParkRepository.save(any())).thenReturn(new Vehicle());
        String returnValue = vehicleParkService.registerNewCar(vehicle);
        Assertions.assertEquals("No Vehicle Parking Space Available.",returnValue);
    }

    @Test
    public void givenVehile_whenExitVehicle_thenExitSuccessful(){
        //Test Data
        VehicleModel vehicle = new VehicleModel("ABC 123", "Pratik");
        Mockito.when(vehicleParkRepository.findById(1L)).thenReturn(Optional.of(new Vehicle("ABC123", "Pratik", LocalDateTime.now(), null)));
        Mockito.when(vehicleParkRepository.save(any())).thenReturn(new Vehicle());
        String returnValue = vehicleParkService.exitCar(1L);
        Assertions.assertEquals("Thank for parking with us, your total fare is £2.",returnValue);
    }

    @Test
    public void givenVehile_whenExitVehicle_EntryError_thenExitUnsuccessful(){
        //Test Data
        VehicleModel vehicle = new VehicleModel("ABC 123", "Pratik");
        Mockito.when(vehicleParkRepository.findById(1L)).thenReturn(Optional.of(new Vehicle()));
        Mockito.when(vehicleParkRepository.save(any())).thenReturn(new Vehicle());
        String returnValue = vehicleParkService.exitCar(1L);
        Assertions.assertEquals("Issue with fetching details, please re-register." ,returnValue);
    }

}
