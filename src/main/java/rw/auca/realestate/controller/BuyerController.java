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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.realestate.domain.Buyer;
import rw.auca.realestate.service.BuyerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Buyer buyer) {
        String message = buyerService.save(buyer);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<Buyer> getAll() {
        return buyerService.getAll();
    }

    @GetMapping("/by-last-name")
    public List<Buyer> getByLastName(@RequestParam String lastName) {
        return buyerService.getByLastName(lastName);
    }

    @GetMapping("/of-agent/{agentId}")
    public ResponseEntity<Object> getOfAgent(@PathVariable UUID agentId) {
        if (!buyerService.agentExists(agentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The agent with that id does not exist");
        }
        return ResponseEntity.ok(buyerService.getBuyersOfAgent(agentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Buyer> buyer = buyerService.getById(id);
        if (buyer.isPresent()) {
            return ResponseEntity.ok(buyer.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The buyer with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Buyer buyer) {
        String message = buyerService.update(id, buyer);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = buyerService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}