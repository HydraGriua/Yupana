package com.acme.yupanaapi.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Wallet;
import java.util.List;
import java.util.Optional;


public interface WalletRepository extends JpaRepository<Wallet, Integer>{
    List<Wallet> findAllBySellerId(Integer sellerId);
    Optional<Wallet> findByIdAndUserId(Integer id,Integer userId);
    Optional<Wallet> findByIdAndSellerId(Integer id,Integer sellerId);
    Boolean existsByIdAndSellerId(Integer id, Integer sellerId);
}
