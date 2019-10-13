package erp.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import erp.application.orcl.db.service.OrclService;

@Controller
@RequestMapping(value="/orcl")
public class OracleController {
	
	@Autowired
	private OrclService orclService;
	
	@GetMapping("/add/{id}/{name}")
	@ResponseBody
	public void addData(@PathVariable("id") int id, @PathVariable("name") String name) {
		orclService.saveservice(id, name);
	}

}
