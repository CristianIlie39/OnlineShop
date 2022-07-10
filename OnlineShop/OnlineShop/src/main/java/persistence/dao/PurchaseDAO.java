package persistence.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.config.HibernateUtil;
import persistence.entities.Purchase;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public class PurchaseDAO {

    public void insert(Purchase purchase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(purchase);
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<Purchase> getListOfPurchases() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getListOfPurchasesQuery = session.createNamedQuery("getListOfPurchases");
        ArrayList<Purchase> purchaseArrayList = (ArrayList<Purchase>) getListOfPurchasesQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return purchaseArrayList;
    }

}
