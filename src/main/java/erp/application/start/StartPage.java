package erp.application.start;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import erp.application.entities.LOG;

@RestController
@WebServlet
@ServletSecurity
public class StartPage {
	
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
	@GetMapping("/Daily-Tasks")
	public void infoUser(HttpServletResponse res, HttpServletRequest req) {
		try {
			final String getInput = req.getParameter("Daily-Tasks");
			if(getInput != null) {
			    res.sendRedirect("/d-Task");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/UserManager")
    public void RegisterUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	try {
    	    final String getInput = request.getParameter("Register");
    	    if(getInput != null) {
    		    response.sendRedirect("register.html");
    	    }
    	    LOG.appLogger().info("Redirect Succeded");
    	}catch (Exception e) {
    		e.printStackTrace();
    		LOG.appLogger().error("Redirect Process Failed");
    	}
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/DeleteUser" ,method = RequestMethod.GET)
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
    	final String input = request.getParameter("Delete");
    	try {
    	   if(input !=  null) 
    		  response.sendRedirect("/PannelUser");
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/text")
    //@ResponseBody
    public String testSecurity() {
    	return "test";
    }
    
}
