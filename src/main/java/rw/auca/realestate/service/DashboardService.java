package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.ViewingStatus;
import rw.auca.realestate.repository.AgentRepository;
import rw.auca.realestate.repository.BuyerRepository;
import rw.auca.realestate.repository.OfferRepository;
import rw.auca.realestate.repository.PropertyRepository;
import rw.auca.realestate.repository.ViewingRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final BuyerRepository buyerRepository;
    private final AgentRepository agentRepository;
    private final PropertyRepository propertyRepository;
    private final ViewingRepository viewingRepository;
    private final OfferRepository offerRepository;

    public DashboardService(BuyerRepository buyerRepository,
                            AgentRepository agentRepository,
                            PropertyRepository propertyRepository,
                            ViewingRepository viewingRepository,
                            OfferRepository offerRepository) {
        this.buyerRepository = buyerRepository;
        this.agentRepository = agentRepository;
        this.propertyRepository = propertyRepository;
        this.viewingRepository = viewingRepository;
        this.offerRepository = offerRepository;
    }

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("buyers", buyerRepository.count());
        summary.put("agents", agentRepository.count());
        summary.put("unassignedProperties", (long) propertyRepository.findFreeProperties().size());
        summary.put("viewingsToday", viewingRepository.countByViewingDate(LocalDate.now()));
        summary.put("requested", viewingRepository.countByStatus(ViewingStatus.REQUESTED));
        summary.put("offers", offerRepository.count());
        return summary;
    }
}