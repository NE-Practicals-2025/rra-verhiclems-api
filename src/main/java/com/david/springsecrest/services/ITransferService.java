package com.david.springsecrest.services;

import com.david.springsecrest.models.Transfer;
import com.david.springsecrest.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface ITransferService {

    Page<Transfer> getAll(Pageable pageable);

    Transfer getById(UUID id);

    Transfer create(Transfer transfer);

    Transfer save(Transfer transfer);
}
