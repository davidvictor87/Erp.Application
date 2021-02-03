package erp.application.start;

import java.io.IOException;
import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import erp.application.entities.LOG;
import erp.application.login.model.Users;
import erp.application.web.security.RolesAndRights;

@RestController
@WebServlet
@ServletSecurity
public class StartPage {
	
	@PreAuthorize(value="hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name(), T(erp.application.web.security.RolesAndRights).MANAGER.name(), T(erp.application.web.security.RolesAndRights).USER.name())")
	@GetMapping("/Daily-Tasks")
	public List<Users> infoUser(HttpServletResponse res, HttpServletRequest req) {
		final List<Users> allUsers = new ArrayList<Users>();
		try {
			final String getInput = req.getParameter("Daily-Tasks");
			if(getInput != null) {
			    res.sendRedirect("/daily/tasks");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return allUsers;
	}

	
    @PreAuthorize(value = "hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name())")
    @PostFilter(value="hasPermission(T(erp.application.web.security.RolesAndRights).WRITE.name())")
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
    @PostFilter(value="hasPermission('DELETE')")
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
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/text")
    @ResponseBody
    public String testSecurity() {
    	return "test";
    }
    
}
