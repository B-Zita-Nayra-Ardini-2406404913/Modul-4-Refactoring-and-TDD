package id.ac.ui.cs.advprog.eshop2.repository;

import id.ac.ui.cs.advprog.eshop2.model.Product;

import java.util.Iterator;

public interface ProductReadRepository {
    Product findById(String productId);
    Iterator<Product> findAll();
}