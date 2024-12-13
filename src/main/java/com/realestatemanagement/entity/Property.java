package com.realestatemanagement.entity;

import com.realestatemanagement.constant.PropertyCategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Property {

    @NotNull
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 32)
    private PropertyCategoryEnum category;

    @NotNull
    @Min(0)
    @Column(name = "usable_area", nullable = false)
    private int usableArea;

    public Property() {
        isActive = true;
        category = PropertyCategoryEnum.UNDEFINED;
    }

}
