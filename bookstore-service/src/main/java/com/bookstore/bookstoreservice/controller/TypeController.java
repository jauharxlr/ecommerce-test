package com.bookstore.bookstoreservice.controller;

import com.bookstore.bookstoreservice.constant.AppConstants;
import com.bookstore.bookstoreservice.model.dto.TypeRequestDto;
import com.bookstore.bookstoreservice.model.dto.TypeResponseDto;
import com.bookstore.bookstoreservice.service.TypeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.Endpoints.STORE + AppConstants.Endpoints.TYPE)
public class TypeController {

    private final TypeService typeService;

    @ApiOperation(value = "add a new type", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @PostMapping
    public ResponseEntity<TypeResponseDto> addType(@Valid @RequestBody TypeRequestDto typeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeService.addType(typeRequestDto));
    }

    @ApiOperation(value = "update type by id", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TypeResponseDto> updateType(@PathVariable Long id, @Valid @RequestBody TypeRequestDto typeRequestDto) {
        return ResponseEntity.ok(typeService.updateType(id,typeRequestDto));
    }

    @ApiOperation(value = "Delete type by id", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
    }

    @ApiOperation(value = "Get type by id", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TypeResponseDto> getBook(@PathVariable Long id) {
       return ResponseEntity.ok(typeService.getType(id));
    }
    @ApiOperation(value = "Get all types and promo codes", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping
    public ResponseEntity<List<TypeResponseDto>> getAllTypes() {
        List<TypeResponseDto> list = typeService.getAllTypes();
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(list);
        }
        return ResponseEntity.ok(list);
    }
}
