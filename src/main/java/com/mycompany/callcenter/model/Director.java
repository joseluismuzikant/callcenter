
package com.mycompany.callcenter.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.callcenter.service.Dispatcher;


@Entity(name = "Director")
@DiscriminatorValue("director")
public class Director extends Employee{
	private static final Logger logger = LoggerFactory.getLogger(Director.class);
	
	public Director() {
		super();
	}
	
	public Director(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public void attendCall(Call call, Dispatcher dispatcher) {
		logger.info("Call{} attend by Director {}",call, this.name);
		super.attendCall(call);
		dispatcher.addDirector(this);
	}
}
