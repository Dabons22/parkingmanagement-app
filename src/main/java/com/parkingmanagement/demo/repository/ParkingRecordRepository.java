package com.parkingmanagement.demo.repository;

import com.parkingmanagement.demo.entities.ParkingRecords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRecordRepository extends JpaRepository<ParkingRecords, Long> {
   List<ParkingRecords> findByLotId(String lotId);
   List<ParkingRecords> findByLicensePlate(String licensePlate);
   }
