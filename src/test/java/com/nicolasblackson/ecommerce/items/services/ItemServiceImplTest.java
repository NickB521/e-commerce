package com.nicolasblackson.ecommerce.items.services;


import com.nicolasblackson.ecommerce.items.exceptions.ItemException;
import com.nicolasblackson.ecommerce.items.models.Item;
import com.nicolasblackson.ecommerce.items.repos.ItemRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ItemServiceImplTest {

    @MockBean
    private ItemRepo mockItemRepo;

    @Autowired
    private ItemService itemService;

    private Item inputItem;
    private Item mockResponseItem1;
    private Item mockResponseItem2;

    @BeforeEach
    public void setUp(){
        inputItem = new Item("Test Item", 13.13, "Test Pic");

        mockResponseItem1 = new Item("Test Item 01", 13.13, "Test Pic");
        mockResponseItem1.setId(1);

        mockResponseItem2 = new Item("Test Item 02", 13.13, "Test Pic");
        mockResponseItem2.setId(2);
    }

    @Test
    @DisplayName("Item Service: Create Item - Success")
    public void createItemTestSuccess(){
        BDDMockito.doReturn(mockResponseItem1).when(mockItemRepo).save(ArgumentMatchers.any());
        Item returnedItem = itemService.create(inputItem);
        Assertions.assertNotNull(returnedItem, "Item should not be null");
        Assertions.assertEquals(returnedItem.getId(),1 );
    }

    @Test
    @DisplayName("Item Service: Get Item by Id - Success")
    public void getItemByIdTestSuccess() throws ItemException {
        BDDMockito.doReturn(Optional.of(mockResponseItem1)).when(mockItemRepo).findById(1);
        Item foundItem = itemService.getItemById(1);
        Assertions.assertEquals(mockResponseItem1.toString(), foundItem.toString());
    }

    @Test
    @DisplayName("Item Service: Get Item by Id - Fail")
    public void getItemByIdTestFailed() {
        BDDMockito.doReturn(Optional.empty()).when(mockItemRepo).findById(1);
        Assertions.assertThrows(ItemException.class, () -> {
            itemService.getItemById(1);
        });
    }

    @Test
    @DisplayName("Item Service: Get All Item - Success")
    public void getAllItemTestSuccess(){
        List<Item> items = new ArrayList<>();
        items.add(mockResponseItem1);
        items.add(mockResponseItem2);

        BDDMockito.doReturn(items).when(mockItemRepo).findAll();

        List<Item> responseItems = itemService.getAllItems();
        Assertions.assertIterableEquals(items,responseItems);
    }

    @Test
    @DisplayName("Item Service: Update Item - Success")
    public void updateItemTestSuccess() throws ItemException {
        Item expectedItemUpdate = new Item("After Update Item", 13.13, "Test Pic");

        BDDMockito.doReturn(Optional.of(mockResponseItem1)).when(mockItemRepo).findById(1);
        BDDMockito.doReturn(expectedItemUpdate).when(mockItemRepo).save(ArgumentMatchers.any());

        Item actualItem = itemService.updateItem(1, expectedItemUpdate);
        Assertions.assertEquals(expectedItemUpdate.toString(), actualItem.toString());
    }

    @Test
    @DisplayName("Item Service: Update Item - Fail")
    public void updateItemTestFail()  {
        Item expectedItemUpdate = new Item("After Update Item", 13.13, "Test Pic");

        BDDMockito.doReturn(Optional.empty()).when(mockItemRepo).findById(1);
        Assertions.assertThrows(ItemException.class, ()-> {
            itemService.updateItem(1, expectedItemUpdate);
        });
    }

    @Test
    @DisplayName("Item Service: Delete Item - Success")
    public void deleteItemTestSuccess() throws ItemException {
        BDDMockito.doReturn(Optional.of(mockResponseItem1)).when(mockItemRepo).findById(1);
        Boolean actualResponse = itemService.deleteItem(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Item Service: Delete Item - Fail")
    public void deleteItemTestFail()  {
        BDDMockito.doReturn(Optional.empty()).when(mockItemRepo).findById(1);
        Assertions.assertThrows(ItemException.class, ()-> {
            itemService.deleteItem(1);
        });
    }

}
