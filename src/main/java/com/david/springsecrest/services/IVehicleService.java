package com.david.springsecrest.services;

import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.Plate;
import com.david.springsecrest.models.Vehicle;
import com.david.springsecrest.payload.request.RegisterPlateNumberDTO;
import com.david.springsecrest.payload.request.UpdateOwnerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface IVehicleService {

    Page<Vehicle> getAll(Pageable pageable);

    Vehicle getById(UUID id);

    Vehicle create(Vehicle vehicle);

    Vehicle save(Vehicle vehicle);
}
