package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.Offer;
import rw.auca.realestate.domain.Viewing;
import rw.auca.realestate.domain.ViewingStatus;
import rw.auca.realestate.repository.OfferRepository;
import rw.auca.realestate.repository.ViewingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ViewingRepository viewingRepository;

    public OfferService(OfferRepository offerRepository, ViewingRepository viewingRepository) {
        this.offerRepository = offerRepository;
        this.viewingRepository = viewingRepository;
    }

    public String save(UUID viewingId, Offer offer) {
        Optional<Viewing> found = viewingRepository.findById(viewingId);
        if (found.isEmpty()) {
            return "The viewing with that id does not exist";
        }
        Viewing viewing = found.get();
        if (viewing.getStatus() != ViewingStatus.ATTENDED) {
            return "Offers are only for ATTENDED viewings";
        }
        offer.setViewing(viewing);
        offer.setOfferDate(LocalDate.now());
        offerRepository.save(offer);
        return "Offer saved successfully";
    }

    public List<Offer> getByBuyer(String nationalId) {
        return offerRepository.findOffersByBuyerNationalId(nationalId);
    }

    public String delete(UUID id) {
        if (!offerRepository.existsById(id)) {
            return "The offer with that id does not exist";
        }
        offerRepository.deleteById(id);
        return "Offer deleted successfully";
    }
}