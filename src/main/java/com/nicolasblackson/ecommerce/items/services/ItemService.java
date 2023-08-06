package com.nicolasblackson.ecommerce.items.services;

import com.nicolasblackson.ecommerce.items.exceptions.ItemException;
import com.nicolasblackson.ecommerce.items.models.Item;

import java.util.List;

public interface ItemService {
    Item create(Item item);
    Item getItemById(Integer id) throws ItemException;
    List<Item> getAllItems();
    Item updateItem(Integer id, Item item) throws ItemException;
    Boolean deleteItem(Integer id) throws ItemException;
}
