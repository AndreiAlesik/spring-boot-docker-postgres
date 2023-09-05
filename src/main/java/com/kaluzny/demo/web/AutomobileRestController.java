package com.kaluzny.demo.web;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import com.kaluzny.demo.dto.AutoRequestDto;
import com.kaluzny.demo.dto.AutoResponseDto;
import com.kaluzny.demo.service.AutomobileService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AutomobileRestController implements AutomobileOpenApi {

    private final AutomobileService automobileService;
    private final AutomobileRepository repository;

    /**
     * Initializes the controller with a sample automobile upon application startup.
     */
    @Transactional
    @PostConstruct
    public void init() {
        repository.save(new Automobile(UUID.randomUUID(), "Ford", "Green", LocalDateTime.now(), LocalDateTime.now(), true, false));
    }

    /**
     * Saves a new automobile and returns the ID of the saved automobile.
     *
     * @param automobile The details of the automobile to be saved.
     * @return ResponseEntity containing the ID of the saved automobile and HTTP status CREATED.
     */
    @PostMapping("/automobiles")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSON')")
    public ResponseEntity<UUID> saveAutomobile( AutoRequestDto automobile) {
        return automobileService.saveAutomobileAndPushMessage(automobile);
    }



    /**
     * Retrieves a collection of all existing automobiles.
     *
     * @return Collection of AutoResponseDto representing all existing automobiles.
     */
    @GetMapping("/automobiles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Collection<AutoResponseDto> getAllAutomobiles() {
        return automobileService.getAllAutomobiles();
    }



    /**
     * Retrieves an automobile by its unique ID.
     *
     * @param id The ID of the automobile to retrieve.
     * @return AutoResponseDto representing the retrieved automobile.
     */
    @GetMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public AutoResponseDto getAutomobileById(@PathVariable UUID id) {
        return automobileService.getAutomobileById(id);
    }

    /**
     * Retrieves a collection of automobiles by their name.
     *
     * @param name The name of the automobiles to retrieve.
     * @return Collection of AutoResponseDto representing automobiles with the specified name.
     */
    @GetMapping(value = "/automobiles", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Collection<AutoResponseDto> findAutomobileByName(@RequestParam(value = "name") String name) {
        return automobileService.findAutomobileByName(name);
    }



    /**
     * Updates an existing automobile with new details and returns the updated automobile.
     *
     * @param id         The ID of the automobile to update.
     * @param automobile The updated details of the automobile.
     * @return AutoResponseDto representing the updated automobile.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSON')")
    @PutMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AutoResponseDto refreshAutomobile(@PathVariable UUID id, @RequestBody AutoRequestDto automobile) {
        return automobileService.refreshAutomobile(id, automobile);
    }

    /**
     * Marks an automobile as removed based on its ID.
     *
     * @param id The ID of the automobile to mark as removed.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSON')")
    @DeleteMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAutomobileById(@PathVariable UUID id) {
        automobileService.removeAutomobileById(id);
    }

    /**
     * Removes all automobiles.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/automobiles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllAutomobiles() {
        automobileService.removeAllAutomobiles();
    }

    /**
     * Retrieves a collection of automobiles by their color and returns a ResponseEntity containing the collection.
     *
     * @param color The color of the automobiles to retrieve.
     * @return ResponseEntity containing the collection of retrieved automobiles.
     */
    @GetMapping(value = "/automobiles", params = {"color"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Collection<AutoResponseDto>> findAutomobileByColor(@RequestParam(value = "color") String color) {
        return automobileService.findAutomobileByColor(color);
    }

    /**
     * Retrieves a collection of automobiles by their name and color.
     *
     * @param name  The name of the automobiles to retrieve.
     * @param color The color of the automobiles to retrieve.
     * @return Collection of AutoResponseDto representing automobiles with the specified name and color.
     */
    @GetMapping(value = "/automobiles", params = {"name", "color"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Collection<AutoResponseDto> findAutomobileByNameAndColor(
            @RequestParam(value = "name") String name, @RequestParam(value = "color") String color) {
        return automobileService.findAutomobileByNameAndColor(name, color);
    }

    /**
     * Retrieves a paged collection of automobiles by their color starting with a specified prefix.
     *
     * @param colorStartsWith The prefix for the color of the automobiles to retrieve.
     * @param page            The page number for pagination.
     * @param size            The number of items per page.
     * @return Collection of AutoResponseDto representing paged automobiles with colors starting with the specified prefix.
     */
    @GetMapping(value = "/automobiles", params = {"colorStartsWith"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Collection<AutoResponseDto> findAutomobileByColorStartsWith(
            @RequestParam(value = "colorStartsWith") String colorStartsWith,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return automobileService.findAutomobileByColorStartsWith(colorStartsWith, page, size);
    }


}

