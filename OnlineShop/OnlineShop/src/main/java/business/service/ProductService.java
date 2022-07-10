package business.service;

import business.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ProductDAO;
import persistence.entities.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    public void insert(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setStock(productDTO.getStock());
        productDAO.insert(product);
    }

    public ProductDTO getProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(product.getProductName());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setStock(product.getStock());
        return productDTO;
    }

    public ProductDTO getProductByName(String productName) {
        Product productFound = productDAO.getProductByName(productName);
        if (productFound == null) {
            return null;
        }
        return getProductDTO(productFound);
    }

    public int updateProduct(String newName, String newDescription, String currentName) {
        return productDAO.updateProduct(newName, newDescription, currentName);
    }

    public int deleteProduct(String productName) {
        return productDAO.deleteProduct(productName);
    }

    public List<ProductDTO> getProductList() {
        List<Product> productList = productDAO.getProductList();
        List<ProductDTO> productDTOList = new LinkedList<>();
        for (Product product : productList) {
            ProductDTO productDTO = new ProductDTO();
            productDTOList.add(getProductDTO(product));
        }
        return productDTOList;
    }

    public void updateStock(String productName, int stock) {
        productDAO.updateStock(productName, stock);
    }

}
