package com.david.springsecrest.services.serviceImpls;


import com.david.springsecrest.exceptions.BadRequestException;
import com.david.springsecrest.exceptions.ResourceNotFoundException;
import com.david.springsecrest.models.Transfer;
import com.david.springsecrest.models.Vehicle;
import com.david.springsecrest.repositories.ITransferRepository;
import com.david.springsecrest.repositories.IVehicleRepository;
import com.david.springsecrest.services.ITransferService;
import com.david.springsecrest.services.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.TreeMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements ITransferService {

    private final ITransferRepository transferRepository;

    @Override
    public Page<Transfer> getAll(Pageable pageable) {
        return this.transferRepository.findAll(pageable);
    }

    @Override
    public Transfer getById(UUID id) {
        return this.transferRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Transfer", "id", id.toString()));
    }

    @Override
    public Transfer create(Transfer transfer) {
        try {
            return this.transferRepository.save(transfer);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("Error Occurred", ex);
        }
    }
    @Override
    public Transfer save(Transfer transfer) {
        try {
            return this.transferRepository.save(transfer);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("Error Occurred", ex);
        }
    }
}
