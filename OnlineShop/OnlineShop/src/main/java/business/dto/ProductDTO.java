package business.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDTO {

    @NotNull(message = "The password field cannot be null!")
    @NotEmpty(message = "The password field cannot be empty!")
    @NotBlank(message = "The password field cannot be blank!")
    private String productName;

    @NotNull(message = "The password field cannot be null!")
    @NotEmpty(message = "The password field cannot be empty!")
    @NotBlank(message = "The password field cannot be blank!")
    private String productDescription;

    @NotNull(message = "The stock field cannot be null!")
    private int stock;

    public ProductDTO(String productName, String productDescription, int stock) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.stock = stock;
    }

    public ProductDTO() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String toString() {
        return "product name: " + productName + "\n" + "product description: " + productDescription + "\n" +
                "stock: " + stock;
    }
}
