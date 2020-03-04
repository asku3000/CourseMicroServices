/**
 * 
 */
package com.practice.microservices.currencyexchangeservice.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.practice.microservices.currencyexchangeservice.models.ExchangeValue;
import com.practice.microservices.currencyexchangeservice.repository.ExchangeValueRepository;

/**
 * @author Ashish.Kumar
 *
 */
@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepository exchangeRepository;

	@RequestMapping(value = "/currency-exchange/from/{from}/to/{to}", method = RequestMethod.GET)
	public ExchangeValue retrieveExchange(@PathVariable("from") String from, @PathVariable("to") String to) {
		
		ExchangeValue exchangeValue = exchangeRepository.findByFromAndTo(from, to);
		//new ExchangeValue(1000L, "USD", "INR", BigDecimal.valueOf(70));
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port").toString()));
		return exchangeValue;
	}

}
