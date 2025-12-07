package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.auth.annotation.RequireRole;
import io.github.tawdi.smartshop.auth.annotation.Role;
import io.github.tawdi.smartshop.dto.ApiResponseDTO;
import io.github.tawdi.smartshop.dto.ValidationGroups;
import io.github.tawdi.smartshop.domain.entity.BaseEntity;
import io.github.tawdi.smartshop.mapper.BaseMapper;
import io.github.tawdi.smartshop.service.BaseCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequireRole(Role.ADMIN)
public abstract class AbstractBaseController<T extends BaseEntity<I>, I, R1, R2> implements BaseController<T, I, R1, R2> {

    protected final BaseCrudService<T, R1, R2, I> service;
    protected final BaseMapper<T, R1, R2> mapper;

    protected AbstractBaseController(BaseCrudService<T, R1, R2, I> service, BaseMapper<T, R1, R2> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @PostMapping({"", "/"})
    @Operation(
            summary = "Create a new resource",
            description = "Creates a new resource with the provided data. All required fields must be provided."
    )

    @ApiResponse(responseCode = "201", description = "Resource created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "409", description = "Resource already exists")

    public ResponseEntity<ApiResponseDTO<R2>> create(@Validated(ValidationGroups.Create.class) @RequestBody R1 requestDTO) {

        R2 responseDTO = service.save(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Resource created successfully", responseDTO));
    }

    @Override
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing resource",
            description = "Updates an existing resource with the provided data. Only provided fields will be updated."
    )
    @ApiResponse(responseCode = "200", description = "Resource updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Resource not found")

    public ResponseEntity<ApiResponseDTO<R2>> update(@PathVariable I id, @Validated(ValidationGroups.Update.class) @RequestBody R1 requestDTO) {
        R2 responseDTO = service.update(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Resource updated successfully", responseDTO));
    }

    @Override
    @GetMapping("/{id}")
    @Operation(
            summary = "Get resource by ID",
            description = "Retrieves a specific resource by its unique identifier."
    )

    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Resource not found")

    public ResponseEntity<ApiResponseDTO<R2>> getById(@PathVariable I id) {
        R2 responseDTO = service.findById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Resource retrieved successfully", responseDTO));
    }

    @Override
    @GetMapping
    @Operation(
            summary = "Get paginated resources",
            description = "Retrieves resources with pagination support. Use page, size, and sort parameters for control."
    )
    @ApiResponse(responseCode = "200", description = "Paginated resources retrieved successfully")
    public ResponseEntity<ApiResponseDTO<Page<R2>>> getAllPaginated(@ParameterObject Pageable pageable) {
        Page<R2> responseDTOPage = service.findAll(pageable);
        return ResponseEntity.ok(ApiResponseDTO.success("Resources retrieved successfully", responseDTOPage));
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a resource",
            description = "Deletes a specific resource by its unique identifier."
    )
    @ApiResponse(responseCode = "200", description = "Resource deleted successfully")
    @ApiResponse(responseCode = "404", description = "Resource not found")

    public ResponseEntity<ApiResponseDTO<Void>> delete(@PathVariable I id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Resource deleted successfully", null));
    }
}
