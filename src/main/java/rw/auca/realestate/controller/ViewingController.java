package rw.auca.realestate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.realestate.domain.Viewing;
import rw.auca.realestate.domain.ViewingStatus;
import rw.auca.realestate.service.ViewingService;

import java.time.LocalDate;
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
        if (message.equals("Viewing date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if (message.equals("Agent already has a viewing on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<Viewing> getAll() {
        return viewingService.getAll();
    }

    @GetMapping("/by-status")
    public List<Viewing> getByStatus(@RequestParam ViewingStatus status) {
        return viewingService.getByStatus(status);
    }

    @GetMapping("/between")
    public List<Viewing> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return viewingService.getBetween(startDate, endDate);
    }

    @GetMapping("/stats/by-status")
    public List<Object[]> getStatsByStatus() {
        return viewingService.getStatsByStatus();
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam ViewingStatus status) {
        String message = viewingService.updateStatus(id, status);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("Cannot change status")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID agentId, @RequestParam String date) {
        LocalDate day = LocalDate.parse(date);
        String message = viewingService.cancelDay(agentId, day);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = viewingService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("That viewing has offers")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }
}