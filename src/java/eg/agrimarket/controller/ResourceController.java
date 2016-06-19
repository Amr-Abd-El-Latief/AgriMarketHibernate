/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eg.agrimarket.model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import eg.agrimarket.model.dto.Product;
 
/**
 *
 * @author muhammad
 */
@WebServlet("/images/*")
public class ResourceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String proName = request.getPathInfo().substring(1); // Returns "foo.png".
        List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
//        ProductDao productDao = new ProductDaoImp();
        for (Product product : products) {
            if (product.getName().equals(proName)) {
                byte[] image = product.getImage();
                if (image != null) {
                    response.setContentType(getServletContext().getMimeType(proName));
                    response.setContentLength(image.length);
                    response.getOutputStream().write(image);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
