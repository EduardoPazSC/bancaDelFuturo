package com.citibanamex.bne.poc-p-auth.connector;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.citibanamex.bne.connector.jdbc.service.JdbcService;
import com.citibanamex.bne.connector.tuxedo.service.TuxedoService;

@Component
public class Connector implements ConnectorService, ApplicationContextAware {
	private static ApplicationContext applicationContext;
	
	@Autowired
	private JdbcService jdbcService;
	
	@Autowired
	private TuxedoService tuxedoService;
	
	@Override
	public JdbcService getJdbcService() {
		return jdbcService;
	}
	
	@Override
	public TuxedoService getTuxedoService() {
		return tuxedoService;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {		
		Connector.applicationContext = applicationContext;
	}
	
	/**
     * A static method to lookup the SpringContextBridgedServices Bean in 
     * the applicationContext. It is basically an instance of itself, which 
     * was registered by the @Component annotation.
     *
     * @return the SpringContextBridgedServices, which exposes all the 
     * Spring services that are bridged from the Spring context.
     */
    public static ConnectorService services() {
        return applicationContext.getBean(ConnectorService.class);
    }
}
