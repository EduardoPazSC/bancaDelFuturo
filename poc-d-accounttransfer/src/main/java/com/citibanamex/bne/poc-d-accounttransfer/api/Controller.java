package com.citibanamex.bne.poc-d-accounttransfer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citibanamex.bne.connector.jdbc.model.SqlStatementResponse;
import com.citibanamex.bne.connector.tuxedo.model.TuxedoStatementResponse;
import com.citibanamex.bne.poc-d-accounttransfer.services.ApiService;

@RestController
@RequestMapping("/v1")
public class Controller {
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private ApiService apiService;

	@RequestMapping(value = "/generic/queryTest", method = RequestMethod.POST)
	public ResponseEntity<SqlStatementResponse> queryTest() {
		logger.info("Calling direct query service");
	
		SqlStatementResponse output = apiService.queryTest();
	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@RequestMapping(value = "/generic/tuxedoTest", method = RequestMethod.POST)
	public ResponseEntity<TuxedoStatementResponse> tuxedoTest(){
		logger.info("Calling tuxedo service");

		TuxedoStatementResponse output = apiService.serviceCall();

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}
