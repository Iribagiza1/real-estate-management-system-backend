package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.realestate.domain.Agent;

import java.util.UUID;

public interface AgentRepository extends JpaRepository<Agent, UUID> {
}