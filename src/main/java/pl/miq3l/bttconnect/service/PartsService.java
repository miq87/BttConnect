package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.models.Part;
import pl.miq3l.bttconnect.repo.PartRepo;

import java.util.List;

@Service
public class PartsService {
    private final PartRepo partRepo;

    @Autowired
    public PartsService(PartRepo partRepo) {
        this.partRepo = partRepo;
    }

    public List<Part> findAll() {
        return partRepo.findAll();
    }

    public List<Part> saveAll(List<Part> parts) { return partRepo.saveAll(parts); }
}
