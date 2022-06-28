package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pl.miq3l.bttconnect.components.PhConnect;
import pl.miq3l.bttconnect.models.Product;
import pl.miq3l.bttconnect.repo.ProductRepo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo repo;
    private final PhConnect ph;

    @Autowired
    public ProductService(ProductRepo productRepo, PhConnect ph) {
        this.repo = productRepo;
        this.ph = ph;
    }

    public void save(Product product) {
        repo.save(product);
    }

    public List<Product> findAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "dateAdded"));
    }

    public Product findById(String manufacturerPart) {
        return repo.findById(manufacturerPart).orElseThrow();
    }

    public Product loadProductFromPh(String part) {
        Product product = ph.getProductFromPh(part);

        if(product == null) {
            throw new NoSuchElementException();
        }
        save(product);
        return product;

    }
}
