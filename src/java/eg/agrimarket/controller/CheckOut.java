/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import eg.agrimarket.model.DAO.UserDao;
import model.DAOImp.UserDaoImpl;
import eg.agrimarket.model.dto.Product;
import eg.agrimarket.model.dto.ProductPerOrder;
import eg.agrimarket.model.dto.User;

/**
 *
 * @author Israa
 */
public class CheckOut extends HttpServlet {

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
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null && (user = (User) session.getAttribute("user")) != null) {
            int balance = user.getBalance();
            int total = 0;
            for (int i = 0; i < user.getCart().getItems().size(); i++) {
                ProductPerOrder item = user.getCart().getItems().get(i);
                total += (item.getQuantity() * item.getProduct().getPrice());

            }
            if (total > balance) {
                out.print("failure");
            } else {
//                List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
                balance -= total;
                user.setBalance(balance);
                session.setAttribute("user", user);
                UserDaoImpl userDao = new UserDaoImpl();
                userDao.updateBalance(user.getCreditNumber(), balance);
                out.print("success");
            }

        } else {
            out.print("failure");
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
