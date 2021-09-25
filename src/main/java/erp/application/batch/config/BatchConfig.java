package erp.application.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import erp.application.h2.model.H2Model;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	private static final String [] NAMES = {"id", "name", "profession", "salary", "isEnabled"};
	
	@Bean
	public Job job(JobBuilderFactory jobFactory, StepBuilderFactory stepFactory, ItemReader<H2Model> itemReader, 
			ItemProcessor<H2Model, H2Model> itemProcessor, ItemWriter<H2Model> itemWriter) {
		Step step = stepFactory.get("ETL-File-Load")
				.<H2Model, H2Model>chunk(100).reader(itemReader).processor(itemProcessor).writer(itemWriter).build();
		Job job = jobFactory.get("ETL-Load").incrementer(new RunIdIncrementer())
		.start(step).build();
		return job;
	}
	
	@Bean
	public FlatFileItemReader<H2Model> fileItemReader(@Value("${input}") Resource resource){
		FlatFileItemReader<H2Model> flatFile = new FlatFileItemReader<H2Model>();
		flatFile.setResource(resource);
		flatFile.setName("File-Reader");
		flatFile.setLinesToSkip(1);
		flatFile.setLineMapper(lineMapper());
		return flatFile;
	}

	@Bean
	public LineMapper<H2Model> lineMapper() {
		DefaultLineMapper<H2Model> defaultMapper = new DefaultLineMapper<H2Model>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(NAMES);
		BeanWrapperFieldSetMapper<H2Model> fieldMapper = new BeanWrapperFieldSetMapper<H2Model>();
		fieldMapper.setTargetType(H2Model.class);
		defaultMapper.setLineTokenizer(lineTokenizer);
		defaultMapper.setFieldSetMapper(fieldMapper);
		return defaultMapper;
	}

}
