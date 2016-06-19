/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.List;
import model.pojo.Order;
import model.pojo.User;

/**
 *
 * @author muhammad
 */
public interface OrderDao {
    public List<Order> getAllOrders();
    //Get by what?
    public Order getOrder(int id);
    public boolean addOrder(Order order);
    public boolean updateOrder(Order order);
}
