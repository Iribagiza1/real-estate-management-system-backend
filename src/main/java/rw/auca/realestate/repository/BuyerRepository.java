package rw.auca.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.realestate.domain.Buyer;

import java.util.List;
import java.util.UUID;

public interface BuyerRepository extends JpaRepository<Buyer, UUID> {

    List<Buyer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}