package com.realestate.specification;

import com.realestate.dto.request.AdSearchRequest;
import com.realestate.entity.Ad;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdSpecification {

    public static Specification<Ad> getAdsByFilters(AdSearchRequest request) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));

            if (request.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }

            if (request.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }

            if (request.getMinArea() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("property").get("area"), request.getMinArea()));
            }

            if (request.getMaxArea() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("property").get("area"), request.getMaxArea()));
            }

            if (request.getAdCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), request.getAdCategory()));
            }

            if (request.getPropertyCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("property").get("category"), request.getPropertyCategory()));
            }

            if (request.getAddress() != null && request.getAddress().getCity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("property").get("address").get("city"), request.getAddress().getCity()));
            }

            if (request.getAddress() != null && request.getAddress().getCounty() != null) {
                predicates.add(criteriaBuilder.equal(root.get("property").get("address").get("county"), request.getAddress().getCounty()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
