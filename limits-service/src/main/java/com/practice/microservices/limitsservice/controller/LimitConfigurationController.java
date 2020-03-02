/**
 * 
 */
package com.practice.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.practice.microservices.limitsservice.configuration.Configuration;
import com.practice.microservices.limitsservice.models.LimitConfiguration;

/**
 * @author Ashish.Kumar
 *
 */
@RestController
public class LimitConfigurationController {

	@Autowired
	private Configuration configuration;

	@RequestMapping(value = "/limits", method = RequestMethod.GET)
	public LimitConfiguration retrieveConfiguration() {
		return new LimitConfiguration(configuration.getMinimum(), configuration.getMaximum());
	}
}
