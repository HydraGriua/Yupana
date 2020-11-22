package com.acme.yupanaapi.domain.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	List<Transaction> findAllByFlowId(Integer flowId);
	List<Transaction> findAllByHistorialId(Integer historialId);
}
