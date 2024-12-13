package com.realestatemanagement.repository;

import com.realestatemanagement.entity.House;
import com.realestatemanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE House " +
            "SET isActive = false " +
            "WHERE id = :id")
    void disable(Long id);

}
