package com.kaluzny.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "AutoRequestDto", description = "Data object for automobile request")
public record AutoRequestDto(
        @Schema(description = "Name of the Automobile.", example = "Volvo", requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(max = 50)
        String name,

        @Schema(description = "Color of the Automobile.", example = "Red", requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(max = 50)
        String color,

        @Schema(description = "Flag indicating whether the color is original.", example = "true")
        @JsonProperty("is_original_color")
        Boolean originalColor
) {
}
