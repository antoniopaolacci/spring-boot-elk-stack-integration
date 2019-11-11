package it.app.example.controller;

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
	
	@RequestMapping(value="/test/{id}", method = RequestMethod.GET)
	public String getTestValue(@PathVariable("id") Integer id, HttpServletRequest httpRequest) throws InterruptedException {

		log.info("DEMO App received GET request for URL /test/%s", id);
		
		String result = "DEMO App received GET request for URL /test/"+id;
		
		return result; 

	} //end method

}
