package com.kaluzny.demo.mapper;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.dto.AutoRequestDto;
import com.kaluzny.demo.dto.AutoResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AutomobileMapper {

    public AutoResponseDto mapToDto(Automobile automobile) {
        return new AutoResponseDto(
                automobile.getId(),
                automobile.getName(),
                automobile.getColor(),
                automobile.getOriginalColor()
        );
    }

    public Automobile mapToEntity(AutoRequestDto requestDto) {
        return new Automobile(
                null,
                requestDto.name(),
                requestDto.color(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                requestDto.originalColor(),
                false
        );
    }
}
