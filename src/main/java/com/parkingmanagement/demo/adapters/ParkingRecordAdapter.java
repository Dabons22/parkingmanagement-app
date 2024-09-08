package com.parkingmanagement.demo.adapters;

import com.parkingmanagement.demo.entities.ParkingRecords;

import java.util.List;

public interface ParkingRecordAdapter {

    ParkingRecords save(ParkingRecords record);
    List<ParkingRecords> findByLotId(String lotId);
    List<ParkingRecords> findByLicensePlate(String licensePlate);
    List<ParkingRecords> findAll();
    void delete(ParkingRecords record);

}
