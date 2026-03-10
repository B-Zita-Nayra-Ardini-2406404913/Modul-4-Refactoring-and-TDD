package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.model.Product;

public interface ProductCommandService {
    Product create(Product product);
    Product edit(Product product);
    Product delete(String productId);
}