package com.parkingmanagement.demo.repository;

import com.parkingmanagement.demo.adapters.VehicleAdapter;
import com.parkingmanagement.demo.entities.VehicleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRepo implements VehicleAdapter {
    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleEntity save(VehicleEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<VehicleEntity> findById(String id) {
        return vehicleRepository.findById(id);
    }
}
