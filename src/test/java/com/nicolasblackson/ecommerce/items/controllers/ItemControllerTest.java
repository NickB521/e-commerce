package com.nicolasblackson.ecommerce.items.controllers;

import com.nicolasblackson.ecommerce.BaseControllerTest;
import com.nicolasblackson.ecommerce.items.exceptions.ItemException;
import com.nicolasblackson.ecommerce.items.models.Item;
import com.nicolasblackson.ecommerce.items.services.ItemService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ItemControllerTest extends BaseControllerTest {

    @MockBean
    private ItemService mockItemService;

    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("Item Post: /items - success")
    public void createItemRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseItem1).when(mockItemService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputItem)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName", Is.is("Test Item 01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Is.is(13.13)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemPicSrc", Is.is("Test Pic")));
    }

    @Test
    @DisplayName("GET /items/1 - Found")
    public void getItemByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseItem1).when(mockItemService).getItemById(1);

        mockMvc.perform(get("/api/v1/items/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName", Is.is("Test Item 01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Is.is(13.13)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemPicSrc", Is.is("Test Pic")));
    }

    @Test
    @DisplayName("GET /items/1 - Not Found")
    public void getItemsByIdTestFail() throws Exception {
        BDDMockito.doThrow(new ItemException("Not Found")).when(mockItemService).getItemById(1);
        mockMvc.perform(get("/api/v1/items/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /items/1 - Success")
    public void putItemTestNotSuccess() throws Exception{
        Item expectedItemUpdate = new Item("After Update Item", 13.13, "Test Pic");
        expectedItemUpdate.setId(1);
        BDDMockito.doReturn(expectedItemUpdate).when(mockItemService).updateItem(any(), any());
        mockMvc.perform(put("/api/v1/items/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputItem)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName", is("After Update Item")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", is(13.13)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemPicSrc", is("Test Pic")));
    }

    @Test
    @DisplayName("DELETE /item/1 - Success")
    public void deleteItemTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockItemService).deleteItem(any());
        mockMvc.perform(delete("/api/v1/items/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /item/1 - Not Found")
    public void deleteItemTestNotFound() throws Exception{
        BDDMockito.doThrow(new ItemException("Not Found")).when(mockItemService).deleteItem(any());
        mockMvc.perform(delete("/widgets/{id}", 1))
                .andExpect(status().isNotFound());
    }

}
