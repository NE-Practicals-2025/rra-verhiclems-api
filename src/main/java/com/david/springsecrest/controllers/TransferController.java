package com.david.springsecrest.controllers;

import com.david.springsecrest.enums.EPlateAvailability;
import com.david.springsecrest.exceptions.BadRequestException;
import com.david.springsecrest.exceptions.ResourceNotFoundException;
import com.david.springsecrest.helpers.Constants;
import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.Plate;
import com.david.springsecrest.models.Vehicle;
import com.david.springsecrest.payload.request.RegisterOwnerDTO;
import com.david.springsecrest.payload.request.RegisterPlateNumberDTO;
import com.david.springsecrest.payload.request.RegisterVehicleDTO;
import com.david.springsecrest.payload.request.UpdateOwnerDTO;
import com.david.springsecrest.payload.response.ApiResponse;
import com.david.springsecrest.repositories.IPlateRepository;
import com.david.springsecrest.services.IOwnerService;
import com.david.springsecrest.services.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
    private final IOwnerService ownerService;
    private final IVehicleService vehicleService;
    private final IPlateRepository plateRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public VehicleController(IOwnerService ownerService, IVehicleService vehicleService, IPlateRepository plateRepository) {
        this.ownerService = ownerService;
        this.vehicleService = vehicleService;
        this.plateRepository = plateRepository;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse> getAllVehicles(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success("Vehicles fetched successfully", vehicleService.getAll(pageable)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Vehicle fetched successfully", this.vehicleService.getById(id)));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterVehicleDTO dto) {

        Owner owner = this.ownerService.getById(dto.getOwnerId());
        Plate plate = this.plateRepository.findById(dto.getPlateNumberId()).orElseThrow(()-> new ResourceNotFoundException("Plate", "id", dto.getPlateNumberId().toString()));
        if(plate.getStatus() == EPlateAvailability.IN_USE){
            throw new BadRequestException("Plate with id "+dto.getPlateNumberId()+" already taken");
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(owner);
        vehicle.setModel(dto.getModel());
        vehicle.setPlate(plate);
        vehicle.setChassisNumber(dto.getChassisNumber());
        vehicle.setManufacturer(dto.getManufacturer());
        vehicle.setManufactureYear(dto.getManufactureYear());
        vehicle.setPrice(dto.getPrice());
        Vehicle entity = this.vehicleService.create(vehicle);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(ApiResponse.success("Vehicle registered successfully", entity));
    }
}
