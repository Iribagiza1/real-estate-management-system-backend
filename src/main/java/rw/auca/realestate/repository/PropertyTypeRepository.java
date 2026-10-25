package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.realestate.domain.PropertyType;

import java.util.UUID;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, UUID> {
}