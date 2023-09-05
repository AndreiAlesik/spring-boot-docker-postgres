package com.kaluzny.demo.web;

import com.kaluzny.demo.dto.AutoRequestDto;
import com.kaluzny.demo.dto.AutoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.UUID;

@Tag(name = "Automobile", description = "the Automobile API")
public interface AutomobileOpenApi {

    @Operation(summary = "Add a new Automobile", description = "Creates a new automobile entity", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Automobile created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Automobile already exists")})
    ResponseEntity<UUID> saveAutomobile(@Parameter(description = "Automobile", required = true) @RequestBody AutoRequestDto automobile);

    @Operation(summary = "Find all Automobiles", description = "Retrieves a list of all automobiles", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AutoResponseDto.class))))})
    Collection<AutoResponseDto> getAllAutomobiles();

    @Operation(summary = "Find automobile by ID", description = "Retrieves a single automobile by its ID", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = AutoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Automobile not found")})
    AutoResponseDto getAutomobileById(
            @Parameter(description = "ID of the Automobile to be obtained. Cannot be empty.", required = true)
            @PathVariable UUID id);

    @Operation(summary = "Find automobile by name", description = "Retrieves automobiles by name", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AutoResponseDto.class))))})
    Collection<AutoResponseDto> findAutomobileByName(
            @Parameter(description = "Name of the Automobile to be obtained. Cannot be empty.", required = true) @RequestParam(value = "name") String name);

    @Operation(summary = "Update an existing Automobile", description = "Updates an existing automobile entity", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Automobile not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})
    AutoResponseDto refreshAutomobile(
            @Parameter(description = "ID of the Automobile to be updated. Cannot be empty.", required = true) @PathVariable UUID id,
            @Parameter(description = "Automobile data for updating.", required = true) @RequestBody AutoRequestDto automobile);

    @Operation(summary = "Deletes an Automobile", description = "Deletes an automobile by its ID", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Automobile not found")})
    void removeAutomobileById(
            @Parameter(description = "ID of the Automobile to be deleted. Cannot be empty.", required = true) @PathVariable UUID id);


    @Operation(summary = "Delete all Automobiles", description = "Deletes all automobiles", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No automobiles found")})
    void removeAllAutomobiles();

    @Operation(summary = "Find automobiles by color", description = "Retrieves automobiles by color", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AutoResponseDto.class))))})
    ResponseEntity<Collection<AutoResponseDto>> findAutomobileByColor(
            @Parameter(description = "Color of the Automobiles to be obtained. Cannot be empty.", required = true) @RequestParam(value = "color") String color);

    @Operation(summary = "Find automobiles by name and color", description = "Retrieves automobiles by name and color", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AutoResponseDto.class))))})
    Collection<AutoResponseDto> findAutomobileByNameAndColor(
            @Parameter(description = "Name of the Automobiles to be obtained. Cannot be empty.", required = true) @RequestParam(value = "name") String name,
            @Parameter(description = "Color of the Automobiles to be obtained. Cannot be empty.", required = true) @RequestParam(value = "color") String color);

    @Operation(summary = "Find automobiles by color starts with", description = "Retrieves automobiles by color that starts with", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AutoResponseDto.class))))})
    Collection<AutoResponseDto> findAutomobileByColorStartsWith(
            @Parameter(description = "Color prefix of the Automobiles to be obtained. Cannot be empty.", required = true) @RequestParam(value = "colorStartsWith") String colorStartsWith,
            @Parameter(description = "Page number for pagination", required = true) @RequestParam(value = "page") int page,
            @Parameter(description = "Number of items per page", required = true) @RequestParam(value = "size") int size);
}
