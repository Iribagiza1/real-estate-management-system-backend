package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.auca.realestate.domain.Property;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    @Query("SELECT p FROM Property p WHERE NOT EXISTS (SELECT a FROM Agent a WHERE a.property = p) ORDER BY p.plotNumber")
    List<Property> findFreeProperties();
}