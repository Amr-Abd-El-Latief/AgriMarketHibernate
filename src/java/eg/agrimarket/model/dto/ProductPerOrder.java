/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.dto;

/**
 *
 * @author Israa
 */
public class ProductPerOrder {
    private Product product;
    private int quantity;

    public ProductPerOrder(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductPerOrder() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
