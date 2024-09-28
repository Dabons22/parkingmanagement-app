package com.parkingmanagement.demo.repository;

import com.parkingmanagement.demo.adapters.ParkingRecordAdapter;
import com.parkingmanagement.demo.entities.ParkingRecords;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParkingRecordRepo implements ParkingRecordAdapter {

    private final ParkingRecordRepository parkingRecordRepository;

    @Override
    public ParkingRecords save(ParkingRecords record) {
        return parkingRecordRepository.save(record);
    }

    @Override
    public List<ParkingRecords> findByLotId(String lotId) {
        return parkingRecordRepository.findByLotId(lotId);
    }

    @Override
    public List<ParkingRecords> findByLicensePlate(String licensePlate) {
        return parkingRecordRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public void delete(ParkingRecords record) {
        parkingRecordRepository.delete(record);
    }

    @Override
    public List<ParkingRecords> findAll() {
        return parkingRecordRepository.findAll();
    }

}

