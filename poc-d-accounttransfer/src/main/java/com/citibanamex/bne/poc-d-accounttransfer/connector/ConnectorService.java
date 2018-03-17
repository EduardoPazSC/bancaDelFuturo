package com.citibanamex.bne.poc-d-accounttransfer.connector;

import com.citibanamex.bne.connector.jdbc.service.JdbcService;
import com.citibanamex.bne.connector.tuxedo.service.TuxedoService;

public interface ConnectorService { 
	public JdbcService getJdbcService();
	public TuxedoService getTuxedoService();
}
