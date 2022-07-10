package frontend.controller;

import business.dto.ProductDTO;
import business.dto.PurchaseDTO;
import business.dto.UserDTO;
import business.service.ProductService;
import business.service.PurchaseService;
import business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @PostMapping(path = "/buyProduct")
    public ResponseEntity insertPurchase(@RequestBody @Valid PurchaseDTO purchaseDTO ) {
        UserDTO userDTO = userService.getUserByEmail(purchaseDTO.getUserDTO().getEmailAddress());
        if (userDTO == null) {
            System.out.println("The user with email address " + purchaseDTO.getUserDTO().getEmailAddress() + " does not" +
                    "exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with email address " +
                    purchaseDTO.getUserDTO().getEmailAddress() + " does not exists in the database.");
        }
        if (!userDTO.isActive()) {
            System.out.println("The user is not logged in! Please log in!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The user is not logged in! Please log in!");
        }
        ProductDTO productDTO = productService.getProductByName(purchaseDTO.getProductDTO().getProductName());
        if (productDTO == null) {
            System.out.println("The product " + purchaseDTO.getProductDTO().getProductName() + " does not exists " +
                    "in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product " +
                    purchaseDTO.getProductDTO().getProductName() + " does not exists in the database.");
        }
        if (!purchaseService.checkProductAvailability(purchaseDTO)) {
            System.out.println("Stock unavailable!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock unavailable!");
        }
        purchaseDTO.setUserDTO(userDTO);
        purchaseDTO.setProductDTO(productDTO);
        purchaseService.insert(purchaseDTO); // a purchase is made
        int updatedStock = purchaseDTO.getProductDTO().getStock() - purchaseDTO.getNumberOfProducts(); // remaining stock
        productService.updateStock(purchaseDTO.getProductDTO().getProductName(), updatedStock); // the stock is updated
        System.out.println("You have successfully purchased your products! Thank you!");
        return ResponseEntity.ok("You have successfully purchased your products! Thank you!");
    }

    @GetMapping(path = "/getListOfPurchases")
    public ResponseEntity getListOfPurchases() {
        ArrayList<PurchaseDTO> purchaseDTOArrayList = purchaseService.getListOfPurchases();
        if (purchaseDTOArrayList.isEmpty()) {
            System.out.println("There are no purchases in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no purchases in the database.");
        }
        System.out.println("The list of purchases is displayed.");
        return ResponseEntity.ok(purchaseDTOArrayList);
    }

}

















