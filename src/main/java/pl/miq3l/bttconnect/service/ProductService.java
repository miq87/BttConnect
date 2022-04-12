package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pl.miq3l.bttconnect.BTT;
import pl.miq3l.bttconnect.PhConnect;
import pl.miq3l.bttconnect.domain.Product;
import pl.miq3l.bttconnect.repo.ProductRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepo repo;
    private final BTT btt;
    private final PhConnect ph;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.repo = productRepo;
        btt = BTT.getInstance();
        ph = PhConnect.getInstance();
        ph.login();
    }

    public void save(Product product) {
        repo.save(product);
    }

    public List<Product> findAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "dateAdded"));
    }

    public Product findById(String manufacturerPart) {
        return repo.findById(manufacturerPart).get();
    }

    public List<Product> loadAllProductsFromPh() {
        List<Product> products = new ArrayList<>();
        btt.loadAllInvertersFromBTT().keySet()
                .parallelStream()
                .limit(5)
                .forEach(c -> {
                    Product product = ph.getProductFromPh(c);
                    if (product != null) {
                        products.add(product);
                        save(product);
                        System.out.printf("[ %s ] : SAVED\n", product.getManufacturerPart());
                    }
                });
        return products;
    }

    public Product loadProductFromPh(String part) {
        Product product = ph.getProductFromPh(part);
        save(product);
        return findById(part);
    }
}
