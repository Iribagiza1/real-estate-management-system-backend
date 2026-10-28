package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.auca.realestate.domain.Viewing;
import rw.auca.realestate.domain.ViewingStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ViewingRepository extends JpaRepository<Viewing, UUID> {

    List<Viewing> findByStatusOrderByViewingDateAsc(ViewingStatus status);

    List<Viewing> findByViewingDateBetweenOrderByViewingDateAsc(LocalDate start, LocalDate end);

    boolean existsByAgentIdAndViewingDateAndStatusNot(UUID agentId, LocalDate viewingDate, ViewingStatus status);

    long countByViewingDate(LocalDate viewingDate);

    long countByStatus(ViewingStatus status);

    @Query("SELECT v.status, COUNT(v) FROM Viewing v GROUP BY v.status")
    List<Object[]> countViewingsByStatus();

    @Modifying
    @Query("UPDATE Viewing v SET v.status = :declined WHERE v.agent.id = :agentId AND v.viewingDate = :date AND v.status <> :attended")
    int cancelDay(@Param("agentId") UUID agentId,
                  @Param("date") LocalDate date,
                  @Param("declined") ViewingStatus declined,
                  @Param("attended") ViewingStatus attended);
}