package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.auca.realestate.domain.Offer;
import rw.auca.realestate.domain.Viewing;

import java.util.List;
import java.util.UUID;

public interface OfferRepository extends JpaRepository<Offer, UUID> {

    @Query("SELECT o FROM Offer o WHERE o.viewing.buyer.nationalId = :nationalId ORDER BY o.offerDate DESC")
    List<Offer> findOffersByBuyerNationalId(@Param("nationalId") String nationalId);

    boolean existsByViewing(Viewing viewing);
}