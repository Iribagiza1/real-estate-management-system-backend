package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.auca.realestate.domain.Buyer;

import java.util.List;
import java.util.UUID;

public interface BuyerRepository extends JpaRepository<Buyer, UUID> {

    List<Buyer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT b FROM Viewing v JOIN v.buyer b WHERE v.agent.id = :agentId")
    List<Buyer> findBuyersOfAgent(@Param("agentId") UUID agentId);
}