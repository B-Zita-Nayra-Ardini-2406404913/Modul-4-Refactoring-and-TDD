package id.ac.ui.cs.advprog.eshop2.controller;

import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.service.ProductQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductReadController {

    private final ProductQueryService queryService;

    public ProductReadController(ProductQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = queryService.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable String id, Model model) {
        Product product = queryService.findById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        // LSP: form cukup tahu bahwa ini Product; tipe konkretnya bisa diganti bebas.
        model.addAttribute("product", new Product());
        return "createProduct";
    }
}