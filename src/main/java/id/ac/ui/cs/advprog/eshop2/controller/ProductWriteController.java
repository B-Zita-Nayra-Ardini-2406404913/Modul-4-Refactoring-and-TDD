package id.ac.ui.cs.advprog.eshop2.controller;

import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.service.ProductCommandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductWriteController {

    private final ProductCommandService commandService;

    public ProductWriteController(ProductCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product) {
        commandService.create(product);
        return "redirect:/product/list";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product) {
        commandService.edit(product);
        return "redirect:list";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        commandService.delete(id);
        return "redirect:/product/list";
    }
}