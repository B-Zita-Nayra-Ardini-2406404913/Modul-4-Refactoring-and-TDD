package id.ac.ui.cs.advprog.eshop2.repository;

import id.ac.ui.cs.advprog.eshop2.exception.ProductNotFoundException;
import id.ac.ui.cs.advprog.eshop2.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private InMemoryProductRepository repository;

    private Product buildProduct(String id, String name, int quantity) {
        Product p = new Product();
        p.setProductId(id);
        p.setProductName(name);
        p.setProductQuantity(quantity);
        return p;
    }

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
    }

    @Test
    void create_returnsSameProduct() {
        Product product = buildProduct("1", "Apple", 10);
        Product result = repository.create(product);
        assertSame(product, result);
    }

    @Test
    void create_productCanBeFoundAfterCreation() {
        Product product = buildProduct("2", "Banana", 5);
        repository.create(product);
        assertNotNull(repository.findById("2"));
    }

    @Test
    void create_multipleProductsStoredIndependently() {
        repository.create(buildProduct("A", "Alpha", 1));
        repository.create(buildProduct("B", "Beta", 2));

        assertNotNull(repository.findById("A"));
        assertNotNull(repository.findById("B"));
    }

    @Test
    void findById_returnsNullWhenNotFound() {
        assertNull(repository.findById("nonexistent"));
    }

    @Test
    void findById_returnsCorrectProduct() {
        repository.create(buildProduct("10", "Mango", 3));
        repository.create(buildProduct("20", "Grape", 7));

        Product found = repository.findById("20");
        assertNotNull(found);
        assertEquals("20", found.getProductId());
        assertEquals("Grape", found.getProductName());
    }

    @Test
    void findById_returnsNullOnEmptyRepository() {
        assertNull(repository.findById("any"));
    }

    @Test
    void findAll_returnsEmptyIteratorWhenNoProducts() {
        Iterator<Product> it = repository.findAll();
        assertFalse(it.hasNext());
    }

    @Test
    void findAll_returnsAllCreatedProducts() {
        repository.create(buildProduct("1", "P1", 1));
        repository.create(buildProduct("2", "P2", 2));
        repository.create(buildProduct("3", "P3", 3));

        Iterator<Product> it = repository.findAll();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void edit_updatesNameAndQuantity() {
        repository.create(buildProduct("5", "OldName", 10));

        Product updated = buildProduct("5", "NewName", 99);
        Product result = repository.edit(updated);

        assertEquals("NewName", result.getProductName());
        assertEquals(99, result.getProductQuantity());
    }

    @Test
    void edit_returnsSameExistingInstance() {
        Product original = buildProduct("6", "Original", 1);
        repository.create(original);

        Product patch = buildProduct("6", "Patched", 50);
        Product result = repository.edit(patch);

        assertSame(original, result);
    }

    @Test
    void edit_throwsProductNotFoundExceptionWhenIdMissing() {
        Product ghost = buildProduct("999", "Ghost", 0);
        assertThrows(ProductNotFoundException.class, () -> repository.edit(ghost));
    }

    @Test
    void delete_removesProductFromRepository() {
        repository.create(buildProduct("7", "ToDelete", 5));
        repository.delete("7");
        assertNull(repository.findById("7"));
    }

    @Test
    void delete_returnsDeletedProduct() {
        Product product = buildProduct("8", "Erasable", 3);
        repository.create(product);

        Product deleted = repository.delete("8");
        assertSame(product, deleted);
    }

    @Test
    void delete_throwsProductNotFoundExceptionWhenIdMissing() {
        assertThrows(ProductNotFoundException.class, () -> repository.delete("does-not-exist"));
    }

    @Test
    void delete_doesNotAffectOtherProducts() {
        repository.create(buildProduct("X", "Keep", 1));
        repository.create(buildProduct("Y", "Remove", 2));

        repository.delete("Y");

        assertNotNull(repository.findById("X"));
        assertNull(repository.findById("Y"));
    }
}