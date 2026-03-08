package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.model.Product;

import java.util.List;

public interface ProductQueryService {
    Product findById(String productId);
    List<Product> findAll();
}