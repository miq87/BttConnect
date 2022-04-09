package pl.miq3l.bttconnect.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.miq3l.bttconnect.domain.Product;
import pl.miq3l.bttconnect.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductApi {

    private final ProductService productService;

    @Autowired
    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{manufacturerPart}")
    public Product findProductByManufacturerPart(@PathVariable String manufacturerPart) {
        return productService.findById(manufacturerPart);
    }

    @GetMapping("/all")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/loadAll")
    public List<Product> loadAllProductsFromPh() {
        return productService.loadAllProductsFromPh();
    }

    @GetMapping("/load/{part}")
    public Product loadProductFromPh(@PathVariable String part) {
        return productService.loadProductFromPh(part);
    }

}
