package id.ac.ui.cs.advprog.eshop2.repository;

import id.ac.ui.cs.advprog.eshop2.model.Product;

public interface ProductWriteRepository {
    Product create(Product product);
    Product edit(Product product);
    Product delete(String productId);
}