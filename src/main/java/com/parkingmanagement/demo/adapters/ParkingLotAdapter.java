package com.parkingmanagement.demo.adapters;

import com.parkingmanagement.demo.entities.ParkingEntity;
import com.parkingmanagement.demo.entities.ParkingRecords;
import com.parkingmanagement.demo.repository.ParkingRecordRepo;

import java.util.Optional;

public interface ParkingLotAdapter {
    ParkingEntity save(ParkingEntity records);
    Optional<ParkingEntity> findById(String lotId);

}
