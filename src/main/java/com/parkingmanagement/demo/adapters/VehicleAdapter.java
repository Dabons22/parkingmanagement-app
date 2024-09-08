package com.parkingmanagement.demo.adapters;

import com.parkingmanagement.demo.entities.VehicleEntity;

import java.util.Optional;

public interface VehicleAdapter {
    VehicleEntity save(VehicleEntity vehicle);
    Optional<VehicleEntity> findById(String id);
}
