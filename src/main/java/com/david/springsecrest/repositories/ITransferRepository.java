package com.david.springsecrest.repositories;

import com.david.springsecrest.models.Transfer;
import com.david.springsecrest.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITransferRepository extends JpaRepository<Transfer, UUID> {
    Optional<Transfer> findById(UUID transferId);
}
