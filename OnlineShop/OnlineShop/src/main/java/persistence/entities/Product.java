package persistence.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "getProductByName", query = "SELECT product FROM Product product WHERE product.productName = :productName"),
        @NamedQuery(name = "updateProduct", query = "UPDATE Product product SET product.productName = :newName, " +
                "product.productDescription = :newDescription WHERE product.productName = :currentName"),
        @NamedQuery(name = "deleteProductByName", query = "DELETE FROM Product product WHERE product.productName = :productName"),
        @NamedQuery(name = "getProductList", query = "SELECT product FROM Product product"),
        @NamedQuery(name = "updateStock", query = "UPDATE Product product SET product.stock = :stock WHERE " +
                "product.productName = :productName")
})

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Purchase> productsPurchaseList;

    public Product(String productName, String productDescription, int stock) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.stock = stock;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Purchase> getProductsPurchaseList() {
        return productsPurchaseList;
    }

    public void setProductsPurchaseList(List<Purchase> productsPurchaseList) {
        this.productsPurchaseList = productsPurchaseList;
    }

    public String toString() {
        return "product name: " + productName + "\n" + "product description: " + productDescription + "\n" +
                "stock: " + stock;
    }

    public boolean equals(Object object) {
        if (object instanceof Product) {
            if (this.id == ((Product) object).getId() && this.productName.equals(((Product) object).getProductName())
            && this.productDescription.equals(((Product) object).getProductDescription())
            && this.stock == ((Product) object).getStock()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, productName, productDescription, stock);
    }
}
