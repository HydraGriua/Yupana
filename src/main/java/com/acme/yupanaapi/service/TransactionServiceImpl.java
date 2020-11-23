package com.acme.yupanaapi.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Historial;
import com.acme.yupanaapi.domain.model.Sale;
import com.acme.yupanaapi.domain.repository.FlowRepository;
import com.acme.yupanaapi.domain.repository.HistorialRepository;
import com.acme.yupanaapi.domain.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.comparator.Comparators;
import java.util.Comparator;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.repository.TransactionRepository;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.exception.LockedActionException;
import com.acme.yupanaapi.exception.ResourceNotFoundException;
import com.acme.yupanaapi.resource.InfoFilter;
import com.acme.yupanaapi.resource.UserWalletResource;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	FlowRepository flowRepository;

	@Autowired
	SaleRepository saleRepository;

	@Autowired
	HistorialRepository historialRepository;

	@Transactional
	@Override

	public Transaction createTransaction(Transaction transactionEntity, int flowId) {
		// Obtenemos el flow al que pertenecera
		Flow flow = flowRepository.findById(flowId)
				.orElseThrow(() -> new ResourceNotFoundException("Flow not found with Id " + flowId));
		if (flow.getCurrentCreditLine() - transactionEntity.getAmount() >= 0) {
			// Actualizamos los datos del flow
			String tipo = flow.getCurrentRateType();
			Float newQuant;
			int dias = (int) ((transactionEntity.getTransactionDate().getTime()
					- flow.getLastTransactionDate().getTime()) / 86400000);
			switch (tipo) {
			case "Simple":
				newQuant = flow.getTotalDebt() * (1 + (flow.getCurrentInterestRate() * dias));
				flow.setTotalDebt(newQuant + transactionEntity.getAmount());
				break;
			case "Nominal":
				newQuant = flow.getTotalDebt() * (float) Math.pow(
						(1 + (flow.getCurrentInterestRate()
								/ ((float) flow.getCurrentRatePeriod() / flow.getCurrentCapitalization()))),
						((float) dias / flow.getCurrentCapitalization()));
				flow.setTotalDebt(newQuant + transactionEntity.getAmount());
				break;
			case "Efectiva":
				newQuant = flow.getTotalDebt() * (float) Math.pow((1 + flow.getCurrentInterestRate()),
						(float) (dias / flow.getCurrentRatePeriod()));
				flow.setTotalDebt(newQuant + transactionEntity.getAmount());
			}
			flow.setLastTransactionDate(transactionEntity.getTransactionDate());
			flow.setCurrentInterestRate(transactionEntity.getInterestRate());
			flow.setCurrentRatePeriod(transactionEntity.getRatePeriod());
			flow.setCurrentRateType(transactionEntity.getRateType());
			flow.setCurrentCapitalization(transactionEntity.getCapitalization());
			flow.setCurrentCreditLine(flow.getCurrentCreditLine() - transactionEntity.getAmount());
			flowRepository.save(flow);
			// guardamos la transaccion
			transactionEntity.setFlow(flow);
			List<Historial> lista = historialRepository.findBySellerId(flow.getWallet().getSeller().getId());
			Historial historial = lista.get(lista.size() - 1);
			transactionEntity.setHistorial(historial);
			// if(transactionEntity.getSale() != null)
			// TODO: agregar la venta en caso exista
			return transactionRepository.save(transactionEntity);
		} else {
			throw new LockedActionException("Superaste tu linea de credito");
		}
	}

	@Override
	public Transaction AssignTransactionWithSale(int transactionId, int saleId) {
		Sale sale = saleRepository.findById(saleId)
				.orElseThrow(() -> new ResourceNotFoundException("Sale not found with Id " + saleId));
		return transactionRepository.findById(transactionId).map(transaction -> {
			transaction.setSale(sale);
			return transactionRepository.save(transaction);
		}).orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
	}

	@Transactional
	@Override
	public Transaction updateTransaction(Transaction transactionEntity, int transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
		transaction.setTransactionName(transactionEntity.getTransactionName());
		transaction.setTransactionDate(transactionEntity.getTransactionDate());
		transaction.setAmount(transactionEntity.getAmount());
		transaction.setInterestRate(transactionEntity.getInterestRate());
		transaction.setCapitalization(transactionEntity.getCapitalization());
		transaction.setCurrencyType(transactionEntity.getCurrencyType());
		transaction.setRatePeriod(transactionEntity.getRatePeriod());
		transaction.setRateType(transactionEntity.getRateType());
		return transactionRepository.save(transaction);
		// TODO: verificar posible metodo con mapping
	}

	@Transactional
	@Override

	public ResponseEntity<?> deleteTransaction(int transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
		transactionRepository.delete(transaction);
		return ResponseEntity.ok().build();
	}

	@Transactional(readOnly = true)
	@Override
	public Transaction getTransactionById(int transactionId) {
		return transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Transaction> getAllByFlowId(int flowId) {
		return transactionRepository.findAllByFlowId(flowId);
	}

	@Override
	public List<Transaction> getAllByHistorialId(int historialId) {
		return transactionRepository.findAllByHistorialId(historialId);

	}

	@Override
	public List<Transaction> orderByObj(InfoFilter obj, List<Transaction> listAll) {
		List<Transaction> listAux = new ArrayList<Transaction>();
		System.out.print("Type: " + obj.getCurrencyBy() + "\n");
		System.out.print("Type: " + obj.getRegisterTypeBy() + "\n");
		System.out.print("Type: " + obj.getRateTypeBy() + "\n");

		for (Transaction userWaR : listAll) {
			System.out.print("\n" + "Type3 INICIO: " + userWaR + "\n");
			System.out.print("Type: " + userWaR.getRateType() + "\n");
			System.out.print("Type PAYMENT: " + userWaR.getSale().getPaymentPay() + "\n");
			// System.out.print("descripcion: " + userWaR.getSale().getDescription()+"\n");
			if (userWaR.getSale().getPaymentPay().equals("Contado") && obj.getPayMethodBy().equals("payed")) {
				if (userWaR.getCurrencyType().toString().equals(obj.getCurrencyBy())) {
					System.out.print("Type1: " + obj.getRateTypeBy() + "\n");
					if (obj.getRegisterTypeBy().equals("all")) {
						System.out.print("Type2: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getSale().getDescription().equals("Tasa de Interés Simple")) {
							System.out.print("TSType3: " + userWaR.getRateType() + "\n");
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							System.out.print("TSType3holl: " + userWaR.getRateType() + "\n");
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("sell")
							&& userWaR.getSale().getDescription().equals("Registro de venta al crédito")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("delivery")
							&& userWaR.getSale().getDescription().equals("Registro de venta al credito con Delivery")) {
						System.out.print("Type5: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("mantenimiento")
							&& userWaR.getSale().getDescription().equals("Mantenimiento de cuenta")) {
						System.out.print("Type4: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("suscripciond")
							&& userWaR.getSale().getDescription().equals("Suscripción a Delivery")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("amortizaciones")
							&& userWaR.getSale().getDescription().equals("Amortizaciones de deuda")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					}
				}
			} else if (userWaR.getSale().getPaymentPay().equals("Credito") && obj.getPayMethodBy().equals("duetoPay")) {
				if (userWaR.getCurrencyType().toString().equals(obj.getCurrencyBy())) {
					System.out.print("Type1: " + obj.getRateTypeBy() + "\n");
					if (obj.getRegisterTypeBy().equals("all")) {
						System.out.print("Type2: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getSale().getDescription().equals("Tasa de Interés Simple")) {
							System.out.print("TSType3: " + userWaR.getRateType() + "\n");
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							System.out.print("TSType3holl: " + userWaR.getRateType() + "\n");
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("sell")
							&& userWaR.getSale().getDescription().equals("Registro de venta al crédito")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("delivery")
							&& userWaR.getSale().getDescription().equals("Registro de venta al credito con Delivery")) {
						System.out.print("Type5: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("mantenimiento")
							&& userWaR.getSale().getDescription().equals("Mantenimiento de cuenta")) {
						System.out.print("Type4: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("suscripciond")
							&& userWaR.getSale().getDescription().equals("Suscripción a Delivery")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("amortizaciones")
							&& userWaR.getSale().getDescription().equals("Amortizaciones de deuda")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					}
				}
			} else if (obj.getPayMethodBy().equals("all")) {
				if (userWaR.getCurrencyType().toString().equals(obj.getCurrencyBy())) {
					System.out.print("Type1: " + obj.getRateTypeBy() + "\n");
					if (obj.getRegisterTypeBy().equals("all")) {
						System.out.print("Type2: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getSale().getDescription().equals("Tasa de Interés Simple")) {
							System.out.print("TSType3: " + userWaR.getRateType() + "\n");
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							System.out.print("TSType3holl: " + userWaR.getRateType() + "\n");
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("sell")
							&& userWaR.getSale().getDescription().equals("Registro de venta al crédito")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("delivery")
							&& userWaR.getSale().getDescription().equals("Registro de venta al credito con Delivery")) {
						System.out.print("Type5: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("mantenimiento")
							&& userWaR.getSale().getDescription().equals("Mantenimiento de cuenta")) {
						System.out.print("Type4: " + obj.getRateTypeBy() + "\n");
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("suscripciond")
							&& userWaR.getSale().getDescription().equals("Suscripción a Delivery")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);
						}
					} else if (obj.getRegisterTypeBy().equals("amortizaciones")
							&& userWaR.getSale().getDescription().equals("Amortizaciones de deuda")) {
						if (obj.getRateTypeBy().equals("all")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("ts")
								&& userWaR.getRateType().equals("Tasa de Interés Simple")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("tc")
								&& userWaR.getRateType().equals("Tasa de Interés Compuesta")) {
							listAux.add(userWaR);
						} else if (obj.getRateTypeBy().equals("te")
								&& userWaR.getRateType().equals("Tasa de Interés Efectiva")) {
							listAux.add(userWaR);

						}
					}
				}
			}
		}
		// ORDENAR//
		if (obj.getOrderBy().equals("dateLasts")) {
			System.out.print("Type: dateLasts");
			listAux.sort(Comparator.comparing(Transaction::getTransactionDate));
			for(Transaction x: listAux) {
				System.out.print("\n Fecha 1;"+x.getTransactionDate()+"\n");
			}
		} else if (obj.getOrderBy().equals("dateFirsts")) {
			System.out.print("Type: dateLasts");
			listAux.sort(Comparator.comparing(Transaction::getTransactionDate).reversed());
			for(Transaction x: listAux) {
				System.out.print("\n Fecha 1;"+x.getTransactionDate()+"\n");
			}
		} else if (obj.getOrderBy().equals("deudaMayor")) {
			listAux.sort(Comparator.comparing(Transaction::getAmount).reversed());

		} else if (obj.getOrderBy().equals("deudaMenor")) {
			listAux.sort(Comparator.comparing(Transaction::getAmount));
		}
		System.out.print("\n FECHA START"+obj.getDateStart()+"\n");
		System.out.print("\n FECHA START"+obj.getDateEnd()+"\n");
		
//		String string = obj.getDateStart();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
//		LocalDate dateStart = LocalDate.parse(string, formatter);
//		String stringT = obj.getDateEnd();
//		DateTimeFormatter formatterR = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
//		LocalDate dateEnd = LocalDate.parse(string, formatterR);
//		if(dateStart != null) {
//			List<Transaction> transaction = new ArrayList<>();
//			for(Transaction traL : listAux) {
////				dateStart.datesUntil(dateEnd).collect(listAux);
////				traL.getTransactionDate().after(dateStart);
//			}
//		}
		
		//System.out.println(date); // 2010-01-02
		
		return listAux;
	}
}
