/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.UserDao;
import model.pojo.User;
import util.JdbcConnection;

/**
 *
 * @author AgriMarket team
 */
public class UserDaoImpl implements UserDao {

    @Override
    public List<User> getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param email user email
     * @param password user Password
     * @return user function for retrieve user
     */
    @Override
    public User signIn(String email, String password) {
        Connection connection;
        User user = null;
        ResultSet res = null;

        try {
            connection = JdbcConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement("select * from user where email =? and password = ?");
            pst.setString(1, email);
            pst.setString(2, password);
            res = pst.executeQuery();
            if (res.next()) {
                user = new User();
                user.setEmail(res.getString("email"));
                user.setUserName(res.getString("user_name"));
                user.setPassword(res.getString("password"));
                user.setJob(res.getString("job"));
                user.setAddress(res.getString("address"));
                user.setImage(res.getBytes("image"));
                user.setDOB(res.getDate("DOB").toLocalDate());
                user.setCreditNumber(res.getString("credit_number"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }

    @Override
    public void updateUser(User user) {
        Connection connection;

        try {
            connection = JdbcConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement("update user set email=? , user_name=? , password=? , job=? , address=? , image=? , DOB=? , credit_number=?  where email =?");
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getUserName());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getJob());
            pst.setString(5, user.getAddress());
            pst.setBytes(6, user.getImage());
            pst.setDate(7, Date.valueOf(user.getDOB()));
            pst.setString(8, user.getCreditNumber());
            pst.setString(9, user.getEmail());
            pst.executeUpdate();

            pst = connection.prepareStatement("delete from interests where email =?");
            pst.setString(1, user.getEmail());
            pst.executeUpdate();

            ArrayList<String> lst = user.getInterests();
            for (int i = 0; i < lst.size(); i++) {
                String get = lst.get(i);

                pst = connection.prepareStatement("insert into  interests (email,name) values (?,?)");
                pst.setString(1, user.getEmail());
                pst.setString(2, get);
                pst.executeUpdate();

            }

//            connection.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean signUp(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
