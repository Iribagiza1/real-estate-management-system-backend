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
import rw.auca.realestate.domain.Agent;
import rw.auca.realestate.service.AgentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Agent agent) {
        String message = agentService.save(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<Agent> getAll() {
        return agentService.getAll();
    }

    @GetMapping("/by-property-type")
    public List<Agent> getByPropertyType(@RequestParam String name) {
        return agentService.getByPropertyType(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Agent> agent = agentService.getById(id);
        if (agent.isPresent()) {
            return ResponseEntity.ok(agent.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The agent with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Agent agent) {
        String message = agentService.update(id, agent);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{agentId}/property-types/{propertyTypeId}")
    public ResponseEntity<String> addPropertyType(@PathVariable UUID agentId, @PathVariable UUID propertyTypeId) {
        String message = agentService.addPropertyType(agentId, propertyTypeId);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("The agent already has")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{agentId}/property")
    public ResponseEntity<String> changeProperty(@PathVariable UUID agentId, @RequestParam int plotNumber) {
        String message = agentService.changeProperty(agentId, plotNumber);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("That property is taken")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = agentService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}