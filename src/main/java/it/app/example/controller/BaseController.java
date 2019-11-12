package it.app.example.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BaseController {

	private static Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@RequestMapping(value="/test/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTestValue(@PathVariable("id") Integer id, HttpServletRequest httpRequest) throws InterruptedException {

		log.info("DEMO app received GET request for URL /test/"+id);
		
		String result = "DEMO app received GET request for URL /test/"+id;
		
		return result; 

	} 
	
	@RequestMapping(value = "/exception", method= { RequestMethod.GET, RequestMethod.POST })
	public String exception() {
		
		String response = "";
		
		try {
			
			throw new Exception("Exception has occured... ");
		
		} catch (Exception e) {
			
			log.error("Error: ", e);

			StringWriter sw = new StringWriter();
			
			PrintWriter pw = new PrintWriter(sw);
			
			e.printStackTrace(pw);
			
			String stackTrace = sw.toString();
			
			log.error("Stack trace: ["+stackTrace+"]");
			
			response = stackTrace;
		
		}

		return response;
	}

} //end class
