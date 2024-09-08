package com.parkingmanagement.demo.service;

import com.parkingmanagement.demo.entities.ParkingEntity;
import com.parkingmanagement.demo.entities.ParkingRecords;
import com.parkingmanagement.demo.entities.VehicleEntity;
import com.parkingmanagement.demo.repository.ParkingLotRepo;
import com.parkingmanagement.demo.repository.ParkingRecordRepo;
import com.parkingmanagement.demo.repository.VehicleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParkingServiceTest {

    @Mock
    private ParkingLotRepo parkingLotRepository;

    @Mock
    private VehicleRepo vehicleRepository;

    @Mock
    private ParkingRecordRepo parkingRecordRepo;

    @InjectMocks
    private ParkingService parkingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidVehicleAndLot_whenCheckInVehicle_thenShouldCheckInVehicle() {
        // Given
        ParkingEntity lot = new ParkingEntity();
        lot.setLotId("lot1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(0);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setLicensePlate("ABC123");

        when(parkingLotRepository.findById("lot1")).thenReturn(Optional.of(lot));
        when(vehicleRepository.findById("ABC123")).thenReturn(Optional.of(vehicle));
        when(parkingRecordRepo.findByLicensePlate("ABC123")).thenReturn(List.of());

        // When
        String result = parkingService.checkInVehicle("ABC123", "lot1");

        // Then
        assertEquals("Vehicle checked in successfully", result);
        verify(parkingLotRepository, times(1)).save(lot);
        verify(parkingRecordRepo, times(1)).save(any(ParkingRecords.class));
    }

    @Test
    void givenCheckedInVehicle_whenCheckOutVehicle_thenShouldCalculateCost() {
        // Given
        ParkingRecords record = new ParkingRecords();
        record.setLicensePlate("ABC123");
        record.setLotId("lot1");
        record.setCheckInTime(LocalDateTime.now().minusMinutes(10));
        record.setCheckOutTime(null);

        ParkingEntity lot = new ParkingEntity();
        lot.setLotId("lot1");
        lot.setCostPerMinute(0.05);
        lot.setOccupiedSpaces(1);

        when(parkingRecordRepo.findByLicensePlate("ABC123")).thenReturn(List.of(record));
        when(parkingLotRepository.findById("lot1")).thenReturn(Optional.of(lot));

        // When
        String result = parkingService.checkOutVehicle("ABC123");

        // Then
        assertTrue(result.startsWith("Vehicle checked out successfully"));
        verify(parkingLotRepository, times(1)).save(lot);
        verify(parkingRecordRepo, times(1)).delete(record);
    }
}
