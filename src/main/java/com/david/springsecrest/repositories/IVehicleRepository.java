package com.david.springsecrest.repositories;

import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, UUID> {

    Optional<Vehicle> findById(UUID vehicleId);

    Optional<Vehicle> findByChassisNumber(String chassisNumber);
}
