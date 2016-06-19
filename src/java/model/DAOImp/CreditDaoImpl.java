/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.CreditJpaController;
import eg.agrimarket.model.dto.Credit;
import eg.agrimarket.model.dto.User;
import eg.agrimarket.util.EntityFactory;
/**
 *
 * @author Amr
 */
public class CreditDaoImpl implements eg.agrimarket.model.DAO.CreditDao {

 //   Connection con;
    EntityManagerFactory entityFactory;
    CreditJpaController creditJpaController;

    public CreditDaoImpl() {

//        try {
//            con = JdbcConnection.getConnection();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
        entityFactory = EntityFactory.getEmf();
        creditJpaController = new CreditJpaController(entityFactory);

    }

    @Override
    public boolean checkCredit(Credit credit) {

        //
        eg.agrimarket.model.pojo.Credit credit1 = new eg.agrimarket.model.pojo.Credit();
        credit1 = creditJpaController.findCredit(credit.getNumber());
        if (credit1 != null) {
            System.out.println("There are ay 7aga");
            return true;
        } else {
            System.out.println("Mafeesh");
            return false;
        }

//        try {
//            PreparedStatement pst = con.prepareStatement("select * from credit where number=?");
//
//            pst.setString(1, credit.getNumber());
//
//            ResultSet res = pst.executeQuery();
//            if (res.next()) {
//                return true;
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(CreditDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //      return false;
    }

    public int getBalance(String creditNumber) {

        eg.agrimarket.model.pojo.Credit credit1 = new eg.agrimarket.model.pojo.Credit();

        credit1 = creditJpaController.findCredit(creditNumber);
        if (credit1 != null) {
            return credit1.getBalance();
        } 
        return 0;
  //  }
//        try {
//            PreparedStatement pst = con.prepareStatement("select balance from agri_project.credit where number =?");
//            pst.setString(1, creditNumber);
//            ResultSet res = pst.executeQuery();
//            if (res.next()) {
//                return res.getInt("balance");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
        }
    }
