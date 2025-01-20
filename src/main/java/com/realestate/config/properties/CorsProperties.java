package com.realestate.config.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    @NotEmpty
    private String[] allowedOrigins;

    @NotEmpty
    private String[] allowedHeaders;

    @NotEmpty
    private String[] allowedMethods;

    private boolean allowCredentials;

    @Min(0)
    private long maxAge;

}
