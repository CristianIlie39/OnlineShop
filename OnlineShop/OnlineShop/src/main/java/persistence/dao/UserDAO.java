package persistence.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.config.HibernateUtil;
import persistence.entities.User;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAO {

    public void insert(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public User getUserByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getUserByEmailQuery = session.createNamedQuery("getUserByEmail");
        getUserByEmailQuery.setParameter("email", email);
        User userFound = null;
        try {
            userFound = (User) getUserByEmailQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return userFound;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getUserByEmailAndPasswordQuery = session.createNamedQuery("getUserByEmailAndPassword");
        getUserByEmailAndPasswordQuery.setParameter("email", email);
        getUserByEmailAndPasswordQuery.setParameter("password", password);
        User userFound = null;
        try {
            userFound = (User) getUserByEmailAndPasswordQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        return userFound;
    }

    public int updateLoginUser(boolean isConnected, String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateLoginUserQuery = session.createNamedQuery("updateLoginUser");
        updateLoginUserQuery.setParameter("isConnected", isConnected);
        updateLoginUserQuery.setParameter("email", email);
        int numberOfUsersUpdated = updateLoginUserQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfUsersUpdated;
    }

    public int updateUserAddress(String newAddress, String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateUserAddressQuery = session.createNamedQuery("updateUserAddress");
        updateUserAddressQuery.setParameter("newAddress", newAddress);
        updateUserAddressQuery.setParameter("email", email);
        int numberOfAddressesUpdated = updateUserAddressQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfAddressesUpdated;
    }

    public int deleteUserByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteUserByNameAndEmailQuery = session.createNamedQuery("deleteUserByEmail");
        deleteUserByNameAndEmailQuery.setParameter("email", email);
        int numberOfUsersDeleted = deleteUserByNameAndEmailQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfUsersDeleted;
    }

    public User getUserByNameAndEmail(String name, String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getUserByNameAndEmailQuery = session.createNamedQuery("getUserByNameAndEmail");
        getUserByNameAndEmailQuery.setParameter("name", name);
        getUserByNameAndEmailQuery.setParameter("email", email);
        User userFound = null;
        try {
            userFound = (User) getUserByNameAndEmailQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return userFound;
    }

    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getAllUsersQuery = session.createNamedQuery("getAllUsers");
        List<User> userList = getAllUsersQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    public int changeActiveStatusForAllUsers(boolean status) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query changeActiveStatusForAllUsersQuery = session.createNamedQuery("changeActiveStatusForAllUsers");
        changeActiveStatusForAllUsersQuery.setParameter("status", status);
        int numberOfUsersWhithStatusChanged = changeActiveStatusForAllUsersQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfUsersWhithStatusChanged;
    }

}
