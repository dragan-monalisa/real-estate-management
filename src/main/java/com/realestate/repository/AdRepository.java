package com.realestate.repository;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.entity.Ad;
import com.realestate.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AdRepository extends JpaRepository<Ad, Long>, JpaSpecificationExecutor<Ad> {

    int countByRealtorAndIsActiveTrue(User realtor);

    List<Ad> findALlByOwner(User user);

    List<Ad> findALlByOwnerAndCategory(User user, AdCategoryEnum adCategory);

    List<Ad> findAllByRealtor(User realtor);

    List<Ad> findAllByRealtorAndCategory(User realtor, AdCategoryEnum adCategory);

    Optional<Ad> findAdById(long id);

    @Modifying
    @Query("UPDATE Ad " +
            "SET isActive = false " +
            "WHERE id = :id")
    void disable(long id);

    default int countRealtorAds(User realtor) {
        return countByRealtorAndIsActiveTrue(realtor);
    }

    default Ad getAdById(long id) {
        return findAdById(id).orElseThrow(
                () -> new EntityNotFoundException("Ad with id: " + id + " not found")
        );
    }

}
