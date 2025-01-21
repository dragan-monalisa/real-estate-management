package com.realestate.repository;

import com.realestate.entity.Apartment;
import com.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> getAllByUser(User user);

    @Modifying
    @Query("UPDATE Apartment " +
            "SET isActive = false " +
            "WHERE id = :id")
    void disable(Long id);

}
