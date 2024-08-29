package com.parkingmanagement.demo.controller;


import com.parkingmanagement.demo.entities.ParkingEntity;
import com.parkingmanagement.demo.entities.VehicleEntity;
import com.parkingmanagement.demo.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/lot")
    public ParkingEntity registerParkingLot(@RequestBody ParkingEntity lot) {
        return parkingService.registerParkingLot(lot);
    }

    @PostMapping("/vehicle")
    public VehicleEntity registerVehicle(@RequestBody VehicleEntity vehicle) {
        return parkingService.registerVehicle(vehicle);
    }

    @PostMapping("/checkin")
    public String checkInVehicle(@RequestParam String licensePlate, @RequestParam String lotId) {
        return parkingService.checkInVehicle(licensePlate, lotId);
    }

    @PostMapping("/checkout")
    public String checkOutVehicle(@RequestParam String licensePlate) {
        return parkingService.checkOutVehicle(licensePlate);
    }

    @GetMapping("/lot/{lotId}")
    public ParkingEntity getParkingLotInfo(@PathVariable String lotId) {
        return parkingService.getParkingLotInfo(lotId);
    }

    @GetMapping("/lot/{lotId}/vehicles")
    public List<VehicleEntity> getVehiclesInLot(@PathVariable String lotId) {
        return parkingService.getVehiclesInLot(lotId);
    }
}
