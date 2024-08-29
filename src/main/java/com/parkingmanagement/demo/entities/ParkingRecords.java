package com.parkingmanagement.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "parking_records") // Table name for Flyway migration
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;
    private String lotId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
