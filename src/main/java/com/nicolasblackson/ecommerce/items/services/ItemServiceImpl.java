package com.nicolasblackson.ecommerce.items.services;

import com.nicolasblackson.ecommerce.items.exceptions.ItemException;
import com.nicolasblackson.ecommerce.items.models.Item;
import com.nicolasblackson.ecommerce.items.repos.ItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private ItemRepo itemRepo;

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo){
        this.itemRepo = itemRepo;
    }
    @Override
    public Item create(Item item) {
        Item savedItem = itemRepo.save(item);
        return savedItem;
    }

    @Override
    public Item getItemById(Integer id) throws ItemException {
        Optional<Item> itemOptional = itemRepo.findById(id);
        if(itemOptional.isEmpty()){
            logger.error("Item with id {} does not exist", id);
            throw new ItemException("Item not found");
        }
        return itemOptional.get();
    }

    @Override
    public List<Item> getAllItems() {
        return (List) itemRepo.findAll();
    }

    @Override
    public Item updateItem(Integer id, Item item) throws ItemException {
        Optional<Item> itemOptional = itemRepo.findById(id);
        if(itemOptional.isEmpty()){
            throw new ItemException("Item does not exists, can not update");
        }
        Item savedItem = itemOptional.get();
        savedItem.setItemName(String.valueOf(item.getItemName()));
        savedItem.setPrice(item.getPrice());
        savedItem.setItemPicSrc(item.getItemPicSrc());
        return itemRepo.save(savedItem);
    }

    @Override
    public Boolean deleteItem(Integer id) throws ItemException {
        Optional<Item> itemOptional = itemRepo.findById(id);
        if(itemOptional.isEmpty()){
            throw new ItemException("Item does not exists, can not delete");
        }
        Item itemToDelete = itemOptional.get();
        itemRepo.delete(itemToDelete);
        return true;
    }
}
