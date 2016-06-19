/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAOImp.OrderDaoImp;
import model.DAOImp.ProductDaoImp;
import eg.agrimarket.model.dto.Order;
import eg.agrimarket.model.dto.Product;
import eg.agrimarket.model.dto.ProductPerOrder;
import eg.agrimarket.model.dto.User;

/**
 *
 * @author Israa
 */
public class Cart extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Cart doGet");
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        String operation = request.getParameter("operation");
        String product = request.getParameter("product");

        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("session!=null");
            User user = (User) session.getAttribute("user");
            if (user != null) {
                Order cart = user.getCart();
                if (cart != null) {
                    System.out.println("cart!=null");
                    ArrayList<ProductPerOrder> items = cart.getItems();
                    if (items != null) {
                        System.out.println("items!=null");
                        boolean isAdd = false, found = false;
                        OrderDaoImp orderDao = new OrderDaoImp();
                        if (operation.equals("add")) {
                            System.out.println("isAdd");
                            isAdd = true;
                        }
                        for (int i = 0; i < items.size(); i++) {
                            ProductPerOrder get = items.get(i);
                            System.out.println("product= " + product);
                            System.out.println("get.getProduct().getName() = " + get.getProduct().getName());
                            if (get.getProduct().getName().equals(product)) {
                                if (operation.equals("delete")) {
                                    System.out.println("delete");
                                    orderDao.deleteItem(cart.getItems().get(i).getProduct().getName(), cart.getId());
                                    cart.getItems().remove(i);
                                    System.out.println(cart.getItems());
                                } else if (operation.equals("quantity")) {
                                    System.out.println("quantity");
                                    cart.getItems().get(i).setQuantity(Integer.parseInt(request.getParameter("newVal")));
                                    orderDao.updateItemQuantity(cart.getItems().get(i), cart.getId());

                                } else if (operation.equals("add")) {
                                    System.out.println("found we hdeef 3leeh");
                                    found = true;
                                    cart.getItems().get(i).setQuantity(cart.getItems().get(i).getQuantity() + 1);
                                    orderDao.updateItemQuantity(cart.getItems().get(i), cart.getId());

                                }
                                break;
                            }

                        }

                        if (isAdd && !found) {
                            ProductDaoImp productDao = new ProductDaoImp();
                            ProductPerOrder item = new ProductPerOrder(productDao.getProduct(product), 1);
                            System.out.println("Product: " + item.getProduct());
                            orderDao.addNewItem(item, cart.getId());

                            cart.getItems().add(item);
                        }
                        user.setCart(cart);
                        session.setAttribute("user", user);

                        Gson gson = new Gson();
                        String res = gson.toJson(cart);
                        out.print(res);
                    }
                }
            }
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            Order order = user.getCart();
            if (order != null) {

                OrderDaoImp orderDao = new OrderDaoImp();
                ProductDaoImp productDao = new ProductDaoImp();

                productDao.emptyCart(order);

                ArrayList<Product> products = (ArrayList<Product>) request.getServletContext().getAttribute("products");
                boolean done = false;

                for (int i = 0; i < products.size(); i++) {
                    Product get = products.get(i);
                    for (int j = 0; j < order.getItems().size(); j++) {
                        ProductPerOrder get1 = order.getItems().get(j);
                        if (get1.getProduct().getName().equals(get.getName())) {
                            get.setQuantity(get1.getProduct().getQuantity());
                            break;
                        }
                        if (order.getItems().size() - 1 == j) {
                            done = true;
                        }
                    }
                    if (done) {
                        break;
                    }

                }

                request.getServletContext().setAttribute("products", products);

                user.setCart(orderDao.submitOrder(order));
                session.setAttribute("user", user);

                response.sendRedirect("index.jsp");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
