/**
 * 
 */
package com.practice.microservices.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.microservices.currencyexchangeservice.models.ExchangeValue;

/**
 * @author Ashish.Kumar
 *
 */
@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

	ExchangeValue findByFromAndTo(String from, String to);

}
