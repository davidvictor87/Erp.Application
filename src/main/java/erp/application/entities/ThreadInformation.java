package erp.application.entities;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadInformation {
	
	@Bean
	public void threadInfo() {
		final boolean lockedMonitors = true;
		final boolean lockedSynchronizers = true;
		StringBuilder sb = new StringBuilder();
		ThreadMXBean thMxBean = ManagementFactory.getThreadMXBean();
		try{
			for(ThreadInfo thread:thMxBean.dumpAllThreads(lockedMonitors, lockedSynchronizers)) {
				sb.append("{");
				sb.append("Thread Name: " + thread.getThreadName()).append(",");
				sb.append("Thread Id: " + thread.getThreadId()).append(",");
				sb.append("Thread State: " + thread.getThreadState()).append(",");
				sb.append("Lock Info: " + thread.getLockInfo()).append(",");
				sb.append("}");
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		LOG.appLogger().info("Thread Info: " + sb.toString());
	}

}
