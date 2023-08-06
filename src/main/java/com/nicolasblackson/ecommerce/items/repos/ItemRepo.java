package com.nicolasblackson.ecommerce.items.repos;

import com.nicolasblackson.ecommerce.items.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepo extends JpaRepository<Item, Integer> {
    Optional<Item> findItemByItemName(String itemName);

}
