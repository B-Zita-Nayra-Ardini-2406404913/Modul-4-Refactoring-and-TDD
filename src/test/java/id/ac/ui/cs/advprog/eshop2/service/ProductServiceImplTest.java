package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.repository.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductCommandServiceImpl productCommandServiceImpl;

    @InjectMocks
    private ProductQueryServiceImpl productQueryServiceImpl;

    @Mock
    private InMemoryProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb5589f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    // Positive Tests - Create
    @Test
    void testCreate_shouldCallRepositoryAndReturnProduct() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productCommandServiceImpl.create(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testCreate_shouldReturnProductWithCorrectName() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productCommandServiceImpl.create(product);

        assertEquals("Sampo Cap Bambang", result.getProductName());
    }

    @Test
    void testCreate_shouldReturnProductWithCorrectQuantity() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productCommandServiceImpl.create(product);

        assertEquals(100, result.getProductQuantity());
    }

    // Positive Tests - FindAll
    @Test
    void testFindAll_shouldReturnAllProducts() {
        Product product2 = new Product();
        product2.setProductId("eb5589f-1c39-460e-8860-71af6af63bd7");
        product2.setProductName("Sampo Cap Usro");
        product2.setProductQuantity(50);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productQueryServiceImpl.findAll();

        assertEquals(2, result.size());
        assertEquals(product.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_withEmptyRepository_shouldReturnEmptyList() {
        List<Product> emptyList = new ArrayList<>();
        Iterator<Product> emptyIterator = emptyList.iterator();

        when(productRepository.findAll()).thenReturn(emptyIterator);

        List<Product> result = productQueryServiceImpl.findAll();

        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll_shouldCallRepository() {
        List<Product> productList = new ArrayList<>();
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        productQueryServiceImpl.findAll();

        verify(productRepository, times(1)).findAll();
    }

    // Positive Tests - FindById
    @Test
    void testFindById_shouldCallRepositoryAndReturnProduct() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productRepository.findById(productId)).thenReturn(product);

        Product result = productQueryServiceImpl.findById(productId);

        assertEquals(product, result);
        assertEquals(productId, result.getProductId());
        verify(productRepository, times(1)).findById(productId);
    }

    // Positive Tests - Delete
    @Test
    void testDelete_shouldCallRepositoryAndReturnDeletedProduct() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productRepository.delete(productId)).thenReturn(product);

        Product result = productCommandServiceImpl.delete(productId);

        assertEquals(product, result);
        assertEquals(productId, result.getProductId());
        verify(productRepository, times(1)).delete(productId);
    }

    // Positive Tests - Edit
    @Test
    void testEdit_shouldCallRepositoryAndReturnUpdatedProduct() {
        when(productRepository.edit(product)).thenReturn(product);

        Product result = productCommandServiceImpl.edit(product);

        assertEquals(product, result);
        assertEquals("Sampo Cap Bambang", result.getProductName());
        assertEquals(100, result.getProductQuantity());
        verify(productRepository, times(1)).edit(product);
    }

    // Negative Tests - Create
    @Test
    void testCreate_shouldNotReturnNull() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productCommandServiceImpl.create(product);

        assertNotNull(result);
    }

    @Test
    void testCreate_shouldNotReturnDifferentProduct() {
        Product differentProduct = new Product();
        differentProduct.setProductId("different-id");
        differentProduct.setProductName("Different Product");
        differentProduct.setProductQuantity(50);

        when(productRepository.create(product)).thenReturn(product);

        Product result = productCommandServiceImpl.create(product);

        assertNotEquals(differentProduct.getProductId(), result.getProductId());
        assertNotEquals(differentProduct.getProductName(), result.getProductName());
        assertNotEquals(differentProduct.getProductQuantity(), result.getProductQuantity());
    }

    // Negative Tests - FindAll
    @Test
    void testFindAll_shouldNotReturnNull() {
        List<Product> productList = new ArrayList<>();
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productQueryServiceImpl.findAll();

        assertNotNull(result);
    }

    @Test
    void testFindAll_shouldNotReturnWrongSize() {
        Product product2 = new Product();
        product2.setProductId("eb5589f-1c39-460e-8860-71af6af63bd7");

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productQueryServiceImpl.findAll();

        assertNotEquals(0, result.size());
        assertNotEquals(1, result.size());
        assertNotEquals(3, result.size());
        assertEquals(2, result.size());
    }

    // Negative Tests - FindById
    @Test
    void testFindById_productNotFound_shouldReturnNull() {
        String productId = "non-existing-id";
        when(productRepository.findById(productId)).thenReturn(null);

        Product result = productQueryServiceImpl.findById(productId);

        assertNull(result);
        verify(productRepository, times(1)).findById(productId);
    }

    // Negative Tests - Delete
    @Test
    void testDelete_productNotFound_shouldThrowException() {
        String productId = "non-existing-id";
        when(productRepository.delete(productId))
                .thenThrow(new RuntimeException("Product with ID " + productId + " not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productCommandServiceImpl.delete(productId);
        });

        assertEquals("Product with ID non-existing-id not found", exception.getMessage());
        verify(productRepository, times(1)).delete(productId);
    }

    // Negative Tests - Edit
    @Test
    void testEdit_productNotFound_shouldThrowException() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existing-id");
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(200);

        when(productRepository.edit(updatedProduct))
                .thenThrow(new RuntimeException("Product with ID non-existing-id not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productCommandServiceImpl.edit(updatedProduct);
        });

        assertEquals("Product with ID non-existing-id not found", exception.getMessage());
        verify(productRepository, times(1)).edit(updatedProduct);
    }

    // Verification Tests
    @Test
    void testCreate_shouldCallRepositoryExactlyOnce() {
        when(productRepository.create(product)).thenReturn(product);

        productCommandServiceImpl.create(product);
        verify(productRepository, times(1)).create(product);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void testFindAll_shouldNotCallRepositoryMultipleTimes() {
        List<Product> productList = new ArrayList<>();
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        productQueryServiceImpl.findAll();

        verify(productRepository, times(1)).findAll();
        verify(productRepository, atMostOnce()).findAll();
    }
}