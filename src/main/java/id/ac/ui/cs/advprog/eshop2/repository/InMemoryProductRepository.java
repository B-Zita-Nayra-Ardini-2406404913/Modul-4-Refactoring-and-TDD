package id.ac.ui.cs.advprog.eshop2.repository;

import id.ac.ui.cs.advprog.eshop2.exception.ProductNotFoundException;
import id.ac.ui.cs.advprog.eshop2.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class InMemoryProductRepository
        implements ProductReadRepository, ProductWriteRepository {

    private final List<Product> productData = new ArrayList<>();

    // ── WRITE operations ──────────────────────────────────────────────────────

    @Override
    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    @Override
    public Product edit(Product product) {
        Product existing = findById(product.getProductId());
        if (existing == null) {
            throw new ProductNotFoundException(product.getProductId());
        }
        existing.setProductName(product.getProductName());
        existing.setProductQuantity(product.getProductQuantity());
        return existing;
    }

    @Override
    public Product delete(String productId) {
        Product existing = findById(productId);
        if (existing == null) {
            throw new ProductNotFoundException(productId);
        }
        productData.remove(existing);
        return existing;
    }

    // ── READ operations ───────────────────────────────────────────────────────

    @Override
    public Product findById(String idToSearch) {
        return productData.stream()
                .filter(p -> idToSearch.equals(p.getProductId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}