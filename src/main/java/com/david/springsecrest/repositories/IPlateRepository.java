package com.david.springsecrest.repositories;

import com.david.springsecrest.models.Owner;
import com.david.springsecrest.models.Plate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPlateRepository extends JpaRepository<Plate, UUID> {

    Optional<Plate> findById(UUID plateId);
    Optional<Plate> findByPlateNumber(String plateNumber);

    @Query("SELECT p FROM Plate" +
            " p " +
            "WHERE lower(p.plateNumber) LIKE lower(concat('%', :searchKey, '%'))")
    Page<Plate> searchPlate(Pageable pageable, String searchKey);

    @Query("SELECT p FROM Plate" +
            " p " +
            "WHERE p.owner.id=:ownerId ")
    List<Plate> getPlateNumbersByOwnerId(@Param("ownerId") UUID ownerId);
}
