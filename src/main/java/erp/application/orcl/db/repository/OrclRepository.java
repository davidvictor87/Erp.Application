package erp.application.orcl.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import erp.application.orcl.db.model.Model;
import org.springframework.stereotype.Repository;

@Repository
public interface OrclRepository extends JpaRepository<Model, Integer>{

}
