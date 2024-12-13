package com.realestatemanagement.constant;

public enum PropertyCategoryEnum {

    LAND,
    HOUSE,
    APARTMENT,
    UNDEFINED;

    public static PropertyCategoryEnum getCategory(String category) {
        if (category == null) {
            throw new IllegalArgumentException("Please provide a valid category.");
        }

        try {
            PropertyCategoryEnum propertyCategory = PropertyCategoryEnum.valueOf(category.toUpperCase());

            if (propertyCategory == UNDEFINED) {
                throw new IllegalArgumentException("Please provide a valid category.");
            }

            return propertyCategory;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unrecognized property category: " + category);
        }
    }

}
