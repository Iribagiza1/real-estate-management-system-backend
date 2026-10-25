package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.realestate.domain.Viewing;
import rw.auca.realestate.domain.ViewingStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ViewingRepository extends JpaRepository<Viewing, UUID> {

    List<Viewing> findByStatusOrderByViewingDateAsc(ViewingStatus status);

    List<Viewing> findByViewingDateBetweenOrderByViewingDateAsc(LocalDate start, LocalDate end);
}