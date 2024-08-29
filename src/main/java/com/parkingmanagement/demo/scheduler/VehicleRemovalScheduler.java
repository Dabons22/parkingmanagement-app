package com.parkingmanagement.demo.scheduler;

import com.parkingmanagement.demo.entities.ParkingEntity;
import com.parkingmanagement.demo.entities.ParkingRecords;
import com.parkingmanagement.demo.repository.ParkingLotRepository;
import com.parkingmanagement.demo.repository.ParkingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class VehicleRemovalScheduler {
    @Autowired
    private ParkingRecordRepository parkingRecordRepository;
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void removeStaleVehicles() {
        LocalDateTime now = LocalDateTime.now();
        List<ParkingRecords> staleRecords = parkingRecordRepository.findAll().stream()
                .filter(record -> record.getCheckInTime().isBefore(now.minusMinutes(15)))
                .collect(Collectors.toList());

        for (ParkingRecords record : staleRecords) {
            parkingRecordRepository.delete(record);
            ParkingEntity lot = parkingLotRepository.findById(record.getLotId()).orElse(null);
            if (lot != null) {
                lot.setOccupiedSpaces(lot.getOccupiedSpaces() - 1);
                parkingLotRepository.save(lot);
            }
        }
    }
}
