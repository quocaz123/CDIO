package com.example.CDIO.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CDIO.domain.Item;
import com.example.CDIO.service.ItemService;
import com.example.CDIO.util.annotation.ApiMessage;
import com.example.CDIO.util.error.IdInvalidation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("items")
    @ApiMessage("Create a item")
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.itemService.handleCreateItem(item));
    }

    @GetMapping("/items/{id}")
    @ApiMessage("Fetch item by id")
    public ResponseEntity<Item> getItemById(@Valid @PathVariable("id") long id) throws IdInvalidation {
        Item currentItem = this.itemService.fetchItemById(id);
        if (currentItem == null) {
            throw new IdInvalidation("Item với " + id + " không tồn tại...");
        }
        return ResponseEntity.status(HttpStatus.OK).body(currentItem);
    }

    @GetMapping("/items")
    @ApiMessage("Fetch all item")
    public ResponseEntity<List<Item>> fetchAllItem() {
        return ResponseEntity.status(HttpStatus.OK).body(this.itemService.fetchAllItem());
    }

    @PutMapping("/items")
    @ApiMessage("Update item")
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item) throws IdInvalidation {
        Item currentItem = this.itemService.handleUpdateItem(item);
        if (currentItem == null) {
            throw new IdInvalidation("Item với " + item.getId() + " không tồn tại...");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.itemService.handleUpdateItem(currentItem));
    }

    @DeleteMapping("/items/{id}")
    @ApiMessage("Delete a item")
    public ResponseEntity<Item> deleteItem(@Valid @PathVariable("id") long id) throws IdInvalidation {
        Item currentItem = this.itemService.fetchItemById(id);
        if (currentItem == null) {
            throw new IdInvalidation("Item với " + id + " không tồn tại...");
        }
        this.itemService.handleDeleteItem(id);
        return ResponseEntity.ok(null);
    }
}
