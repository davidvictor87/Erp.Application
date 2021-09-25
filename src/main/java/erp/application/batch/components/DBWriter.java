package erp.application.batch.components;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;
import erp.application.h2.model.H2Model;
import erp.application.h2.repository.H2Repository;

@Component
public class DBWriter implements ItemWriter<H2Model>{
	
	@Autowired
	private H2Repository h2Repo;

	@Override
	public void write(List<? extends H2Model> models) throws Exception {
		LOG.appLogger().info("Save data for: " + models);
		h2Repo.saveAll(models);
	}

}
