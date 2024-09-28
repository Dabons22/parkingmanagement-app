package com.parkingmanagement.demo.service;

import com.parkingmanagement.demo.entities.ParkingEntity;
import com.parkingmanagement.demo.entities.ParkingRecords;
import com.parkingmanagement.demo.entities.VehicleEntity;
import com.parkingmanagement.demo.repository.ParkingLotRepo;
import com.parkingmanagement.demo.repository.ParkingRecordRepo;
import com.parkingmanagement.demo.repository.VehicleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class ParkingService {
    @Autowired
    private final VehicleRepo vehicleRepository;

    private final ParkingLotRepo parkingLotRepository;

    private final ParkingRecordRepo parkingRecordRepository;

    private static final Logger log = LoggerFactory.getLogger(ParkingService.class);


    public ParkingEntity registerParkingLot(ParkingEntity lot) {
        log.info("Registering parking lot: {}", lot);

        return parkingLotRepository.save(lot);
    }

    public VehicleEntity registerVehicle(VehicleEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public String checkInVehicle(String licensePlate, String lotId) {
        Optional<ParkingEntity> lotOpt = parkingLotRepository.findById(lotId);
        Optional<VehicleEntity> vehicleOpt = vehicleRepository.findById(licensePlate);

        if (lotOpt.isEmpty() || vehicleOpt.isEmpty()) {
            return "Parking lot or vehicle not found";
        }

        ParkingEntity lot = lotOpt.get();
        VehicleEntity vehicle = vehicleOpt.get();

        if (lot.getOccupiedSpaces() >= lot.getCapacity()) {
            return "Parking lot is full";
        }

        List<ParkingRecords> existingRecords = parkingRecordRepository.findByLicensePlate(licensePlate);
        if (!existingRecords.isEmpty()) {
            return "Vehicle is already parked";
        }

        ParkingRecords record = new ParkingRecords();
        record.setLicensePlate(licensePlate);
        record.setLotId(lotId);
        record.setCheckInTime(LocalDateTime.now());

        parkingRecordRepository.save(record);
        lot.setOccupiedSpaces(lot.getOccupiedSpaces() + 1);
        parkingLotRepository.save(lot);

        return "Vehicle checked in successfully";
    }

    public String checkOutVehicle(String licensePlate) {
        List<ParkingRecords> records = parkingRecordRepository.findByLicensePlate(licensePlate);
        if (records.isEmpty()) {
            return "No parking record found for vehicle";
        }

        ParkingRecords record = records.get(0); // Assuming one record per vehicle
        ParkingEntity lot = parkingLotRepository.findById(record.getLotId()).orElse(null);

        if (lot == null) {
            return "Parking lot not found";
        }

        record.setCheckOutTime(LocalDateTime.now());
        parkingRecordRepository.save(record);

        long minutesParked = Duration.between(record.getCheckInTime(), record.getCheckOutTime()).toMinutes();
        double cost = minutesParked * lot.getCostPerMinute();

        lot.setOccupiedSpaces(lot.getOccupiedSpaces() - 1);
        parkingLotRepository.save(lot);

        parkingRecordRepository.delete(record);

        return String.format("Vehicle checked out successfully. Parking cost: %.2f", cost);
    }

    public ParkingEntity getParkingLotInfo(String lotId) {
        return parkingLotRepository.findById(lotId).orElse(null);
    }

    public List<VehicleEntity> getVehiclesInLot(String lotId) {
        List<ParkingRecords> records = parkingRecordRepository.findByLotId(lotId);
        return records.stream()
                .map(record -> vehicleRepository.findById(record.getLicensePlate()).orElse(null))
                .toList();
    }

    public void removeStaleVehicles() {
        LocalDateTime now = LocalDateTime.now();
        List<ParkingRecords> staleRecords = parkingRecordRepository.findAll().stream()
                .filter(record -> record.getCheckInTime().isBefore(now.minusMinutes(15)) && record.getCheckOutTime() == null)
                .toList();


        for (ParkingRecords record : staleRecords) {
            checkOutVehicle(record.getLicensePlate());
        }
    }
}
