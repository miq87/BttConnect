package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.miq3l.bttconnect.domain.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
}
