package erp.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import erp.application.employee.model.EmployeeInitialSavedData;
import erp.application.employee.repository.EmployeeInitialSavedDataRepo;
import erp.application.employee.repository.EmployeeProcessedDataRepo;
import erp.application.entities.LOG;

@Service
public class EmployeeService {

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	private Lock lock3 = new ReentrantLock();
	@Autowired
	private EmployeeInitialSavedDataRepo initRepo;
	@Autowired
	private EmployeeProcessedDataRepo processedRepo;

	public void saveInitiaInfos(EmployeeInitialSavedData employee) {
		initRepo.saveAndFlush(employee);
	}

	public void calculateTaxes() {
		final int numberOfThreads = 3;
		LOG.appLogger().info("Current thread: " + Thread.currentThread().getName());
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		try {
			Runnable runnable = () -> {
				calculateCasTax();
				calculateCassTax();
				calculeteIncomeTax();
			};
			executor.submit(runnable);
			executor.shutdown();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private void preventDeadLock(Lock firstLock, Lock secondLock, Lock thirdlock) throws InterruptedException{
            while(true) {
            	boolean deadlock1 = false;
            	boolean deadlock2 = false;
            	boolean deadlock3 = false;
            	try {
                    deadlock1 = firstLock.tryLock();
                    if(deadlock1 && deadlock2 && deadlock3) {
                    	return;
                    }
                    if(deadlock1) {
                       firstLock.unlock();
                    }
                    if(deadlock2) {
                    	secondLock.unlock();
                    }
                    if(deadlock3) {
                    	thirdlock.unlock();
                    }
            	}finally {
            		Thread.sleep(1);
            	}
            }
	}

	public List<Double> calculateCasTax() {
		List<Double> casTaxList = null;
		try {
			preventDeadLock(lock1, lock2, lock3);
			casTaxList = initRepo.findAll().stream().map(employeeCAS -> employeeCAS.getSalary() * 25 / 100)
					.collect(Collectors.toList());
		}catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}finally {
			lock1.unlock();
		}
		System.out.println(casTaxList);
		return casTaxList;
	}

	public List<Double> calculateCassTax() {
		List<Double> cassTaxList = null;
		try {
			preventDeadLock(lock1, lock2, lock3);
			cassTaxList = initRepo.findAll().stream().map(employeeCASS -> employeeCASS.getSalary() * 10 / 100)
					.collect(Collectors.toList());
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		} finally {
			lock2.unlock();
		}
		System.out.println(cassTaxList);
		return cassTaxList;
	}

	public List<Double> calculeteIncomeTax() {
		List<Double> incomeTaxList = null;
		try {
			preventDeadLock(lock1, lock2, lock3);
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

	public void find() {
		System.out.println(initRepo.findAll());
	}

}
