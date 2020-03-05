/**
 * 
 */
package com.practice.microservices.currencyconversionservice.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.practice.microservices.currencyconversionservice.feigns.CurrencyExchangeProxy;
import com.practice.microservices.currencyconversionservice.models.CurrencyConversionBean;

/**
 * @author Ashish.Kumar
 *
 */
@RestController
public class CurrencyConversionController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	@RequestMapping(value = "/currency-converter/from/{from}/to/{to}/quantity/{quantity}", method = RequestMethod.GET)
	public CurrencyConversionBean convertCurrency(@PathVariable("from") String from, @PathVariable("to") String to,
			@PathVariable("quantity") BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = restTemplate.getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);

		CurrencyConversionBean currencyConversionBean = responseEntity.getBody();
		currencyConversionBean.setTotalAmount(
				quantity.multiply(currencyConversionBean.getConversionMultiple()));
		currencyConversionBean.setPort(currencyConversionBean.getPort());
		currencyConversionBean.setQuantity(quantity);

		/*
		 * return new CurrencyConversionBean(currencyConversionBean.getId(), from, to,
		 * currencyConversionBean.getConversionMultiple(), quantity,
		 * currencyConversionBean.getConversionMultiple().multiply(quantity),
		 * Integer.parseInt(environment.getProperty("local.server.port")));
		 */
		return currencyConversionBean;
	}
	
	@RequestMapping(value = "/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}", method = RequestMethod.GET)
	public CurrencyConversionBean convertCurrencyUsingFeign(@PathVariable("from") String from, @PathVariable("to") String to,
			@PathVariable("quantity") BigDecimal quantity) {

		CurrencyConversionBean currencyConversionBean = proxy.retrieveExchangeValue(from, to);
		currencyConversionBean.setTotalAmount(
				quantity.multiply(currencyConversionBean.getConversionMultiple()));
		currencyConversionBean.setPort(currencyConversionBean.getPort());
		currencyConversionBean.setQuantity(quantity);

		return currencyConversionBean;
	}
}
