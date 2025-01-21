package com.realestate.repository;

import com.realestate.entity.Ad;
import com.realestate.entity.Favorite;
import com.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite getFavoriteByAdAndUser(Ad ad, User user);

    List<Favorite> getAllByUser(User user);

}
