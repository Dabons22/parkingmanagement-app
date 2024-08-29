package com.parkingmanagement.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class VehicleEntity {
    @Id
    @Column(name = "license_plate")
    private String licensePlate;

    private String type; // Car, Motorcycle, Truck

    @Column(name = "owner_name")
    private String ownerName;
}
