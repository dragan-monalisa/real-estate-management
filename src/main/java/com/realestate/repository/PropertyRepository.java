package com.realestate.repository;

import com.realestate.entity.Property;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findPropertyById(Long id);

    default Property getPropertyById(Long id) {
        return findPropertyById(id).orElseThrow(
                () -> new EntityNotFoundException("Property with id: " + id + " not found")
        );
    }

}
