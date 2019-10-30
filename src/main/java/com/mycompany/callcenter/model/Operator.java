
package com.mycompany.callcenter.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.callcenter.service.Dispatcher;


@Entity(name = "Operator")
@DiscriminatorValue("operator")
public class Operator extends Employee{
	private static final Logger logger = LoggerFactory.getLogger(Operator.class);
	
	public Operator() {
		super();
	}
	
	public Operator(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public void attendCall(Call call, Dispatcher dispatcher) {
		logger.info("Call{} attend by Operator {}",call, this.name);
		super.attendCall(call);
		dispatcher.addOperator(this);
	}
	
}
