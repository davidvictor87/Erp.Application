package erp.application.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import erp.application.h2.model.H2Model;

@Transactional
@Repository
public interface H2Repository extends JpaRepository<H2Model, Integer>{
	
	public H2Model saveH2Model(H2Model h);

}
