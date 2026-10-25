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
import rw.auca.realestate.domain.Viewing;
import rw.auca.realestate.service.ViewingService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/viewings")
public class ViewingController {

    private final ViewingService viewingService;

    public ViewingController(ViewingService viewingService) {
        this.viewingService = viewingService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Viewing viewing) {
        String message = viewingService.save(viewing);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<Viewing> getAll() {
        return viewingService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Viewing> viewing = viewingService.getById(id);
        if (viewing.isPresent()) {
            return ResponseEntity.ok(viewing.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The viewing with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Viewing viewing) {
        String message = viewingService.update(id, viewing);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = viewingService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}