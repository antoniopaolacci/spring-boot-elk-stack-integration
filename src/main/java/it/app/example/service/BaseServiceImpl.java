package it.app.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {

	private static Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

	public String call(int id) throws InterruptedException {

		log.info("Service with with id: ["+id+"] execute.");
		
		return "Service with with id: ["+id+"] execute.";

	}

}
