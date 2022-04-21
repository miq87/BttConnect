package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.domain.Inverter;
import pl.miq3l.bttconnect.repo.InverterRepo;

import java.util.List;

@Service
public class InverterService {

    private final InverterRepo inverterRepo;

    @Autowired
    public InverterService(InverterRepo inverterRepo) {
        this.inverterRepo = inverterRepo;
    }

    public void saveAll(List<Inverter> inverters) {
        inverterRepo.saveAll(inverters);
    }

}
