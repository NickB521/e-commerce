package com.nicolasblackson.ecommerce.items.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ItemTest {
    private Item item1;
    private Item item2;
    private Item emptyItem1;
    private Item emptyItem2;

    @BeforeEach
    public void setUp(){
        emptyItem1 = new Item();
        emptyItem2 = new Item();

        item1 = new Item("Test Item 01", 13.13, "Test Pic");
        item1.setId(1);

        item2 = new Item("Test Item 02", 13.13, "Test Pic");
        item2.setId(2);
    }

    @Test
    public void testEmptyToString() throws Exception{
        assertEquals(
                emptyItem1.toString(),
                emptyItem2.toString(),
                "Both empty Item instances should have the same toString"
        );
    }

    @Test
    public void testContentToString() throws Exception {

        assertNotEquals(
                item1.toString(),
                item2.toString(),
                "Both non-empty Item instances should have the same toString");
    }

    @Test
    public void testNotToString() throws Exception {

        assertNotEquals(
                emptyItem1.toString(),
                item2.toString(),
                "The Widget instances should not have the same toString");
    }
}
