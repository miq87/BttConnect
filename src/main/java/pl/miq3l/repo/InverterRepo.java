package pl.miq3l.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.miq3l.domain.Inverter;

public interface InverterRepo extends JpaRepository<Inverter, String> {
}
