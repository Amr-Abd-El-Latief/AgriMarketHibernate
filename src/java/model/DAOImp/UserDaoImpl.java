package model.DAOImp;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import eg.agrimarket.model.DAO.UserDao;
import model.JPA.DAO.CreditJpaController;
import model.JPA.DAO.InterestJpaController;
import model.JPA.DAO.UserJpaController;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import eg.agrimarket.model.dto.Order;
import eg.agrimarket.model.dto.Product;
import eg.agrimarket.model.dto.ProductPerOrder;
import eg.agrimarket.model.pojo.Category;
import eg.agrimarket.model.pojo.Credit;
import eg.agrimarket.model.pojo.Interest;
import eg.agrimarket.model.pojo.Order1;
import eg.agrimarket.model.pojo.OrderProduct;
import eg.agrimarket.model.pojo.User;
import eg.agrimarket.util.EntityFactory;

/**
 *
 * @author AgriMarket team
 */
public class UserDaoImpl implements UserDao {

    Connection con;
    private static EntityManager em;

    public UserDaoImpl() {
        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        em = userJPA.getEntityManager();

    }

    @Override
    public List<eg.agrimarket.model.dto.User> getAllUsers() {

        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        List<User> allUser = userJPA.findUserEntities();

        CreditJpaController credit = new CreditJpaController(EntityFactory.getEmf());
        ArrayList<Credit> credits = new ArrayList<>();
        for (int i = 0; i < allUser.size(); i++) {
            credits.add(credit.findCreditForEntity(allUser.get(i).getEmail()));
        }

        List<eg.agrimarket.model.dto.User> users = new ArrayList<>();
        for (int i = 0; i < allUser.size(); i++) {
            eg.agrimarket.model.dto.User user = new eg.agrimarket.model.dto.User();
            user.setAddress(allUser.get(i).getAddress());
            Date sqlDate = new Date(allUser.get(i).getDob().getTime());
            user.setDOB(sqlDate.toLocalDate());
            user.setEmail(allUser.get(i).getEmail());
            user.setImage(allUser.get(i).getImage());
            user.setPassword(allUser.get(i).getPassword());
            user.setUserName(allUser.get(i).getUserName());
            user.setJob(allUser.get(i).getJob());
            user.setBalance(credits.get(i).getBalance());
            user.setCreditNumber(credits.get(i).getNumber());

//            user.setInterests(getAllInterests(user.getEmail()));

            OrderDaoImp cart = new OrderDaoImp();
            user.setCart(cart.getCart(allUser.get(i).getEmail()));
            user.setOrders(cart.getAllOrders(allUser.get(i).getEmail()));

            users.add(user);
        }
        return users;
    }

    public ArrayList<eg.agrimarket.model.dto.Interest> getUserInterests(List<Interest> interests) {
        ArrayList<eg.agrimarket.model.dto.Interest> list = new ArrayList<>();
        for (Interest i : interests) {
            eg.agrimarket.model.dto.Interest interest = new eg.agrimarket.model.dto.Interest();
            interest.setId(i.getId());
            interest.setName(i.getName());
            list.add(interest);
        }
        return list;
    }

    public List<Interest> getJPAUserInterests(ArrayList<eg.agrimarket.model.dto.Interest> interests) {
        List<Interest> list = new ArrayList<>();
        InterestJpaController interestHiberDao = new InterestJpaController(EntityFactory.getEmf());
        for (eg.agrimarket.model.dto.Interest i : interests) {
            Interest interest = interestHiberDao.findInterest(i.getId());
            list.add(interest);
        }
        return list;
    }

    public ArrayList<ProductPerOrder> getProductPerOrder(List<OrderProduct> products) {
        ArrayList<ProductPerOrder> list = new ArrayList<>();
        for (OrderProduct p : products) {
            ProductPerOrder product = new ProductPerOrder();
            product.setQuantity(p.getQuantity());
            product.setProduct(getProduct(p.getProductId()));
            list.add(product);
        }
        return list;
    }

    public List<OrderProduct> getProductPerOrder(ArrayList<ProductPerOrder> products) {
        List<OrderProduct> list = new ArrayList<>();
        for (ProductPerOrder p : products) {
            OrderProduct product = new OrderProduct();
            product.setQuantity(p.getQuantity());
            product.setProduct(getJPAProduct(p.getProduct()));
            list.add(product);
        }
        return list;
    }

    public eg.agrimarket.model.pojo.Product getJPAProduct(eg.agrimarket.model.dto.Product p) {
        eg.agrimarket.model.pojo.Product product = new eg.agrimarket.model.pojo.Product();
        product.setCategoryId(new Category(p.getCategoryId()));
        product.setDesc(p.getDesc());
        product.setImage(p.getImage());
        product.setName(p.getName());
        product.setQuantity(p.getQuantity());
        product.setPrice(p.getPrice());
        return product;
    }

    public eg.agrimarket.model.dto.Product getProduct(String name) {
        ProductDaoImp productDao = new ProductDaoImp();
        eg.agrimarket.model.dto.Product product = productDao.getProduct(name);
        return product;
    }

//    public List<Order1> getJPAUserOrders(ArrayList<Order> orders) {
//        List<Order1> list = new Vector<Order1>();
//        for (Order i : orders) {
//            Order1 o = new Order1();
//            o.setDate(new java.util.Date(Date.valueOf(i.getDate()).getTime()));
//            o.setId(i.getId());
//            o.setOrderProductList(null);
//            o.setStatus(i.getStatus());
//            list.add(o);
//        }
//        return list;
//
//    }
    /**
     *
     * @param email user email
     * @param password user Password
     * @return user function for retrieve user
     */
    @Override
    public eg.agrimarket.model.dto.User signIn(String email, String password) {
        User user = null;

        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        user = userJPA.signInUser(email, password);
        if (user == null) {
            return null;
        }

        CreditJpaController credit = new CreditJpaController(EntityFactory.getEmf());
//        user.setCreditNumber(credit.findCreditForEntity(email));
        Credit userCredit = credit.findCreditForEntity(email);

//        Order1JpaController order = new Order1JpaController(EntityFactory.getEmf());
//        user.setOrder1List(order.findAllOrdersForEntity(email));
        eg.agrimarket.model.dto.User dtoUser = new eg.agrimarket.model.dto.User();

        dtoUser.setEmail(user.getEmail());
        dtoUser.setImage(user.getImage());
        dtoUser.setUserName(user.getUserName());
        dtoUser.setAddress(user.getAddress());
        dtoUser.setJob(user.getJob());
        dtoUser.setBalance(userCredit.getBalance());
        dtoUser.setCreditNumber(userCredit.getNumber());
        dtoUser.setPassword(user.getPassword());
        Date sqlDate = new Date(user.getDob().getTime());
        dtoUser.setDOB(sqlDate.toLocalDate());

        OrderDaoImp orderDao = new OrderDaoImp();

        dtoUser.setCart(orderDao.getCart(user.getEmail()));
        dtoUser.setOrders(orderDao.getAllOrders(user.getEmail()));

//        dtoUser.setInterests(getAllInterests(user.getEmail()));

        return dtoUser;

    }

    public ArrayList<eg.agrimarket.model.dto.User> getAllUsersLazy() {
        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        List<User> allUser = userJPA.findUserEntities();
        CreditJpaController credit = new CreditJpaController(EntityFactory.getEmf());
        ArrayList<Credit> credits = new ArrayList<>();
        for (int i = 0; i < allUser.size(); i++) {
            credits.add(credit.findCreditForEntity(allUser.get(i).getEmail()));
        }

        ArrayList<eg.agrimarket.model.dto.User> users = new ArrayList<>();
        for (int i = 0; i < allUser.size(); i++) {
            eg.agrimarket.model.dto.User user = new eg.agrimarket.model.dto.User();
            user.setAddress(allUser.get(i).getAddress());
            user.setBalance(credits.get(i).getBalance());
            OrderDaoImp cart = new OrderDaoImp();
            user.setCart(cart.getCart(allUser.get(i).getEmail()));
            user.setCreditNumber(credits.get(i).getNumber());
            user.setEmail(allUser.get(i).getEmail());
            user.setImage(allUser.get(i).getImage());
            user.setUserName(allUser.get(i).getUserName());
            user.setJob(allUser.get(i).getJob());
            user.setPassword(allUser.get(i).getPassword());

            Date sqlDate = new Date(allUser.get(i).getDob().getTime());
            user.setDOB(sqlDate.toLocalDate());

//            user.setInterests(getAllInterests(allUser.get(i).getEmail()));

            users.add(user);
        }
        return users;
    }

//    public ArrayList<eg.agrimarket.model.dto.Interest> getAllInterests(String userEmail) {
//        InterestJpaController jpaInterest = new InterestJpaController(EntityFactory.getEmf());
//        return getUserInterests(jpaInterest.findAllInterestForEntity(userEmail));
//    }

    @Override
    public void updateUser(eg.agrimarket.model.dto.User user) {

        try {
            UserJpaController userHiberDao = new UserJpaController(EntityFactory.getEmf());
            User jpaUser = userHiberDao.findUser(user.getEmail());
            jpaUser.setUserName(user.getUserName());
            jpaUser.setPassword(user.getPassword());
            jpaUser.setJob(user.getJob());
            jpaUser.setAddress(user.getAddress());
            jpaUser.setDob(new java.util.Date(Date.valueOf(user.getDOB()).getTime()));
            jpaUser.setImage(user.getImage());
            jpaUser.setInterestList(getJPAUserInterests(user.getInterests()));

            UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
            userJPA.edit(jpaUser);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateBalance(String user, int balance) {

        try {
            CreditJpaController jpaCredit = new CreditJpaController(EntityFactory.getEmf());

            eg.agrimarket.model.pojo.Credit credit = jpaCredit.findCredit(user);
            credit.setBalance(balance);
            jpaCredit.edit(credit);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean signUp(eg.agrimarket.model.dto.User user) {

        try {
            User jpaUser = new User(user.getEmail(), user.getUserName(), user.getPassword(), user.getJob(), user.getAddress(), new java.util.Date(user.getDOB().getYear(), user.getDOB().getMonthValue(), user.getDOB().getDayOfMonth()));

            CreditDaoImpl creditDao = new CreditDaoImpl();
            int balance = creditDao.getBalance(user.getCreditNumber());
            jpaUser.setCreditNumber(new Credit(user.getCreditNumber(), balance));
            jpaUser.setImage(user.getImage());
            jpaUser.setInterestList(getJPAUserInterests(user.getInterests()));

            UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
            userJPA.create(jpaUser);
            if (userJPA.findUser(user.getEmail()) != null) {
                Order cart = new Order();
                cart.setDate(LocalDate.now());
                cart.setUser_email(user.getEmail());
                cart.setStatus("cart");
                OrderDaoImp orderDao = new OrderDaoImp();
                if (orderDao.addOrder(cart)) {
                    cart = orderDao.getCart(user.getEmail());
                    user.setCart(cart);
                    user.getOrders().add(cart);
                }

                user.setBalance(balance);

                return true;
            }

        } catch (PreexistingEntityException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public eg.agrimarket.model.dto.User getUser(String email) {

        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        User jpaUser = userJPA.findUser(email);

        CreditJpaController credit = new CreditJpaController(EntityFactory.getEmf());
        Credit creditNumber = credit.findCreditForEntity(email);

        eg.agrimarket.model.dto.User dtoUser = new eg.agrimarket.model.dto.User();
        dtoUser.setAddress(jpaUser.getAddress());
        dtoUser.setBalance(creditNumber.getBalance());
        dtoUser.setCreditNumber(creditNumber.getNumber());
        OrderDaoImp cart = new OrderDaoImp();
        dtoUser.setCart(cart.getCart(jpaUser.getEmail()));
        Date sql = new Date(jpaUser.getDob().getTime());
        dtoUser.setDOB(sql.toLocalDate());
        dtoUser.setEmail(jpaUser.getEmail());
        dtoUser.setImage(jpaUser.getImage());
        dtoUser.setUserName(jpaUser.getUserName());
        dtoUser.setJob(jpaUser.getJob());
        dtoUser.setPassword(jpaUser.getPassword());

        dtoUser.setOrders(cart.getAllOrders(dtoUser.getEmail()));
//        dtoUser.setInterests(getAllInterests(dtoUser.getEmail()));
        return dtoUser;
    }

    @Override
    public boolean isUserExist(String email, String Password
    ) {

        User user = null;
        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        user = userJPA.signInUser(email, Password);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserExist(String email) {
        User user = null;
        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        user = userJPA.findUser(email);
        if (user != null) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean isCreditNumberAssigned(eg.agrimarket.model.dto.Credit dtocredit) {

        CreditJpaController creditHiberDao = new CreditJpaController(EntityFactory.getEmf());
        eg.agrimarket.model.pojo.Credit credit = creditHiberDao.findCredit(dtocredit.getNumber());

        UserJpaController userJPA = new UserJpaController(EntityFactory.getEmf());
        return userJPA.isCreditNumberAssignedForUser(credit);

    }
}
