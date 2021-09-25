package erp.application.batch.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import erp.application.h2.model.H2Model;

@Component
public class Processor implements ItemProcessor<H2Model, H2Model>{
	
	private static final Map<String, String> PROFESSION_CODES = new HashMap<>();
	
	public Processor() {
		PROFESSION_CODES.put("001", "Transport");
		PROFESSION_CODES.put("002", "Warehouse");
		PROFESSION_CODES.put("004", "Technical");
		PROFESSION_CODES.put("005", "Management");
		PROFESSION_CODES.put("006", "Administrative");
		PROFESSION_CODES.put("007", "Temporary");
		PROFESSION_CODES.put("008", "Intern");
		PROFESSION_CODES.put("009", "Special");
		PROFESSION_CODES.put("010", "Other");
	}

	@Override
	public H2Model process(H2Model model) throws Exception {
		String profCode = model.getProfession();
		String prof = PROFESSION_CODES.get(profCode);
		model.setProfession(prof);
		return model;
	}

}
