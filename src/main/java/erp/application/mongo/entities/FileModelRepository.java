package erp.application.mongo.entities;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import erp.application.entities.models.FileModel;

@Component
public interface FileModelRepository extends ReactiveCrudRepository<FileModel, Integer>{

}
