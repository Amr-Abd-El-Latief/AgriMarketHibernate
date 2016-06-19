/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.util;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Israa
 */
public class EntityFactory {

    private static EntityManagerFactory emf;

    public synchronized static EntityManagerFactory getEmf() {
        if (emf == null) {
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
            emf = Persistence.createEntityManagerFactory("AgriMarketPU", properties);
        }
        return emf;
    }

}
