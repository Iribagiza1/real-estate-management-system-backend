package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.auca.realestate.domain.Agent;
import rw.auca.realestate.domain.Property;

import java.util.List;
import java.util.UUID;

public interface AgentRepository extends JpaRepository<Agent, UUID> {

    @Query("SELECT DISTINCT a FROM Agent a JOIN a.propertyTypes t WHERE LOWER(t.name) = LOWER(:name)")
    List<Agent> findAgentsByPropertyTypeName(@Param("name") String name);

    boolean existsByProperty(Property property);
}