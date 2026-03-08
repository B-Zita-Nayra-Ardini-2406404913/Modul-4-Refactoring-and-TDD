package id.ac.ui.cs.advprog.eshop2.controller;

import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.service.ProductCommandService;
import id.ac.ui.cs.advprog.eshop2.service.ProductQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    // ProductReadController Tests
    @InjectMocks
    private ProductReadController productReadController;

    // ProductWriteController Tests
    @InjectMocks
    private ProductWriteController productWriteController;

    @Mock
    private ProductQueryService productQueryService;

    @Mock
    private ProductCommandService productCommandService;

    @Mock
    private Model model;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb5589f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    // ==================== READ CONTROLLER TESTS ====================

    // Positive Tests - Create Page (Read Controller)
    @Test
    void testCreateProductPage_shouldReturnCorrectViewName() {
        String viewName = productReadController.createProductPage(model);

        assertEquals("createProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPage_shouldNotReturnWrongViewName() {
        String viewName = productReadController.createProductPage(model);

        assertNotEquals("productList", viewName);
        assertNotEquals("wrongView", viewName);
        assertNotEquals("editProduct", viewName);
    }

    // Positive Tests - List Page (Read Controller)
    @Test
    void testProductListPage_shouldReturnCorrectViewName() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productQueryService.findAll()).thenReturn(productList);

        String viewName = productReadController.productListPage(model);

        assertEquals("productList", viewName);
        verify(productQueryService, times(1)).findAll();
        verify(model, times(1)).addAttribute("products", productList);
    }

    @Test
    void testProductListPage_shouldAddProductsToModel() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productQueryService.findAll()).thenReturn(productList);

        productReadController.productListPage(model);

        verify(model, times(1)).addAttribute("products", productList);
    }

    @Test
    void testProductListPage_shouldNotReturnWrongViewName() {
        List<Product> productList = new ArrayList<>();
        when(productQueryService.findAll()).thenReturn(productList);

        String viewName = productReadController.productListPage(model);

        assertNotEquals("createProduct", viewName);
        assertNotEquals("wrongView", viewName);
        assertNotEquals("editProduct", viewName);
    }

    @Test
    void testProductListPage_withEmptyList_shouldStillWork() {
        List<Product> emptyList = new ArrayList<>();
        when(productQueryService.findAll()).thenReturn(emptyList);

        String viewName = productReadController.productListPage(model);

        assertEquals("productList", viewName);
        verify(model, times(1)).addAttribute("products", emptyList);
    }

    // Positive Tests - Edit Page (Read Controller)
    @Test
    void testEditProductPage_shouldReturnCorrectViewName() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productQueryService.findById(productId)).thenReturn(product);

        String viewName = productReadController.editProductPage(productId, model);

        assertEquals("editProduct", viewName);
        verify(productQueryService, times(1)).findById(productId);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProductPage_shouldAddProductToModel() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productQueryService.findById(productId)).thenReturn(product);

        productReadController.editProductPage(productId, model);

        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProductPage_shouldFindProductById() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productQueryService.findById(productId)).thenReturn(product);

        productReadController.editProductPage(productId, model);

        verify(productQueryService, times(1)).findById(productId);
    }

    @Test
    void testEditProductPage_shouldNotReturnWrongViewName() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productQueryService.findById(productId)).thenReturn(product);

        String viewName = productReadController.editProductPage(productId, model);

        assertNotEquals("createProduct", viewName);
        assertNotEquals("productList", viewName);
        assertNotEquals("wrongView", viewName);
    }

    @Test
    void testEditProductPage_shouldNotCallFindByIdMultipleTimes() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productQueryService.findById(productId)).thenReturn(product);

        productReadController.editProductPage(productId, model);

        verify(productQueryService, times(1)).findById(productId);
        verify(productQueryService, atMostOnce()).findById(productId);
    }

    // Negative Tests - when product not found
    @Test
    void testEditProductPage_withNullProduct_shouldStillReturnView() {
        String productId = "non-existent-id";
        when(productQueryService.findById(productId)).thenReturn(null);

        String viewName = productReadController.editProductPage(productId, model);

        assertEquals("editProduct", viewName);
        verify(model, times(1)).addAttribute("product", null);
    }

    // ==================== WRITE CONTROLLER TESTS ====================

    // Positive Tests - Create Post (Write Controller)
    @Test
    void testCreateProductPost_shouldCallServiceAndRedirect() {
        when(productCommandService.create(any(Product.class))).thenReturn(product);

        String viewName = productWriteController.createProductPost(product);

        assertEquals("redirect:/product/list", viewName);
        verify(productCommandService, times(1)).create(product);
    }

    @Test
    void testCreateProductPost_shouldNotReturnWrongRedirect() {
        when(productCommandService.create(any(Product.class))).thenReturn(product);

        String viewName = productWriteController.createProductPost(product);

        assertNotEquals("redirect:create", viewName);
        assertNotEquals("createProduct", viewName);
        assertNotEquals("redirect:/product/create", viewName);
    }

    // Positive Tests - Edit Post (Write Controller)
    @Test
    void testEditProductPost_shouldCallServiceAndRedirect() {
        when(productCommandService.edit(product)).thenReturn(product);

        String viewName = productWriteController.editProductPost(product);

        assertEquals("redirect:list", viewName);
        verify(productCommandService, times(1)).edit(product);
    }

    @Test
    void testEditProductPost_shouldCallEditWithCorrectProduct() {
        when(productCommandService.edit(product)).thenReturn(product);

        productWriteController.editProductPost(product);

        verify(productCommandService, times(1)).edit(product);
    }

    @Test
    void testEditProductPost_shouldNotReturnWrongRedirect() {
        when(productCommandService.edit(product)).thenReturn(product);

        String viewName = productWriteController.editProductPost(product);

        assertNotEquals("redirect:/product/create", viewName);
        assertNotEquals("editProduct", viewName);
        assertNotEquals("redirect:/product/edit", viewName);
    }

    @Test
    void testEditProductPost_shouldNotCallServiceMultipleTimes() {
        when(productCommandService.edit(product)).thenReturn(product);

        productWriteController.editProductPost(product);

        verify(productCommandService, times(1)).edit(product);
        verify(productCommandService, atMostOnce()).edit(product);
    }

    @Test
    void testEditProductPost_withNullProduct_shouldStillCallService() {
        Product nullProduct = null;
        when(productCommandService.edit(any())).thenReturn(product);

        productWriteController.editProductPost(nullProduct);

        verify(productCommandService, times(1)).edit(nullProduct);
    }

    // Positive Tests - Delete (Write Controller)
    @Test
    void testDeleteProduct_shouldCallServiceAndRedirect() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productCommandService.delete(productId)).thenReturn(product);

        String viewName = productWriteController.deleteProduct(productId);

        assertEquals("redirect:/product/list", viewName);
        verify(productCommandService, times(1)).delete(productId);
    }

    @Test
    void testDeleteProduct_shouldCallDeleteWithCorrectId() {
        String productId = "test-product-id";
        when(productCommandService.delete(productId)).thenReturn(product);

        productWriteController.deleteProduct(productId);

        verify(productCommandService, times(1)).delete(productId);
    }

    @Test
    void testDeleteProduct_shouldNotReturnWrongRedirect() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productCommandService.delete(productId)).thenReturn(product);

        String viewName = productWriteController.deleteProduct(productId);

        assertNotEquals("redirect:/product/create", viewName);
        assertNotEquals("redirect:/product/edit", viewName);
        assertNotEquals("deleteProduct", viewName);
    }

    @Test
    void testDeleteProduct_shouldNotCallServiceMultipleTimes() {
        String productId = "eb5589f-1c39-460e-8860-71af6af63bd6";
        when(productCommandService.delete(productId)).thenReturn(product);

        productWriteController.deleteProduct(productId);

        verify(productCommandService, times(1)).delete(productId);
        verify(productCommandService, atMostOnce()).delete(productId);
    }

    // Edge Cases
    @Test
    void testDeleteProduct_withEmptyId_shouldStillCallService() {
        String emptyId = "";
        when(productCommandService.delete(emptyId)).thenReturn(product);

        String viewName = productWriteController.deleteProduct(emptyId);

        assertEquals("redirect:/product/list", viewName);
        verify(productCommandService, times(1)).delete(emptyId);
    }

    @Test
    void testDeleteProduct_withNullId_shouldStillCallService() {
        String nullId = null;
        when(productCommandService.delete(nullId)).thenReturn(product);

        String viewName = productWriteController.deleteProduct(nullId);

        assertEquals("redirect:/product/list", viewName);
        verify(productCommandService, times(1)).delete(nullId);
    }
}