package erp.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import erp.application.employee.model.EmployeeInitialSavedData;
import erp.application.employee.model.EmployeeProcessedData;
import erp.application.employee.repository.EmployeeInitialSavedDataRepo;
import erp.application.employee.repository.EmployeeProcessedDataRepo;
import erp.application.employee.repository.TaxesRepository;
import erp.application.entities.LOG;

@Service
public class EmployeeService {

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	private Lock lock3 = new ReentrantLock();
	public static double totaxTax = 0.0;
	@Autowired
	private EmployeeInitialSavedDataRepo initRepo;
	@Autowired
	private EmployeeProcessedDataRepo processedRepo;
	@Autowired
	private TaxesRepository tRepository;

	public void saveInitiaInfos(EmployeeInitialSavedData employee) {
		initRepo.saveAndFlush(employee);
	}
	
	public void saveProcessedInfos(EmployeeProcessedData employee) {
		processedRepo.save(employee);
	}

	public List<Double> calculateTaxes() {
		final int numberOfThreads = 3;
		LOG.appLogger().info("Current thread: " + Thread.currentThread().getName());
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		List<Double> totalTaxes = new ArrayList<>();
		try {
			Callable<List<Double>> callable = () -> {
				for (int i = 0; i < calculateCasTax().size(); i++) {
					totalTaxes.add(calculateCasTax().get(i) + calculateCassTax().get(i) + calculeteIncomeTax().get(i));
				}
				return totalTaxes;
			};
			Future<List<Double>> future = executor.submit(callable);
			future.get();
			executor.shutdown();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return totalTaxes;
	}

	public void preventDeadLock(Lock firstLock, Lock secondLock, Lock thirdlock) throws InterruptedException {
		while (true) {
			boolean deadlock1 = false;
			boolean deadlock2 = false;
			boolean deadlock3 = false;
			try {
				deadlock1 = firstLock.tryLock();
				if (deadlock1 && deadlock2 && deadlock3) {
					return;
				}
				if (deadlock1) {
					firstLock.unlock();
				}
				if (deadlock2) {
					secondLock.unlock();
				}
				if (deadlock3) {
					thirdlock.unlock();
				}
			} finally {
				Thread.sleep(1);
			}
		}
	}

	public List<Double> calculateCasTax() {
		List<Double> casTaxList = null;
		lock1.lock();
		try {
			casTaxList = initRepo.findAll().stream().map(employeeCAS -> employeeCAS.getSalary() * 25 / 100)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			lock1.unlock();
		}
		System.out.println(casTaxList);
		return casTaxList;
	}

	public List<Double> calculateCassTax() {
		List<Double> cassTaxList = null;
		lock2.lock();
		try {
			cassTaxList = initRepo.findAll().stream().map(employeeCASS -> employeeCASS.getSalary() * 10 / 100)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			lock2.unlock();
		}
		System.out.println(cassTaxList);
		return cassTaxList;
	}

	public List<Double> calculeteIncomeTax() {
		List<Double> incomeTaxList = null;
		lock3.lock();
		try {
			incomeTaxList = initRepo.findAll().stream().map(employeeIncome -> employeeIncome.getSalary() * 10 / 100)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			lock3.unlock();
		}
		System.out.println(incomeTaxList);
		return incomeTaxList;
	}

	public List<EmployeeInitialSavedData> findAll() {
		return initRepo.findAll();
	}
	
	public void printTaxes() {
		System.out.println(tRepository.findAll());
	}

	public double totalTaxes(double... taxes) {
		double sum = 0.0;
		for (double total : taxes) {
			sum += total;
		}
		return sum;
	}

}
