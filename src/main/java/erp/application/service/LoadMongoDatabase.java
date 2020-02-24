package erp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import erp.application.entities.models.FileModel;
import erp.application.mongo.entities.FileModelRepository;
import reactor.core.publisher.Flux;

@Service
public class LoadMongoDatabase {
	
	@Autowired
	private FileModelRepository repository;

	public CommandLineRunner init(FileModel fileModel) {
		return args -> {
			Flux.just(new FileModel()).flatMap(repository::save).subscribe(System.out::println);
		};
	}

}
