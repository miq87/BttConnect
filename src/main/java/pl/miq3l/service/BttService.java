package pl.miq3l.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.BTT;
import pl.miq3l.domain.Inverter;
import pl.miq3l.repo.InverterRepo;

import java.util.List;
import java.util.TreeMap;

@Service
public class BttService {
    private final BTT btt;
    private final InverterRepo inverterRepo;

    @Autowired
    public BttService(InverterRepo inverterRepo) {
        this.inverterRepo = inverterRepo;
        btt = BTT.getInstance();
        inverterRepo.saveAll(btt.loadAllInvertersFromBTT().values());
        System.out.println("Saved all inverters from BTT");

        System.out.println("PH_UID: " + System.getenv("PH_UID"));
        System.out.println("PH_URL: " + System.getenv("PH_URL"));
    }

    public List<Inverter> findAll() {
        return inverterRepo.findAll();
    }

    public TreeMap<String, Inverter> loadAllInvertersFromBTT() {
        return btt.loadAllInvertersFromBTT();
    }

}
