package rw.auca.realestate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.realestate.domain.Offer;
import rw.auca.realestate.service.OfferService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID viewingId, @RequestBody Offer offer) {
        String message = offerService.save(viewingId, offer);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("Offers are only")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/buyer/{nationalId}")
    public List<Offer> getByBuyer(@PathVariable String nationalId) {
        return offerService.getByBuyer(nationalId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = offerService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}