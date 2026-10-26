package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.Agent;
import rw.auca.realestate.repository.AgentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public String save(Agent agent) {
        agentRepository.save(agent);
        return "Agent saved successfully";
    }

    public List<Agent> getAll() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getById(UUID id) {
        return agentRepository.findById(id);
    }

    public String update(UUID id, Agent agent) {
        if (!agentRepository.existsById(id)) {
            return "The agent with that id does not exist";
        }
        agent.setId(id);
        agentRepository.save(agent);
        return "Agent updated successfully";
    }

    public String delete(UUID id) {
        if (!agentRepository.existsById(id)) {
            return "The agent with that id does not exist";
        }
        agentRepository.deleteById(id);
        return "Agent deleted successfully";
    }

    public List<Agent> getByPropertyType(String name) {
        return agentRepository.findAgentsByPropertyTypeName(name);
    }
}