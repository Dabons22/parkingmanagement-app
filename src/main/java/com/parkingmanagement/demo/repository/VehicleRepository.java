package com.parkingmanagement.demo.repository;

import com.parkingmanagement.demo.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository  extends JpaRepository<VehicleEntity, String >{
}
