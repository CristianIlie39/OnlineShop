package frontend.controller;

import business.dto.ProductDTO;
import business.dto.UserDTO;
import business.service.ProductService;
import business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistence.entities.Product;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @PostMapping(path = "/addProduct")
    public ResponseEntity insertProduct(@RequestBody @Valid ProductDTO productDTO) {
        if (productService.getProductByName(productDTO.getProductName()) != null) {
            System.out.println("The product " + productDTO.getProductName() + " already exists in the database.");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The product " + productDTO.getProductName()
                    + " already exists in the database");
        }
        productService.insert(productDTO);
        System.out.println("The product " + productDTO.getProductName() + " was added in the database.");
        return ResponseEntity.ok("The product " + productDTO.getProductName() + " was added in the database.");
    }

    @PutMapping(path = "/updateProduct")
    public ResponseEntity updateProduct(@RequestParam String newProductName, @RequestParam String newProductDescription,
                                        @RequestParam String currentProductName, @RequestParam String adminEmail) {
        UserDTO admin = userService.getUserByEmail(adminEmail);
        if (admin == null) {
            System.out.println("The administrator with email address " + adminEmail + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The administrator with email address " + adminEmail
                    + " does not exists in the database.");
        }
        if (admin.isUserRole() && admin.isActive()) {
            int numberOfProductsUpdated = productService.updateProduct(newProductName, newProductDescription, currentProductName);
            if (numberOfProductsUpdated == 0) {
                System.out.println("The product " + currentProductName + " does not exists in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product " + currentProductName +
                        " does not exists in the database.");
            } else {
                System.out.println("The product " + currentProductName + " was updated.");
                return ResponseEntity.ok("The product " + currentProductName + " was updated.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation.");
        }
    }

    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity deleteProduct(@RequestParam String productName, @RequestParam String adminEmail) {
        UserDTO admin = userService.getUserByEmail(adminEmail);
        if (admin == null) {
            System.out.println("The administrator with email address " + adminEmail + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The administrator with email address " + adminEmail
                    + " does not exists in the database.");
        }
        if (admin.isUserRole() && admin.isActive()) {
            int numberOfProductsDeleted = productService.deleteProduct(productName);
            if (numberOfProductsDeleted == 0) {
                System.out.println("The product " + productName + " does not exists in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product " + productName +
                        " does not exists in the database.");
            } else {
                System.out.println("The product " + productName + " was deleted from database.");
                return ResponseEntity.ok("The product " + productName + " was deleted from database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation.");
        }
    }

    @GetMapping(path = "/getProductList")
    public ResponseEntity getProductList() {
        List<ProductDTO> productDTOList = productService.getProductList();
        if (productDTOList.isEmpty()) {
            System.out.println("There are no products in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no products in the database.");
        } else {
            System.out.println("The list of products is displayed.");
            return ResponseEntity.ok(productDTOList);
        }
    }

}
