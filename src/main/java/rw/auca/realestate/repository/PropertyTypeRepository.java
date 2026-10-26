package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.auca.realestate.domain.PropertyType;

import java.util.List;
import java.util.UUID;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, UUID> {

    @Query("SELECT t FROM PropertyType t WHERE t.agents IS EMPTY")
    List<PropertyType> findUnusedPropertyTypes();
}