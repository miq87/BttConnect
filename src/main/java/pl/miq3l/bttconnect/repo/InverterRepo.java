package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.miq3l.bttconnect.models.Inverter;

@Repository
public interface InverterRepo extends JpaRepository<Inverter, String> {
}
