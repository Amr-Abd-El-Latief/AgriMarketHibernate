/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import eg.agrimarket.model.DAO.OrderDao;
import model.JPA.DAO.Order1JpaController;
import model.JPA.DAO.OrderProductJpaController;
import model.JPA.DAO.ProductJpaController;
import model.JPA.DAO.UserJpaController;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import eg.agrimarket.model.dto.Order;
import eg.agrimarket.model.dto.Product;
import eg.agrimarket.model.dto.ProductPerOrder;
import eg.agrimarket.model.pojo.Order1;
import eg.agrimarket.model.pojo.OrderProduct;
import eg.agrimarket.model.pojo.OrderProductPK;
import eg.agrimarket.model.pojo.User;
import eg.agrimarket.util.EntityFactory;

/**
 *
 * @author muhammad
 */
public class OrderDaoImp implements OrderDao {

    public OrderDaoImp() {
       
    }

    public ArrayList<Order> getAllOrders(String userEmail) {

        UserJpaController userHiberDao = new UserJpaController(EntityFactory.getEmf());
        User user = userHiberDao.findUser(userEmail);
        userHiberDao.getEntityManager().detach(user);
        String hql = "from Order1 where userEmail = :user";
        Order1JpaController orderHiberDao = new Order1JpaController(EntityFactory.getEmf());
        Query query = orderHiberDao.getEntityManager().createQuery(hql).setParameter("user", user);
        List<Order1> orders = query.getResultList();
        ArrayList<Order> userOrders = new ArrayList();

        //Transfer data between hibernate and dtos
        for (int i = 0; i < orders.size(); i++) {
            Order1 hiberOrder = orders.get(i);

            Order order = new Order();
            order.setId(hiberOrder.getId());

            java.util.Date utilDate = hiberOrder.getDate();
            Date sqlDate = new Date(utilDate.getTime());

            order.setDate(sqlDate.toLocalDate());
            order.setStatus(hiberOrder.getStatus());
            order.setUser_email(userEmail);

            ProductDaoImp products = new ProductDaoImp();
            order.setProducts(products.getAllProducts(hiberOrder.getId()));
            int total = 0;
            for (Product p : order.getProducts()) {
                total += p.getQuantity() * p.getPrice();
            }
            order.setTotal(total);
            order.setItems(getOrderItems(hiberOrder.getId()));

            userOrders.add(order);

        }
        return userOrders;
    }

    @Override
    public boolean addOrder(Order order) {
        Order1 hiberOrder = new Order1();
        LocalDate ld = order.getDate();
        Date sqlDate = Date.valueOf(ld);
        hiberOrder.setDate(new java.util.Date(sqlDate.getTime()));
        hiberOrder.setStatus(order.getStatus());
        UserJpaController userHiberDao = new UserJpaController(EntityFactory.getEmf());
        User hiberUser = userHiberDao.findUser(order.getUser_email());
        userHiberDao.getEntityManager().detach(hiberUser);
        hiberOrder.setUserEmail(hiberUser);
        Order1JpaController orderHiberDao = new Order1JpaController(EntityFactory.getEmf());
        orderHiberDao.create(hiberOrder);
        
        orderHiberDao.getEntityManager().detach(hiberOrder);
        //A3ml eh??
        return true;
    }

    public Order getCart(String userMail) {

        UserJpaController userHiberDao = new UserJpaController(EntityFactory.getEmf());
        User user = userHiberDao.findUser(userMail);
        userHiberDao.getEntityManager().detach(user);
        String hql = "from Order1 where userEmail = :user and status = :status";
        Order1JpaController orderHiberDao = new Order1JpaController(EntityFactory.getEmf());
        Query query = orderHiberDao.getEntityManager().createQuery(hql).setParameter("user", user).setParameter("status", "cart");

        Order1 hiberOrder = null;
        try {
            hiberOrder = (Order1) query.getSingleResult();
            orderHiberDao.getEntityManager().detach(hiberOrder);
        } catch (Exception e) {
        }
        if (hiberOrder != null) {
            Order order = new Order();
            order.setId(hiberOrder.getId());

            java.util.Date utilDate = hiberOrder.getDate();
            Date sqlDate = new Date(utilDate.getTime());

            order.setDate(sqlDate.toLocalDate());
            order.setStatus(hiberOrder.getStatus());
            order.setUser_email(userMail);

            ProductDaoImp products = new ProductDaoImp();
            order.setProducts(products.getAllProducts(hiberOrder.getId()));
            
            order.setItems(getOrderItems(hiberOrder.getId()));
            int total = 0;
            for (ProductPerOrder p : order.getItems()) {
                total += p.getQuantity() * p.getProduct().getPrice();
            }
            order.setTotal(total);
            return order;
        } else {
            Order orderNew = new Order();
            orderNew.setDate(LocalDate.now());
            orderNew.setUser_email(userMail);
            orderNew.setStatus("cart");
            orderNew.setItems(new ArrayList<>());
            orderNew.setProducts(new ArrayList<>());
            orderNew.setTotal(0);
            if (addOrder(orderNew)) {
//                return getCart(userMail);
                return orderNew;
            } else {
                return null;
            }
        }
    }

    public boolean updateItemQuantity(ProductPerOrder order, int order_id) {
        OrderProduct orderProduct = new OrderProduct();
        OrderProductPK pk = new OrderProductPK(order_id, order.getProduct().getName());
        orderProduct.setOrderProductPK(pk);
        orderProduct.setQuantity(order.getQuantity());

        ProductJpaController productHiberDao = new ProductJpaController(EntityFactory.getEmf());
        Order1JpaController orderHiberDao = new Order1JpaController(EntityFactory.getEmf());

        eg.agrimarket.model.pojo.Product product = productHiberDao.findProduct(order.getProduct().getName());
        Order1 wantedOrder = orderHiberDao.findOrder1(order_id);

        System.out.println(wantedOrder);
        orderProduct.setProduct(product);
        orderProduct.setOrder1(wantedOrder);

        OrderProductJpaController orderProductHiberDao = new OrderProductJpaController(EntityFactory.getEmf());
        try {
            orderProductHiberDao.edit(orderProduct);
            orderProductHiberDao.getEntityManager().detach(orderProduct);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Order submitOrder(Order order) {
        Order1JpaController orderHiberDao = new Order1JpaController(EntityFactory.getEmf());

        Order1 hiberOrder = orderHiberDao.findOrder1(order.getId());
        hiberOrder.setStatus("done");

        try {
            //UPDATE OLD ORDER TO BE DONE
            orderHiberDao.edit(hiberOrder);
            orderHiberDao.getEntityManager().detach(hiberOrder);

            //CREATE NEW ORDER
            Order1 newHiberOrder = new Order1();
            newHiberOrder.setDate(new java.util.Date());
            newHiberOrder.setStatus("cart");

            //MAP TO OUR USER
            UserJpaController userHiberDao = new UserJpaController(EntityFactory.getEmf());
            User user = userHiberDao.findUser(order.getUser_email());
            userHiberDao.getEntityManager().detach(user);

            newHiberOrder.setUserEmail(user);

            //ADD TO DB
            orderHiberDao.create(newHiberOrder);
            
            orderHiberDao.getEntityManager().detach(newHiberOrder);

            //FORM THE DTO 
            Order resultOrder = new Order();
            resultOrder.setId(newHiberOrder.getId());
            resultOrder.setStatus(newHiberOrder.getStatus());
            resultOrder.setUser_email(order.getUser_email());
            resultOrder.setItems(new ArrayList<>());
            resultOrder.setProducts(new ArrayList<>());
            resultOrder.setTotal(0);

            java.util.Date utilDate = newHiberOrder.getDate();
            Date sqlDate = new Date(utilDate.getTime());

            resultOrder.setDate(sqlDate.toLocalDate());

            return resultOrder;

        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean addNewItem(ProductPerOrder order, int order_id) {

        OrderProduct orderProduct = new OrderProduct();
        OrderProductPK pk = new OrderProductPK(order_id, order.getProduct().getName());
        orderProduct.setOrderProductPK(pk);
        orderProduct.setQuantity(order.getQuantity());

        ProductJpaController productHiberDao = new ProductJpaController(EntityFactory.getEmf());
        Order1JpaController orderHiberDao = new Order1JpaController(EntityFactory.getEmf());

        eg.agrimarket.model.pojo.Product product = productHiberDao.findProduct(order.getProduct().getName());
        Order1 wantedOrder = orderHiberDao.findOrder1(order_id);

        orderProduct.setProduct(product);
        orderProduct.setOrder1(wantedOrder);

        OrderProductJpaController orderProductHiberDao = new OrderProductJpaController(EntityFactory.getEmf());
        try {
            orderProductHiberDao.create(orderProduct);
            orderProductHiberDao.getEntityManager().detach(orderProduct);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean deleteItem(String product_id, int order_id) {
        OrderProductPK pk = new OrderProductPK(order_id, product_id);
        OrderProductJpaController orderProductHiberDao = new OrderProductJpaController(EntityFactory.getEmf());
        try {
//            OrderProduct op = orderProductHiberDao.findOrderProduct(pk);
            orderProductHiberDao.destroy(pk);
//            orderProductHiberDao.getEntityManager().detach(op);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public static void main(String[] args) {
         OrderDaoImp controller = new OrderDaoImp();
         controller.deleteItem("krn2bet", 3);
    }
    
    //STOPED HERE 
    public ArrayList<ProductPerOrder> getOrderItems(int order_id) {

//        String hql = "from OrderProduct o where o.orderProductPK.orderId = :orderId";
        OrderProductJpaController orderProductHiberDao = new OrderProductJpaController(EntityFactory.getEmf());
//        Query query = orderProductHiberDao.getEntityManager().createQuery(hql).setParameter("orderId", order_id);
//        List<OrderProduct> hiberOrderProducts = (List<OrderProduct>) query.getResultList();

        List<OrderProduct> hiberOrderProducts = orderProductHiberDao.findOrderProductOf(order_id);
        ArrayList<ProductPerOrder> items = new ArrayList<>();

        for (int i = 0; i < hiberOrderProducts.size(); i++) {
            OrderProduct hiberOrderProduct = hiberOrderProducts.get(i);
            ProductPerOrder item = new ProductPerOrder();
            item.setQuantity(hiberOrderProduct.getQuantity());
            ProductDaoImp productsDao = new ProductDaoImp();
            item.setProduct(productsDao.getProduct(hiberOrderProduct.getOrderProductPK().getProductId()));
            items.add(item);

        }

        return items;
    }

}
