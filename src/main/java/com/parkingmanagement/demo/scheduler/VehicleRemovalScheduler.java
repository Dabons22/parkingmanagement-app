package com.parkingmanagement.demo.scheduler;

import com.parkingmanagement.demo.entities.ParkingEntity;
import com.parkingmanagement.demo.entities.ParkingRecords;
import com.parkingmanagement.demo.repository.ParkingLotRepo;
import com.parkingmanagement.demo.repository.ParkingRecordRepo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class VehicleRemovalScheduler {

    private ParkingRecordRepo parkingRecordRepository;

    private ParkingLotRepo parkingLotRepository;

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void removeStaleVehicles() {
        List<ParkingRecords> staleRecords = parkingRecordRepository.findAll().stream()
                .filter(record -> record.getCheckInTime().isBefore(LocalDateTime.now().minusMinutes(15))).toList();

        for (ParkingRecords record : staleRecords) {
            parkingRecordRepository.delete(record);
            ParkingEntity lot = parkingLotRepository.findById(record.getLotId()).orElse(null);
            if (!Objects.nonNull(lot)) {
                lot.setOccupiedSpaces(lot.getOccupiedSpaces() - 1);
                parkingLotRepository.save(lot);
            }
        }
    }
}
