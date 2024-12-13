package com.realestatemanagement.dto.request;

import com.realestatemanagement.constant.LandTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LandRequest {

    @NotBlank
    private LandTypeEnum landType;
}
