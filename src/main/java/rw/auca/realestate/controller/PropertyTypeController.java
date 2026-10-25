package rw.auca.realestate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.realestate.domain.PropertyType;
import rw.auca.realestate.service.PropertyTypeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/property-types")
public class PropertyTypeController {

    private final PropertyTypeService propertyTypeService;

    public PropertyTypeController(PropertyTypeService propertyTypeService) {
        this.propertyTypeService = propertyTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody PropertyType propertyType) {
        String message = propertyTypeService.save(propertyType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<PropertyType> getAll() {
        return propertyTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<PropertyType> propertyType = propertyTypeService.getById(id);
        if (propertyType.isPresent()) {
            return ResponseEntity.ok(propertyType.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The property type with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody PropertyType propertyType) {
        String message = propertyTypeService.update(id, propertyType);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = propertyTypeService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}