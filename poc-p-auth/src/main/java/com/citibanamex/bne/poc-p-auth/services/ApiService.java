package com.citibanamex.bne.poc-p-auth.services;

import com.citibanamex.bne.connector.jdbc.model.SqlStatementResponse;
import com.citibanamex.bne.connector.tuxedo.model.TuxedoStatementResponse;

public interface ApiService {
	public SqlStatementResponse queryTest();
	public TuxedoStatementResponse serviceCall();
}
