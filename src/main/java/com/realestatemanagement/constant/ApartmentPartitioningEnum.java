package com.realestatemanagement.constant;

public enum ApartmentPartitioningEnum {

    OPEN_PLAN,
    SEPARATE_ROOMS,
    STUDIO,
    DUPLEX,
    PENTHOUSE,
    UNDEFINED;

    public static ApartmentPartitioningEnum getPartitioning(String partitioning) {
        if (partitioning == null) {
            return ApartmentPartitioningEnum.UNDEFINED;
        }

        try {
            return ApartmentPartitioningEnum.valueOf(partitioning.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ApartmentPartitioningEnum.UNDEFINED;
        }
    }

}
