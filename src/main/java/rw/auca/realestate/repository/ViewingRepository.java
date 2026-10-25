package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.realestate.domain.Viewing;

import java.util.UUID;

public interface ViewingRepository extends JpaRepository<Viewing, UUID> {
}