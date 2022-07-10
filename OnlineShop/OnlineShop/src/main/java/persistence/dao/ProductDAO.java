package persistence.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.config.HibernateUtil;
import persistence.entities.Product;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDAO {

    public void insert(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    public Product getProductByName(String productName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getProductByNameQuery = session.createNamedQuery("getProductByName");
        getProductByNameQuery.setParameter("productName", productName);
        Product productFound = null;
        try {
            productFound = (Product) getProductByNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return productFound;
    }

    public int updateProduct(String newName, String newDescription, String currentName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateProductQuery = session.createNamedQuery("updateProduct");
        updateProductQuery.setParameter("newName", newName);
        updateProductQuery.setParameter("newDescription", newDescription);
        updateProductQuery.setParameter("currentName", currentName);
        int numberOfProductUpdated = updateProductQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfProductUpdated;
    }

    public int deleteProduct(String productName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteProductQuery = session.createNamedQuery("deleteProductByName");
        deleteProductQuery.setParameter("productName", productName);
        int numberOfProductsDeleted = deleteProductQuery.executeUpdate();
        session.getTransaction();
        session.close();
        return numberOfProductsDeleted;
    }

    public List<Product> getProductList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getProductListQuery = session.createNamedQuery("getProductList");
        List<Product> productList = getProductListQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return productList;
    }

    public void updateStock(String productName, int stock) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateStockQuery = session.createNamedQuery("updateStock");
        updateStockQuery.setParameter("productName", productName);
        updateStockQuery.setParameter("stock", stock);
        updateStockQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
