package business.service;

import business.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.UserDAO;
import persistence.entities.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public void insert(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmailAddress(userDTO.getEmailAddress());
        user.setAddress(userDTO.getAddress());
        user.setUserRole(userDTO.isUserRole());
        user.setActive(userDTO.isActive());
        userDAO.insert(user);
    }

    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmailAddress(user.getEmailAddress());
        userDTO.setAddress(user.getAddress());
        userDTO.setUserRole(user.isUserRole());
        userDTO.setActive(user.isActive());
        return userDTO;
    }

    public UserDTO getUserByEmail(String email) {
        User userFound = userDAO.getUserByEmail(email);
        if (userFound == null) {
            return null;
        }
        return getUserDTO(userFound);
    }

    public String cryptPassword(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, messageDigest.digest(password.getBytes()));
        String cryptedPassword = bigInteger.toString();
        return cryptedPassword;
    }

    public UserDTO getUserByEmailAndPassword(String email, String password) {
        User userFound = userDAO.getUserByEmailAndPassword(email, password);
        if (userFound == null) {
            return null;
        }
        return getUserDTO(userFound);
    }

    public int updateLoginUser(boolean isConnected, String email) {
        return userDAO.updateLoginUser(isConnected, email);
    }

    public int updateUserAddress(String newAddress, String email) {
        return userDAO.updateUserAddress(newAddress, email);
    }

    public int deleteUserByEmail(String email) {
        return userDAO.deleteUserByEmail(email);
    }

    public UserDTO getUserByNameAndEmail(String name, String email) {
        User userFound = userDAO.getUserByNameAndEmail(name, email);
        if (userFound == null) {
            return null;
        }
        return getUserDTO(userFound);
    }

    public List<UserDTO> getAllUsers() {
        List<User> userList = userDAO.getAllUsers();
        List<UserDTO> userDTOList = new LinkedList<>();
        for (User user : userList) {
            userDTOList.add(getUserDTO(user));
        }
        return userDTOList;
    }

    public int changeActiveStatusForAllUsers(boolean status) {
        return userDAO.changeActiveStatusForAllUsers(status);
    }

}
