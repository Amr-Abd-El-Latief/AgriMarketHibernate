/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eg.agrimarket.model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import eg.agrimarket.model.dto.Product;
import eg.agrimarket.model.pojo.Category;
 import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author muhammad
 */
public class ProductController extends HttpServlet {
//delete product

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("product_Name");
        List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
        if (products != null) {
            Iterator<Product> iterator = products.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getName().equals(productName)) {
                    iterator.remove();
                }
            }

            request.getServletContext().setAttribute("products", products);

        }
        ProductDao dao = new ProductDaoImp();
        dao.removeProduct(productName);
        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?#product-div");

    }

    //add product
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            eg.agrimarket.model.pojo.Product product = new eg.agrimarket.model.pojo.Product();
            Product productJPA = new Product();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> it = items.iterator();

            while (it.hasNext()) {
                FileItem item = it.next();
                if (!item.isFormField()) {
                    byte[] image = item.get();
                    if (image != null && image.length != 0) {
                        product.setImage(image);
                        productJPA.setImage(image);
                    }
                } else {
                    switch (item.getFieldName()) {
                        case "name":
                            product.setName(item.getString());
                            productJPA.setName(item.getString());
                            System.out.println("name" + item.getString());
                            break;
                        case "price":
                            product.setPrice(Float.valueOf(item.getString()));
                            productJPA.setPrice(Float.valueOf(item.getString()));
                            break;
                        case "quantity":
                            product.setQuantity(Integer.valueOf(item.getString()));
                            productJPA.setQuantity(Integer.valueOf(item.getString()));
                            break;
                        case "desc":
                            product.setDesc(item.getString());
                            productJPA.setDesc(item.getString());
                            System.out.println("desc: " + item.getString());
                            break;
                        default:
                            Category category = new Category();
                            category.setId(Integer.valueOf(item.getString()));
                            product.setCategoryId(category);
                            productJPA.setCategoryId(category.getId());
                    }
                }
            }

            ProductDao daoImp = new ProductDaoImp();
            boolean check = daoImp.addProduct(product);
            if (check) {
                List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
                if (products != null) {
                    products.add(productJPA);
                    request.getServletContext().setAttribute("products", products);

                }
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?success=Successfully#header3-41");
            } else {
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?status=Exist!#header3-41");
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
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
