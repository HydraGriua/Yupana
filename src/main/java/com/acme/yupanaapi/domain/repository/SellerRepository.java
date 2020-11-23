package com.acme.yupanaapi.domain.repository;
import com.acme.yupanaapi.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Integer> {

    Optional<Seller> findByIdAndUserId(int id, int userId);
    Optional<Seller> findByBusinessName(String businessName);
    Optional<Seller> findByEmail(String email);
}
