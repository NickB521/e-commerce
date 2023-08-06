package com.nicolasblackson.ecommerce.items.controllers;

import com.nicolasblackson.ecommerce.items.exceptions.ItemException;
import com.nicolasblackson.ecommerce.items.models.Item;
import com.nicolasblackson.ecommerce.items.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@CrossOrigin("*")
public class ItemController {
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("")
    public ResponseEntity<Item> createItemRequest(@RequestBody Item item){
        Item savedItem = itemService.create(item);
        ResponseEntity response = new ResponseEntity(savedItem, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = itemService.getAllItems();
        ResponseEntity<List<Item>> response = new ResponseEntity<>(items, HttpStatus.OK);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Integer id){
        try{
            Item item = itemService.getItemById(id);
            ResponseEntity<?> response = new ResponseEntity<>(item, HttpStatus.OK);
            return response;
        } catch (ItemException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody Item item){
        try{
            Item updateItem = itemService.updateItem(id, item);
            ResponseEntity response = new ResponseEntity(updateItem, HttpStatus.OK);
            return response;
        } catch (ItemException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Integer id){
        try {
            itemService.deleteItem(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (ItemException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

}
