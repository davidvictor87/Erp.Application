package erp.application.batch.trigger;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.batch.operations.JobRestartException;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Component
public class BatchTrigger {
	
	private JobLauncher jobLauncher;
	private Job job;
	
	@Autowired
	public BatchTrigger(JobLauncher jl, Job j) {
		this.jobLauncher = jl;
		this.job = j;
	}
	
	@PostConstruct
	public BatchStatus load () throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobRestartException, org.springframework.batch.core.repository.JobRestartException {
		Map<String, JobParameter> batchMaps = new HashMap<>();
		batchMaps.put("RECORD TIME: ", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(batchMaps);
	  JobExecution jobExecution = jobLauncher.run(job, parameters);
	  LOG.appLogger().info("JOB STATUS: " + jobExecution.getStatus().toString());
		return jobExecution.getStatus();
	}

}
