package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.miq3l.bttconnect.domain.Part;

public interface PartsRepo extends JpaRepository<Part, String> {
}
