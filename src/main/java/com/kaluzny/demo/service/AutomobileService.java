package com.kaluzny.demo.service;

import com.kaluzny.demo.dto.AutoRequestDto;
import com.kaluzny.demo.dto.AutoResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.UUID;

public interface AutomobileService {
    ResponseEntity<UUID> saveAutomobileAndPushMessage(AutoRequestDto automobile);

    Collection<AutoResponseDto> getAllAutomobiles();

    AutoResponseDto getAutomobileById(UUID id);

    Collection<AutoResponseDto> findAutomobileByName(String name);

    AutoResponseDto refreshAutomobile(UUID id, AutoRequestDto automobile);

    void removeAutomobileById(UUID id);

    void removeAllAutomobiles();

    ResponseEntity<Collection<AutoResponseDto>> findAutomobileByColor(String color);

    Collection<AutoResponseDto> findAutomobileByNameAndColor(String name, String color);

    Collection<AutoResponseDto> findAutomobileByColorStartsWith(String colorStartsWith, int page, int size);

}
