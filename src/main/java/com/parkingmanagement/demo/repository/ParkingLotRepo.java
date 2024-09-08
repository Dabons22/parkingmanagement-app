package com.parkingmanagement.demo.repository;

import com.parkingmanagement.demo.adapters.ParkingLotAdapter;
import com.parkingmanagement.demo.entities.ParkingEntity;
import com.parkingmanagement.demo.entities.ParkingRecords;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class ParkingLotRepo implements ParkingLotAdapter {
   private final ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingEntity save(ParkingEntity records) {
        return parkingLotRepository.save(records);
    }

    @Override
    public Optional<ParkingEntity> findById(String lotId) {
        return parkingLotRepository.findById(lotId);
    }
}
