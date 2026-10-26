package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.Property;
import rw.auca.realestate.repository.PropertyRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public String save(Property property) {
        propertyRepository.save(property);
        return "Property saved successfully";
    }

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getById(UUID id) {
        return propertyRepository.findById(id);
    }

    public String update(UUID id, Property property) {
        if (!propertyRepository.existsById(id)) {
            return "The property with that id does not exist";
        }
        property.setId(id);
        propertyRepository.save(property);
        return "Property updated successfully";
    }

    public String delete(UUID id) {
        if (!propertyRepository.existsById(id)) {
            return "The property with that id does not exist";
        }
        propertyRepository.deleteById(id);
        return "Property deleted successfully";
    }

    public List<Property> getFree() {
        return propertyRepository.findFreeProperties();
    }
}