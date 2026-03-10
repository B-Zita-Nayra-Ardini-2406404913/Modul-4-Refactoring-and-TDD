package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.repository.ProductReadRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductReadRepository readRepository;

    public ProductQueryServiceImpl(ProductReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    @Override
    public Product findById(String productId) {
        return readRepository.findById(productId);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> it = readRepository.findAll();
        List<Product> result = new ArrayList<>();
        it.forEachRemaining(result::add);
        return result;
    }
}