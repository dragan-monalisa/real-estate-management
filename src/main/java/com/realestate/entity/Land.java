package com.realestate.entity;

import com.realestate.constant.LandTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "hs", name = "land")
public class Land extends Property {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "land_type", nullable = false, length = 32)
    private LandTypeEnum landType;

}
