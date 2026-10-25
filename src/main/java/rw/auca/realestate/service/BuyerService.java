package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.Buyer;
import rw.auca.realestate.repository.BuyerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public String save(Buyer buyer) {
        buyerRepository.save(buyer);
        return "Buyer saved successfully";
    }

    public List<Buyer> getAll() {
        return buyerRepository.findAll();
    }

    public Optional<Buyer> getById(UUID id) {
        return buyerRepository.findById(id);
    }

    public String update(UUID id, Buyer buyer) {
        if (!buyerRepository.existsById(id)) {
            return "The buyer with that id does not exist";
        }
        buyer.setId(id);
        buyerRepository.save(buyer);
        return "Buyer updated successfully";
    }

    public String delete(UUID id) {
        if (!buyerRepository.existsById(id)) {
            return "The buyer with that id does not exist";
        }
        buyerRepository.deleteById(id);
        return "Buyer deleted successfully";
    }
}