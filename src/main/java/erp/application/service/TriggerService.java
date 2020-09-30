package erp.application.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import erp.application.entities.LOG;
import erp.application.login.repository.LevelRepository;
import erp.application.login.repository.UserRepository;
import erp.application.login.repository.UserRoleRepository;

@Service
@Transactional
public class TriggerService {

	@Autowired
	private LevelRepository levelRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserRoleRepository userRoleRepo;

	public final void deleteUser(long objectId) throws InterruptedException, ExecutionException {
		CompletableFuture.supplyAsync(() -> {
			try {
				LOG.appLogger().info("Parameter Received: " + objectId + " Thread Name: " + Thread.currentThread().getName());
				return objectId;
			} catch (Exception e) {
				LOG.appLogger().debug(e.getLocalizedMessage());
				throw new IllegalStateException(e.getMessage());
			}
		}).thenApplyAsync(deleteUserId -> {
			try {
				userRepo.deleteById(deleteUserId);
				LOG.appLogger().info("Parameter Received: " + deleteUserId + " Thread Name: " + Thread.currentThread().getName());
				return deleteUserId;
			} catch (Exception e) {
				LOG.appLogger().debug(e.getLocalizedMessage());
				throw new IllegalStateException(e.getMessage());
			}
		}).thenApplyAsync(deleteLevelId -> {
			try {
				levelRepo.deleteById(deleteLevelId);
				LOG.appLogger().info("Parameter Received: " + deleteLevelId + " Thread Name: " + Thread.currentThread().getName());
				return deleteLevelId;
			} catch (Exception e) {
				LOG.appLogger().debug(e.getLocalizedMessage());
				throw new IllegalStateException(e.getMessage());
			}
		}).thenApplyAsync(deleteUserRoleId -> {
			try {
				final String deleteId = String.valueOf(deleteUserRoleId);
				userRoleRepo.deleteByUserId(deleteId);
				LOG.appLogger().info("Parameter Received: " + deleteUserRoleId + " Thread Name: "+ Thread.currentThread().getName());
				return deleteUserRoleId;
			} catch (Exception e) {
				LOG.appLogger().debug(e.getLocalizedMessage());
				throw new IllegalStateException(e.getMessage());
			}
		}).get();
	}

}
