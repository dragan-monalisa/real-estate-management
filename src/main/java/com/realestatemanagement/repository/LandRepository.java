package com.realestatemanagement.repository;

import com.realestatemanagement.entity.Land;
import com.realestatemanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LandRepository extends JpaRepository<Land, Long> {

    List<Land> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Land " +
            "SET isActive = false " +
            "WHERE id = :id")
    void disable(Long id);

}
