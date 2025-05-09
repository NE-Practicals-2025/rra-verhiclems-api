package com.david.springsecrest.services.serviceImpls;


import com.david.springsecrest.enums.ERole;
import com.david.springsecrest.exceptions.BadRequestException;
import com.david.springsecrest.exceptions.ResourceNotFoundException;
import com.david.springsecrest.helpers.Utility;
import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.Plate;
import com.david.springsecrest.models.User;
import com.david.springsecrest.payload.request.RegisterPlateNumberDTO;
import com.david.springsecrest.payload.request.UpdateOwnerDTO;
import com.david.springsecrest.payload.request.UpdateUserDTO;
import com.david.springsecrest.repositories.IOwnerRepository;
import com.david.springsecrest.repositories.IPlateRepository;
import com.david.springsecrest.repositories.IUserRepository;
import com.david.springsecrest.services.IOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements IOwnerService {

    private final IOwnerRepository ownerRepository;
    private final IPlateRepository plateRepository;

    @Override
    public Page<Owner> getAll(Pageable pageable) {
        return this.ownerRepository.findAll(pageable);
    }

    @Override
    public Owner getById(UUID id) {
        return this.ownerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id.toString()));
    }

    @Override
    public Owner create(Owner owner) {
        try {
            Optional<Owner> userOptional = this.ownerRepository.findByNationalId(owner.getNationalId());
            if (userOptional.isPresent())
                throw new BadRequestException(String.format("Owner with nationalId '%s' already exists", owner.getNationalId()));
            return this.ownerRepository.save(owner);
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = Utility.getConstraintViolationMessage(ex, owner);
            throw new BadRequestException(errorMessage, ex);
        }
    }

    @Override
    public Plate registerPlateNumber(UUID ownerId, RegisterPlateNumberDTO registerPlateNumberDTO) {
        try {
            Owner owner = this.ownerRepository.findById(ownerId).orElseThrow(()-> new ResourceNotFoundException("Owner", "id", ownerId.toString()));
            Optional<Plate> plateNumber = this.plateRepository.findByPlateNumber(registerPlateNumberDTO.getPlateNumber());
            if (plateNumber.isPresent())
                throw new BadRequestException(String.format("Plate number '%s' already exists", registerPlateNumberDTO.getPlateNumber()));
            Plate plate = new Plate();
            plate.setPlateNumber(registerPlateNumberDTO.getPlateNumber());
            plate.setIssuedDate(registerPlateNumberDTO.getIssuedDate());
            plate.setOwner(owner);
            return this.plateRepository.save(plate);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("An error occurred!", ex);
        }
    }

    @Override
    public List<Plate> getPlateNumbersByOwnerId(UUID ownerId) {
        try {
            Owner owner = this.ownerRepository.findById(ownerId).orElseThrow(()-> new ResourceNotFoundException("Owner", "id", ownerId.toString()));
            return this.plateRepository.getPlateNumbersByOwnerId(ownerId);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("An error occurred!", ex);
        }
    }

    @Override
    public Owner save(Owner owner) {
        try {
            return this.ownerRepository.save(owner);
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = Utility.getConstraintViolationMessage(ex, owner);
            throw new BadRequestException(errorMessage, ex);
        }
    }

//    @Override
//    public User update(UUID id, UpdateUserDTO dto) {
//        User entity = this.userRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("User", "id", id.toString()));
//
//        Optional<User> userOptional = this.userRepository.findByEmail(dto.getEmail());
//        if (userOptional.isPresent() && (userOptional.get().getId() != entity.getId()))
//            throw new BadRequestException(String.format("User with email '%s' already exists", entity.getEmail()));
//
//        entity.setEmail(dto.getEmail());
//        entity.setFirstName(dto.getFirstName());
//        entity.setLastName(dto.getLastName());
//        entity.setTelephone(dto.getTelephone());
//        entity.setGender(dto.getGender());
//
//        return this.userRepository.save(entity);
//    }

    @Override
    public Owner update(UUID id, UpdateOwnerDTO dto) {
        Owner owner = this.ownerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner", "id", id.toString()));
        owner.setNames(dto.getNames());
        owner.setPhoneNumber(dto.getTelephone());
        owner.setNationalId(dto.getNationalId());
        owner.setAddress(dto.getAddress());
        return this.ownerRepository.save(owner);
    }

    @Override
    public boolean delete(UUID id) {
        this.ownerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Owner", "id", id));

        this.ownerRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<Owner> searchOwner(Pageable pageable, String searchKey) {
        return this.ownerRepository.searchOwner(pageable, searchKey);
    }

    @Override
    public Owner getByNationalId(String nationalId) {
        return this.ownerRepository.findByNationalId(nationalId).orElseThrow(
                () -> new ResourceNotFoundException("Owner", "id", nationalId));
    }

}
