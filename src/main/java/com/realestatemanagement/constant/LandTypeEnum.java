package com.realestatemanagement.constant;

import lombok.Getter;

@Getter
public enum LandTypeEnum {

    AGRICULTURAL("Agricultural land"),
    RESIDENTIAL("Residential land"),
    COMMERCIAL("Commercial land"),
    INDUSTRIAL("Industrial land"),
    FOREST("Forest land"),
    RECREATIONAL("Recreational land"),
    MIXED_USE("Mixed-use land"),
    UNDEVELOPED("Undeveloped land"),
    UNDEFINED("Undefined land type");

    private final String description;

    LandTypeEnum(String description) {
        this.description = description;
    }

    public static LandTypeEnum getLandType(String landType) {
        if (landType == null) {
            return LandTypeEnum.UNDEFINED;
        }

        try {
            return LandTypeEnum.valueOf(landType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return LandTypeEnum.UNDEFINED;
        }
    }

}
