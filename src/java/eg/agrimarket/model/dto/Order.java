/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author muhammad
 */
public class Order {
    private int id;
    private LocalDate date;
    private String status;
    private String user_email;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<ProductPerOrder> items = new ArrayList<>();
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
    
    

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<ProductPerOrder> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProductPerOrder> items) {
        this.items = items;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    
}
