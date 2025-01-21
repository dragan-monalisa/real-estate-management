package com.realestate.service;

import com.realestate.dto.response.FavoriteView;
import com.realestate.entity.Ad;
import com.realestate.entity.Favorite;
import com.realestate.entity.User;
import com.realestate.mapper.ModelMapper;
import com.realestate.repository.AdRepository;
import com.realestate.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AdRepository adRepository;

    @PreAuthorize("hasAnyAuthority('USER')")
    public void addToFavorite(User user, Long adId) {
        Ad ad = adRepository.getAdById(adId);

        var favorite = Favorite.builder()
                .ad(ad)
                .user(user)
                .build();

        favoriteRepository.save(favorite);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public void removeFavorite(User user, Long adId) {
        Ad ad = adRepository.getAdById(adId);
        Favorite favorite = favoriteRepository.getFavoriteByAdAndUser(ad, user);

        favoriteRepository.delete(favorite);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public List<FavoriteView> getFavorites(User user) {
        List<Favorite> favorites = favoriteRepository.getAllByUser(user);

        return ModelMapper.mapAll(favorites, FavoriteView.class);
    }

}
