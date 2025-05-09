package com.david.springsecrest.services;

import com.david.springsecrest.models.Owner;
import com.david.springsecrest.payload.request.UpdateOwnerDTO;
import com.david.springsecrest.payload.request.UpdateUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface IOwnerService {

    Page<Owner> getAll(Pageable pageable);

    Owner getById(UUID id);

    Owner create(Owner owner);
    Owner save(Owner owner);

    Owner update(UUID id, UpdateOwnerDTO dto);

    boolean delete(UUID id);

    Page<Owner> searchOwner(Pageable pageable, String searchKey);

    Owner getByNationalId(String email);

}
