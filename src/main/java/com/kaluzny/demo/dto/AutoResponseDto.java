package com.kaluzny.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "AutoResponseDto", description = "Data object for automobile response")
public record AutoResponseDto(
        @Schema(description = "Unique identifier of the Automobile.", example = "1")
        UUID id,

        @Schema(description = "Name of the Automobile.", example = "Volvo")
        String name,

        @Schema(description = "Color of the Automobile.", example = "Red")
        String color,

        @Schema(description = "Flag indicating whether the color is original.", example = "true")
        Boolean originalColor
) {
}
