package business.service;

import business.dto.ProductDTO;
import business.dto.PurchaseDTO;
import business.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ProductDAO;
import persistence.dao.PurchaseDAO;
import persistence.dao.UserDAO;
import persistence.entities.Product;
import persistence.entities.Purchase;
import persistence.entities.User;

import java.util.ArrayList;

@Service
public class PurchaseService {

    @Autowired
    PurchaseDAO purchaseDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    UserDAO userDAO;

    public void insert(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setProductName(purchaseDTO.getProductName());
        purchase.setNumberOfProducts(purchaseDTO.getNumberOfProducts());
        setUser(purchaseDTO, purchase);
        setProduct(purchaseDTO, purchase);
        purchaseDAO.insert(purchase);
    }

    public void setUser(PurchaseDTO purchaseDTO, Purchase purchase) {
        User userFound = userDAO.getUserByEmail(purchaseDTO.getUserDTO().getEmailAddress());
        if (userFound != null) {
            purchase.setUser(userFound);
        } else {
            User user = new User();
            user.setName(purchaseDTO.getUserDTO().getName());
            user.setPassword(purchaseDTO.getUserDTO().getPassword());
            user.setDateOfBirth(purchaseDTO.getUserDTO().getDateOfBirth());
            user.setPhoneNumber(purchaseDTO.getUserDTO().getPhoneNumber());
            user.setEmailAddress(purchaseDTO.getUserDTO().getEmailAddress());
            user.setAddress(purchaseDTO.getUserDTO().getAddress());
            user.setUserRole(purchaseDTO.getUserDTO().isUserRole());
            user.setActive(purchaseDTO.getUserDTO().isActive());
            purchase.setUser(user);
        }
    }

    public void setProduct(PurchaseDTO purchaseDTO, Purchase purchase) {
        Product productFound = productDAO.getProductByName(purchaseDTO.getProductDTO().getProductName());
        if (productFound != null) {
            purchase.setProduct(productFound);
        } else {
            Product product = new Product();
            product.setProductName(purchaseDTO.getProductDTO().getProductName());
            product.setProductDescription(purchaseDTO.getProductDTO().getProductDescription());
            product.setStock(purchaseDTO.getProductDTO().getStock());
            purchase.setProduct(product);
        }
    }

    public boolean checkProductAvailability(PurchaseDTO purchaseDTO) {
        Product product = productDAO.getProductByName(purchaseDTO.getProductDTO().getProductName());
        return product.getStock() >= purchaseDTO.getNumberOfProducts();
    }

    public void setProductDTO(Purchase purchase, PurchaseDTO purchaseDTO) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(purchase.getProduct().getProductName());
        productDTO.setProductDescription(purchase.getProduct().getProductDescription());
        productDTO.setStock(purchase.getProduct().getStock());
        purchaseDTO.setProductDTO(productDTO);
    }

    public void setUserDTO(Purchase purchase, PurchaseDTO purchaseDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(purchase.getUser().getName());
        userDTO.setPassword(purchase.getUser().getPassword());
        userDTO.setDateOfBirth(purchase.getUser().getDateOfBirth());
        userDTO.setPhoneNumber(purchase.getUser().getPhoneNumber());
        userDTO.setEmailAddress(purchase.getUser().getEmailAddress());
        userDTO.setAddress(purchase.getUser().getAddress());
        userDTO.setUserRole(purchase.getUser().isUserRole());
        userDTO.setActive(purchase.getUser().isActive());
        purchaseDTO.setUserDTO(userDTO);
    }

    public PurchaseDTO getPurchaseDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setProductName(purchase.getProductName());
        purchaseDTO.setNumberOfProducts(purchase.getNumberOfProducts());
        setProductDTO(purchase, purchaseDTO);
        setUserDTO(purchase, purchaseDTO);
        return purchaseDTO;
    }

    public ArrayList<PurchaseDTO> getListOfPurchases() {
        ArrayList<Purchase> purchaseArrayList = purchaseDAO.getListOfPurchases();
        ArrayList<PurchaseDTO> purchaseDTOArrayList = new ArrayList<>();
        for (Purchase purchase : purchaseArrayList) {
            purchaseDTOArrayList.add(getPurchaseDTO(purchase));
        }
        return purchaseDTOArrayList;
    }

}
