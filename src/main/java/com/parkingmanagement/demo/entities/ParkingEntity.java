package com.parkingmanagement.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking_lots")
@Getter
@Setter
public class ParkingEntity {
    @Id
    @Column(name = "lot_id")
    private String lotId;

    private String location;
    private int capacity;

    @Column(name = "occupied_spaces")
    private int occupiedSpaces;

    @Column(name = "cost_per_minute")
    private double costPerMinute;

    @Override
    public String toString() {
        return String.format("ParkingEntity(lotId=%s, location=%s, capacity=%d, occupiedSpaces=%d, costPerMinute=%.2f)",
                lotId, location, capacity, occupiedSpaces, costPerMinute);
    }
}
