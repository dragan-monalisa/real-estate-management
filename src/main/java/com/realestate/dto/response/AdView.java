package com.realestate.dto.response;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.AdStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdView {

    private long id;
    private LocalDateTime createdAt;
    private String title;
    private String description;
    private AdStatusEnum status;
    private AdCategoryEnum category;
    private BigDecimal price;
    private RealtorView realtor;
    private PropertyView property;

}
