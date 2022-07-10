package business.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PurchaseDTO {

    @NotNull(message = "The password field cannot be null!")
    @NotEmpty(message = "The password field cannot be empty!")
    @NotBlank(message = "The password field cannot be blank!")
    private String productName;

    @NotNull(message = "The numberOfProduct field cannot be null!")
    private int numberOfProducts;

    @NotNull @Valid
    private UserDTO userDTO;

    @NotNull @Valid
    private ProductDTO productDTO;

    public PurchaseDTO(String productName, int numberOfProducts, UserDTO userDTO, ProductDTO productDTO) {
        this.productName = productName;
        this.numberOfProducts = numberOfProducts;
        this.userDTO = userDTO;
        this.productDTO = productDTO;
    }

    public PurchaseDTO() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public String toString() {
        return "product name: " + productName + "\n" + "number of products: " + numberOfProducts + "\n" + "user: " +
                userDTO.getName();
    }
}
