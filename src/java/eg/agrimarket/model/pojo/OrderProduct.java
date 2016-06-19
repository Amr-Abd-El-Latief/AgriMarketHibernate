/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Formula;

/**
 *
 * @author Israa
 */
@Entity
@Table(name = "order_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderProduct.findAll", query = "SELECT o FROM OrderProduct o"),
    @NamedQuery(name = "OrderProduct.findByOrderId", query = "SELECT o FROM OrderProduct o WHERE o.orderProductPK.orderId = :orderId"),
    @NamedQuery(name = "OrderProduct.findByProductId", query = "SELECT o FROM OrderProduct o WHERE o.orderProductPK.productId = :productId"),
    @NamedQuery(name = "OrderProduct.findByQuantity", query = "SELECT o FROM OrderProduct o WHERE o.quantity = :quantity")})
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderProductPK orderProductPK;
    @Basic(optional = false)
    @Column(name = "`quantity`")
    private int quantity;
    @JoinColumn(name = "product_id", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Order1 order1;
    
    @Formula(value = "product_id")
    private String productId;

    public OrderProduct() {
    }

    public OrderProduct(OrderProductPK orderProductPK) {
        this.orderProductPK = orderProductPK;
    }

    public OrderProduct(OrderProductPK orderProductPK, int quantity) {
        this.orderProductPK = orderProductPK;
        this.quantity = quantity;
    }

    public OrderProduct(int orderId, String productId) {
        this.orderProductPK = new OrderProductPK(orderId, productId);
    }

    public OrderProductPK getOrderProductPK() {
        return orderProductPK;
    }

    public void setOrderProductPK(OrderProductPK orderProductPK) {
        this.orderProductPK = orderProductPK;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order1 getOrder1() {
        return order1;
    }

    public void setOrder1(Order1 order1) {
        this.order1 = order1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderProductPK != null ? orderProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderProduct)) {
            return false;
        }
        OrderProduct other = (OrderProduct) object;
        if ((this.orderProductPK == null && other.orderProductPK != null) || (this.orderProductPK != null && !this.orderProductPK.equals(other.orderProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.pojo.OrderProduct[ orderProductPK=" + orderProductPK + " ]";
    }
    
}
