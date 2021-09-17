package erp.application.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import erp.application.h2.model.H2Model;

@NoRepositoryBean
@Repository
public interface H2Repository extends JpaRepository<H2Model, Integer>{
	
	public H2Model saveH2Model(H2Model h);

}
