package com.acme.yupanaapi.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Wallet;
import java.util.List;
import java.util.Optional;


public interface WalletRepository extends JpaRepository<Wallet, Integer>{
    List<Wallet> findAllBySellerId(int sellerId);
    Optional<Wallet> findByIdAndUserId(int id,int userId);
    Optional<Wallet> findByIdAndSellerId(int id,int sellerId);
    Boolean existsByIdAndSellerId(int id, int sellerId);
}
