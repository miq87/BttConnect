package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.miq3l.bttconnect.models.Part;

public interface PartRepo extends JpaRepository<Part, String> {
}
