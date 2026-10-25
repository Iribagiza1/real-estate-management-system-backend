package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.Viewing;
import rw.auca.realestate.repository.ViewingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ViewingService {

    private final ViewingRepository viewingRepository;

    public ViewingService(ViewingRepository viewingRepository) {
        this.viewingRepository = viewingRepository;
    }

    public String save(Viewing viewing) {
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
        if (!viewingRepository.existsById(id)) {
            return "The viewing with that id does not exist";
        }
        viewing.setId(id);
        viewingRepository.save(viewing);
        return "Viewing updated successfully";
    }

    public String delete(UUID id) {
        if (!viewingRepository.existsById(id)) {
            return "The viewing with that id does not exist";
        }
        viewingRepository.deleteById(id);
        return "Viewing deleted successfully";
    }
}