package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.realestate.domain.Property;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
}