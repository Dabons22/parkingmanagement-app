package com.parkingmanagement.demo.repository;

import com.parkingmanagement.demo.entities.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingEntity, String> {
}
