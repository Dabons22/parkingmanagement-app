//package com.parkingmanagement.demo.scheduler;
//
//import com.parkingmanagement.demo.entities.ParkingEntity;
//import com.parkingmanagement.demo.entities.ParkingRecords;
//import com.parkingmanagement.demo.repository.ParkingLotRepository;
//import com.parkingmanagement.demo.repository.ParkingRecordRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class VehicleRemovalSchedulerTest {
//
//    @InjectMocks
//    private VehicleRemovalScheduler vehicleRemovalScheduler;
//
//    @Mock
//    private ParkingRecordRepository parkingRecordRepository;
//
//    @Mock
//    private ParkingLotRepository parkingLotRepository;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void givenNoStaleRecords_whenRemoveStaleVehicles_thenNothingIsRemoved() {
//        // Arrange
//        when(parkingRecordRepository.findAll()).thenReturn(Arrays.asList());
//
//        // Act
//        vehicleRemovalScheduler.removeStaleVehicles();
//
//        // Assert
//        verify(parkingRecordRepository, times(0)).delete(any());
//        verify(parkingLotRepository, times(0)).findById(anyString());
//        verify(parkingLotRepository, times(0)).save(any(ParkingEntity.class));
//    }
//
//    @Test
//    void givenStaleRecords_whenRemoveStaleVehicles_thenRecordsAreRemovedAndLotIsUpdated() {
//        // Arrange
//        LocalDateTime now = LocalDateTime.now();
//        ParkingRecords staleRecord = new ParkingRecords();
//        staleRecord.setId(1L);
//        staleRecord.setLicensePlate("ABC123");
//        staleRecord.setLotId("P001");
//        staleRecord.setCheckInTime(now.minusMinutes(16));
//        staleRecord.setCheckOutTime(null); // Not checked out, so it's stale
//
//        ParkingEntity parkingLot = new ParkingEntity();
//        parkingLot.setLotId("P001");
//        parkingLot.setLocation("Downtown");
//        parkingLot.setCapacity(100);
//        parkingLot.setOccupiedSpaces(1);
//        parkingLot.setCostPerMinute(0.05);
//
//        when(parkingRecordRepository.findAll()).thenReturn(Collections.singletonList(staleRecord));
//        when(parkingLotRepository.findById("P001")).thenReturn(Optional.of(parkingLot));
//
//        // Act
//        vehicleRemovalScheduler.removeStaleVehicles();
//
//        // Assert
//        verify(parkingRecordRepository, times(1)).delete(staleRecord);
//        verify(parkingLotRepository, times(1)).save(parkingLot);
//        assertEquals(0, parkingLot.getOccupiedSpaces(), "The occupied spaces should be updated.");
//    }
//
//    @Test
//    void testRemoveStaleVehicles_withStaleRecord_noLotFound() {
//        // Arrange
//        LocalDateTime now = LocalDateTime.now();
//        ParkingRecords staleRecord = new ParkingRecords();
//        staleRecord.setCheckInTime(now.minusMinutes(20));
//        staleRecord.setLotId("lot123");
//
//        when(parkingRecordRepository.findAll()).thenReturn(Arrays.asList(staleRecord));
//        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.empty());
//
//        // Act
//        vehicleRemovalScheduler.removeStaleVehicles();
//
//        // Assert
//        verify(parkingRecordRepository, times(1)).delete(staleRecord);
//        verify(parkingLotRepository, times(1)).findById("lot123");
//        verify(parkingLotRepository, times(0)).save(any(ParkingEntity.class));
//    }
//}
