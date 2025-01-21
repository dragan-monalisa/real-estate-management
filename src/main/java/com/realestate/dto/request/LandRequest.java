package com.realestate.dto.request;

import com.realestate.constant.LandTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LandRequest extends PropertyRequest {

    @NotNull
    private LandTypeEnum landType;

}
