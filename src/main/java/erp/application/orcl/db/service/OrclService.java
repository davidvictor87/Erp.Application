package erp.application.orcl.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import erp.application.orcl.db.model.Model;
import erp.application.orcl.db.repository.OrclRepository;

@Service
public class OrclService {
	
	@Autowired
	private OrclRepository orclRepo;
	
	public void saveservice(int id, String name) {
		orclRepo.save(new Model(id, name));
	}

}
