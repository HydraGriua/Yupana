package com.acme.yupanaapi.domain.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	List<Transaction> findAllByFlowId(int flowId);
	List<Transaction> findAllByHistorialId(int historialId);
}
