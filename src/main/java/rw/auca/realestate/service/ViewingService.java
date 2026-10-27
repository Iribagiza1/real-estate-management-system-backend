package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.realestate.domain.Viewing;
import rw.auca.realestate.domain.ViewingStatus;
import rw.auca.realestate.repository.OfferRepository;
import rw.auca.realestate.repository.ViewingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ViewingService {

    private final ViewingRepository viewingRepository;
    private final OfferRepository offerRepository;

    public ViewingService(ViewingRepository viewingRepository, OfferRepository offerRepository) {
        this.viewingRepository = viewingRepository;
        this.offerRepository = offerRepository;
    }

    public String save(Viewing viewing) {
        if (viewing.getViewingDate().isBefore(LocalDate.now())) {
            return "Viewing date cannot be in the past";
        }
        boolean busy = viewingRepository.existsByAgentIdAndViewingDateAndStatusNot(
                viewing.getAgent().getId(), viewing.getViewingDate(), ViewingStatus.DECLINED);
        if (busy) {
            return "Agent already has a viewing on that date";
        }
        viewing.setStatus(ViewingStatus.REQUESTED);
        viewingRepository.save(viewing);
        return "Viewing saved successfully";
    }

    public List<Viewing> getAll() {
        return viewingRepository.findAll();
    }

    public Optional<Viewing> getById(UUID id) {
        return viewingRepository.findById(id);
    }

    public String update(UUID id, Viewing viewing) {
        Optional<Viewing> existing = viewingRepository.findById(id);
        if (existing.isEmpty()) {
            return "The viewing with that id does not exist";
        }
        viewing.setId(id);
        viewing.setStatus(existing.get().getStatus());
        viewingRepository.save(viewing);
        return "Viewing updated successfully";
    }

    public String delete(UUID id) {
        Optional<Viewing> found = viewingRepository.findById(id);
        if (found.isEmpty()) {
            return "The viewing with that id does not exist";
        }
        if (offerRepository.existsByViewing(found.get())) {
            return "That viewing has offers, delete them first";
        }
        viewingRepository.deleteById(id);
        return "Viewing deleted successfully";
    }

    public List<Viewing> getByStatus(ViewingStatus status) {
        return viewingRepository.findByStatusOrderByViewingDateAsc(status);
    }

    public List<Viewing> getBetween(LocalDate start, LocalDate end) {
        return viewingRepository.findByViewingDateBetweenOrderByViewingDateAsc(start, end);
    }

    public String updateStatus(UUID id, ViewingStatus newStatus) {
        Optional<Viewing> found = viewingRepository.findById(id);
        if (found.isEmpty()) {
            return "The viewing with that id does not exist";
        }
        Viewing viewing = found.get();
        ViewingStatus current = viewing.getStatus();
        boolean allowed = (current == ViewingStatus.REQUESTED
                && (newStatus == ViewingStatus.ACCEPTED || newStatus == ViewingStatus.DECLINED))
                || (current == ViewingStatus.ACCEPTED
                && (newStatus == ViewingStatus.ATTENDED || newStatus == ViewingStatus.DECLINED));
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        viewing.setStatus(newStatus);
        viewingRepository.save(viewing);
        return "Viewing status updated successfully";
    }

    public List<Object[]> getStatsByStatus() {
        return viewingRepository.countViewingsByStatus();
    }

    @Transactional
    public String cancelDay(UUID agentId, LocalDate date) {
        int count = viewingRepository.cancelDay(agentId, date, ViewingStatus.DECLINED, ViewingStatus.ATTENDED);
        return count + " viewings cancelled";
    }
}