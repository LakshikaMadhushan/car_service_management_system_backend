package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.ItemCategoryFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveItemCategoryRequestDTO;
import com.esoft.carservice.service.ItemCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.esoft.carservice.constant.ResponseCodes.OPERATION_SUCCESS;
import static com.esoft.carservice.constant.ResponseMessages.SUCCESS_RESPONSE;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("v1/category")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getAllItem() {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, itemCategoryService.getAllCategory(),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @GetMapping(value = "/{itemCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getItem(@PathVariable long itemCategoryId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, itemCategoryService.getCategory(itemCategoryId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateItem(@RequestBody UpdateSaveItemCategoryRequestDTO dto) {
        itemCategoryService.updateCategory(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveItem(@RequestBody UpdateSaveItemCategoryRequestDTO dto) {
        itemCategoryService.saveCategory(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{itemCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> deleteItem(@PathVariable long itemCategoryId) {
        itemCategoryService.deleteCategory(itemCategoryId);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> itemCategoryFilter(@RequestBody ItemCategoryFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, itemCategoryService.getCategoryFilter(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
