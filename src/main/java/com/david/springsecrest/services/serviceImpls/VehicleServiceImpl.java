package com.david.springsecrest.services.serviceImpls;


import com.david.springsecrest.exceptions.BadRequestException;
import com.david.springsecrest.exceptions.ResourceNotFoundException;
import com.david.springsecrest.models.Vehicle;
import com.david.springsecrest.repositories.IVehicleRepository;
import com.david.springsecrest.services.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;

    @Override
    public Page<Vehicle> getAll(Pageable pageable) {
        return this.vehicleRepository.findAll(pageable);
    }

    @Override
    public Vehicle getById(UUID id) {
        return this.vehicleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", id.toString()));
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        try {
            return this.vehicleRepository.save(vehicle);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("Error Occurred", ex);
        }
    }
    @Override
    public Vehicle save(Vehicle vehicle) {
        try {
            return this.vehicleRepository.save(vehicle);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("Error Occurred", ex);
        }
    }
}
