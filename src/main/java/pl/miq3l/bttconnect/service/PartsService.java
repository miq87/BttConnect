package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.domain.Part;
import pl.miq3l.bttconnect.repo.PartsRepo;

import java.util.List;

@Service
public class PartsService {
    private final PartsRepo partsRepo;

    @Autowired
    public PartsService(PartsRepo partsRepo) {
        this.partsRepo = partsRepo;
    }

    public List<Part> findAll() {
        return partsRepo.findAll();
    }

    public List<Part> saveAll(List<Part> parts) { return partsRepo.saveAll(parts); }
}
