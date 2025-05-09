package com.david.springsecrest.repositories;

import com.david.springsecrest.enums.ERole;
import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner, UUID> {

    Optional<Owner> findById(UUID userID);

    Optional<Owner> findByNationalId(String nationalId);
//    Optional<User> findByActivationCode(String activationCode);

    @Query("SELECT u FROM Owner u " +
            "WHERE lower(u.names) LIKE lower(concat('%', :searchKey, '%')) " +
            "OR lower(u.nationalId) LIKE lower(concat('%', :searchKey, '%')) " +
            "OR lower(u.phoneNumber) LIKE lower(concat('%', :searchKey, '%'))")
    Page<Owner> searchOwner(Pageable pageable, String searchKey);
}
