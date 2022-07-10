package persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "getListOfPurchases", query = "SELECT purchase FROM Purchase purchase")
})

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "number_of_products")
    private int numberOfProducts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "products_id")
    private Product product;

    public Purchase(String productName, int numberOfProducts, User user, Product product) {
        this.productName= productName;
        this.numberOfProducts = numberOfProducts;
        this.user = user;
        this.product = product;
    }

    public Purchase() {

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

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String toString() {
        return "product name: " + productName + "\n" + "number of products: " + numberOfProducts + "\n" +
                "user: " + user.getName();
    }

    public boolean equals(Object object) {
        if (object instanceof Purchase) {
            if (this.id == ((Purchase) object).getId() && this.productName.equals(((Purchase) object).getProductName())
            && this.numberOfProducts == ((Purchase) object).getNumberOfProducts()
            && this.user.equals(((Purchase) object).getUser())
            && this.product.equals(((Purchase) object).getProduct())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, productName, numberOfProducts, user, product);
    }
}
