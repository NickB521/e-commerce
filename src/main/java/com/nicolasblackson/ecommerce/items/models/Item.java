package com.nicolasblackson.ecommerce.items.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemName;
    private Double price;
    private String itemPicSrc;

    public Item(String itemName, Double price, String itemPicSrc) {
        this.itemName = itemName;
        this.price = price;
        this.itemPicSrc = itemPicSrc;
    }
    public Item() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getItemPicSrc() {
        return itemPicSrc;
    }

    public void setItemPicSrc(String itemPicSrc) {
        this.itemPicSrc = itemPicSrc;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName ='" + itemName + '\'' +
                ", price = " + price +
                ", itemPicSrc = " +itemPicSrc +
                '}';
    }
}
