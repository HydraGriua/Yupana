package com.acme.yupanaapi.domain.repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	List<Transaction> findAllByFlowId(int flowId);
	List<Transaction> findAllBySellerId(int sellerId);
	List<Transaction> findAllByTransactionDate(Date saleDate);
}
