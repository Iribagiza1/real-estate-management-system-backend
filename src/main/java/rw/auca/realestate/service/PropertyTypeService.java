package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.PropertyType;
import rw.auca.realestate.repository.PropertyTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyTypeService {

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyTypeService(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public String save(PropertyType propertyType) {
        propertyTypeRepository.save(propertyType);
        return "Property type saved successfully";
    }

    public List<PropertyType> getAll() {
        return propertyTypeRepository.findAll();
    }

    public Optional<PropertyType> getById(UUID id) {
        return propertyTypeRepository.findById(id);
    }

    public String update(UUID id, PropertyType propertyType) {
        if (!propertyTypeRepository.existsById(id)) {
            return "The property type with that id does not exist";
        }
        propertyType.setId(id);
        propertyTypeRepository.save(propertyType);
        return "Property type updated successfully";
    }

    public String delete(UUID id) {
        if (!propertyTypeRepository.existsById(id)) {
            return "The property type with that id does not exist";
        }
        propertyTypeRepository.deleteById(id);
        return "Property type deleted successfully";
    }

    public List<PropertyType> getUnused() {
        return propertyTypeRepository.findUnusedPropertyTypes();
    }
}