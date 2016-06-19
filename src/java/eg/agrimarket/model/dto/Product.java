/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.dto;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author muhammad
 */
public class Product {

    private String name;
    private float price;
    private String desc;
    private int categoryId;
    private byte[] image;
    private int quantity;
    private String imageUrl;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        if (image == null || image.length == 0) {
//            System.out.println("I am null");
            return null;
        }
        String url = "data:image/png;base64," + Base64.encodeBase64String(image);

//        System.out.println(url);
        return url;
    }

}
