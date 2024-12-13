package com.realestatemanagement.repository;

import com.realestatemanagement.entity.Apartment;
import com.realestatemanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Apartment " +
            "SET isActive = false " +
            "WHERE id = :id")
    void disable(Long id);

}
