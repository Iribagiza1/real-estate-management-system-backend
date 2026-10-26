package rw.auca.realestate.service;

import org.springframework.stereotype.Service;
import rw.auca.realestate.domain.Agent;
import rw.auca.realestate.domain.Property;
import rw.auca.realestate.domain.PropertyType;
import rw.auca.realestate.repository.AgentRepository;
import rw.auca.realestate.repository.PropertyRepository;
import rw.auca.realestate.repository.PropertyTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final PropertyRepository propertyRepository;

    public AgentService(AgentRepository agentRepository,
                        PropertyTypeRepository propertyTypeRepository,
                        PropertyRepository propertyRepository) {
        this.agentRepository = agentRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.propertyRepository = propertyRepository;
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

    public String addPropertyType(UUID agentId, UUID propertyTypeId) {
        Optional<Agent> foundAgent = agentRepository.findById(agentId);
        if (foundAgent.isEmpty()) {
            return "The agent with that id does not exist";
        }
        Optional<PropertyType> foundType = propertyTypeRepository.findById(propertyTypeId);
        if (foundType.isEmpty()) {
            return "That property type does not exist";
        }
        Agent agent = foundAgent.get();
        boolean alreadyHas = false;
        for (PropertyType type : agent.getPropertyTypes()) {
            if (type.getId().equals(propertyTypeId)) {
                alreadyHas = true;
            }
        }
        if (alreadyHas) {
            return "The agent already has that property type";
        }
        agent.getPropertyTypes().add(foundType.get());
        agentRepository.save(agent);
        return "Property type added to the agent";
    }

    public String changeProperty(UUID agentId, int plotNumber) {
        Optional<Agent> foundAgent = agentRepository.findById(agentId);
        if (foundAgent.isEmpty()) {
            return "The agent with that id does not exist";
        }
        Optional<Property> foundProperty = propertyRepository.findByPlotNumber(plotNumber);
        if (foundProperty.isEmpty()) {
            return "The property with that plotNumber does not exist";
        }
        Agent agent = foundAgent.get();
        Property property = foundProperty.get();
        if (agent.getProperty() != null && agent.getProperty().getId().equals(property.getId())) {
            return "Property changed successfully";
        }
        if (agentRepository.existsByProperty(property)) {
            return "That property is taken by another agent";
        }
        agent.setProperty(property);
        agentRepository.save(agent);
        return "Property changed successfully";
    }
}