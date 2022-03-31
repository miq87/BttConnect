package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.miq3l.bttconnect.domain.Inverter;

public interface InverterRepo extends JpaRepository<Inverter, String> {
}
