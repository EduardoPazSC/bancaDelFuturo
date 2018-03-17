package com.citibanamex.bne.poc-d-accounttransfer.implementation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.citibanamex.bne.connector.jdbc.model.SqlStatementRequest;
import com.citibanamex.bne.connector.jdbc.model.SqlStatementResponse;
import com.citibanamex.bne.connector.jdbc.service.JdbcService;
import com.citibanamex.bne.connector.tuxedo.model.TuxedoChannel;
import com.citibanamex.bne.connector.tuxedo.model.TuxedoStatementRequest;
import com.citibanamex.bne.connector.tuxedo.model.TuxedoStatementResponse;
import com.citibanamex.bne.connector.tuxedo.service.TuxedoService;
import com.citibanamex.bne.poc-d-accounttransfer.connector.Connector;
import com.citibanamex.bne.poc-d-accounttransfer.services.ApiService;

@Service
public class ApiServiceImp implements ApiService {	
	@Override
	public SqlStatementResponse queryTest() {
		SqlStatementRequest request = new SqlStatementRequest();
		JdbcService jdbcService = Connector.services().getJdbcService();

		request.setSql("select * from BBS_THIRD_PARTY_ACCNT where CONTRACT_NUMBER=? and PRODUCT_CODE=? and  INSTRUMENT_CODE=?");
		request.setParams(new Object[] {"2791858", "0001", "01"});

		SqlStatementResponse response =  jdbcService.query(request);

		return response;
	}

	@Override
	public TuxedoStatementResponse serviceCall() {
		TuxedoStatementRequest request = new TuxedoStatementRequest();
		TuxedoStatementResponse response = null;

		TuxedoService tuxedoService = Connector.services().getTuxedoService();

		Map<String, String> body = new HashMap<String, String>();
		Map<String, String> headers = new HashMap<String, String>();

		request.setDestino(1);
		request.setChannel(TuxedoChannel.SF);
		request.setServiceName("LOGINHU_BE");
		request.setTimeOut(30);

		headers.put("I_MODORIGEN", "01");
		headers.put("I_IDIOMA", "1");
		headers.put("I_APLORIGEN", "0001");
		headers.put("I_ORIGEN", "");

		body.put("I_PASSWORD", "13SAAS01");
		body.put("EJEDOS", "00000000");
		body.put("AHNLABFLAG", "0");
		body.put("I_USUARIO", "62318289");
		body.put("IPCLIENTE", "168.72.178.27");
		body.put("BANTIPO", "4");
		body.put("FLAGLOGIN", "2");

		request.setParametersHeader(headers);
		request.setParametersBody(body);

		response = tuxedoService.service(request);

		return response;
	}
}
