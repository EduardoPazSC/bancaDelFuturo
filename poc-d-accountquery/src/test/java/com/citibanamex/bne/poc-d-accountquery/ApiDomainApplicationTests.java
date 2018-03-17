package com.citibanamex.bne.poc-d-accountquery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.context.annotation.ComponentScan;

import com.citibanamex.bne.poc-d-accountquery.ApiDomainApplication;

@ComponentScan("com.citibanamex.bne")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiDomainApplication.class)
public class ApiDomainApplicationTests {

	@Test
	public void contextLoads() {
	}

}
