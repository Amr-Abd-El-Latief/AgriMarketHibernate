/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.DAO;

import java.util.List;
import eg.agrimarket.model.dto.Order;
import eg.agrimarket.model.dto.User;

/**
 *
 * @author muhammad
 */
public interface OrderDao {
//    public List<Order> getAllOrders();
    //Get by what?
//    public Order getOrder(int id);
    public boolean addOrder(Order order);
//    public boolean updateOrder(Order order);
    
    
}
