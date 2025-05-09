package com.david.springsecrest.controllers;

import com.david.springsecrest.enums.ERole;
import com.david.springsecrest.exceptions.BadRequestException;
import com.david.springsecrest.helpers.Constants;
import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.Role;
import com.david.springsecrest.models.User;
import com.david.springsecrest.payload.request.CreateUserDTO;
import com.david.springsecrest.payload.request.RegisterOwnerDTO;
import com.david.springsecrest.payload.request.UpdateOwnerDTO;
import com.david.springsecrest.payload.request.UpdateUserDTO;
import com.david.springsecrest.payload.response.ApiResponse;
import com.david.springsecrest.repositories.IOwnerRepository;
import com.david.springsecrest.repositories.IRoleRepository;
import com.david.springsecrest.services.IOwnerService;
import com.david.springsecrest.services.IUserService;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {
    private final IOwnerService ownerService;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public OwnerController(IOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PutMapping(path = "/update/:id")
    public ResponseEntity<ApiResponse> update(@RequestParam UUID id, @RequestBody UpdateOwnerDTO dto) {
        Owner updated = this.ownerService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Owner updated successfully", updated));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse> getAllOwners(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success("Owner fetched successfully", ownerService.getAll(pageable)));
    }

    @GetMapping(path = "/search")
    public Page<Owner> searchUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "searchKey") String searchKey
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ownerService.searchOwner(pageable, searchKey);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Owner fetched successfully", this.ownerService.getById(id)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Owner deleted successfully", this.ownerService.delete(id)));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterOwnerDTO dto) {

        Owner owner = new Owner();

        owner.setNames(dto.getNames());
        owner.setAddress(dto.getAddress());
        owner.setNationalId(dto.getNationalId());
        owner.setPhoneNumber(dto.getTelephone());

        Owner entity = this.ownerService.create(owner);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(ApiResponse.success("Owner created successfully", entity));
    }
}
