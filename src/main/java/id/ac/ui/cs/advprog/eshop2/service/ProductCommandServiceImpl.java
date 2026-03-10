package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.repository.ProductWriteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductWriteRepository writeRepository;

    public ProductCommandServiceImpl(ProductWriteRepository writeRepository) {
        this.writeRepository = writeRepository;
    }

    @Override
    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        return writeRepository.create(product);
    }

    @Override
    public Product edit(Product product) {
        return writeRepository.edit(product);
    }

    @Override
    public Product delete(String productId) {
        return writeRepository.delete(productId);
    }
}