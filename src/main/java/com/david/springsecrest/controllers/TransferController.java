package com.david.springsecrest.controllers;

import com.david.springsecrest.enums.EPlateAvailability;
import com.david.springsecrest.exceptions.BadRequestException;
import com.david.springsecrest.exceptions.ResourceNotFoundException;
import com.david.springsecrest.helpers.Constants;
import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.Plate;
import com.david.springsecrest.models.Transfer;
import com.david.springsecrest.models.Vehicle;
import com.david.springsecrest.payload.request.RegisterTransferDTO;
import com.david.springsecrest.payload.request.RegisterVehicleDTO;
import com.david.springsecrest.payload.response.ApiResponse;
import com.david.springsecrest.repositories.IPlateRepository;
import com.david.springsecrest.services.IOwnerService;
import com.david.springsecrest.services.ITransferService;
import com.david.springsecrest.services.IVehicleService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/transfer")
public class TransferController {
    private final IOwnerService ownerService;
    private final IVehicleService vehicleService;
    private final IPlateRepository plateRepository;
    private final ITransferService transferService;


    public TransferController(IOwnerService ownerService, IVehicleService vehicleService, IPlateRepository plateRepository, ITransferService transferService) {
        this.ownerService = ownerService;
        this.vehicleService = vehicleService;
        this.plateRepository = plateRepository;
        this.transferService = transferService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse> getAllTransfers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success("Transfers fetched successfully", transferService.getAll(pageable)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Transfer fetched successfully", this.transferService.getById(id)));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterTransferDTO dto) {

        Owner formerOwner = this.ownerService.getById(dto.getFormerOwnerId());
        Owner newOwner = this.ownerService.getById(dto.getNewOwnerId());
        Vehicle vehicle = this.vehicleService.getById(dto.getVehicleId());

        Transfer transfer = new Transfer();
        transfer.setNewOwner(newOwner);
        transfer.setFormerOwner(formerOwner);
        transfer.setPrice(dto.getPrice());
        transfer.setVehicle(vehicle);
//        Plate plate = this.plateRepository.findById(dto.getPlateNumberId()).orElseThrow(()-> new ResourceNotFoundException("Plate", "id", dto.getPlateNumberId().toString()));
//        if(plate.getStatus() == EPlateAvailability.IN_USE){
//            throw new BadRequestException("Plate with id "+dto.getPlateNumberId()+" already taken");
//        }
        Transfer entity = this.transferService.create(transfer);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(ApiResponse.success("Transfer registered successfully", entity));
    }
}
