package com.parkingmanagement.demo.config;

import com.parkingmanagement.demo.repository.ParkingLotRepo;
import com.parkingmanagement.demo.repository.ParkingRecordRepo;
import com.parkingmanagement.demo.repository.VehicleRepo;
import com.parkingmanagement.demo.service.ParkingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParkingLotConfig {

    @Bean
    public ParkingService parkingService(VehicleRepo vehicleRepo, ParkingLotRepo parkingLotRepo, ParkingRecordRepo parkingRecordRepo){
        return new ParkingService(vehicleRepo,parkingLotRepo,parkingRecordRepo);
    }
}
