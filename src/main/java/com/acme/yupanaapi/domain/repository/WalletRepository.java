package com.acme.yupanaapi.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Wallet;
import java.util.List;
import java.util.Optional;


public interface WalletRepository extends JpaRepository<Wallet, Long>{
    List<Wallet> findAllBySellerId(Long sellerId);
    Optional<Wallet> findByIdAndUserId(Long id,Long userId);
    Boolean existsByIdAndSellerId(Long id, Long sellerId);
}
