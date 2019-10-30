
package com.mycompany.callcenter.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.callcenter.service.Dispatcher;


@Entity(name = "Supervisor")
@DiscriminatorValue("supervisor")
public class Supervisor extends Employee{
	private static final Logger logger = LoggerFactory.getLogger(Supervisor.class);
	

	public Supervisor() {
		super();
	}
	
	public Supervisor(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public void attendCall(Call call, Dispatcher dispatcher) {
		logger.info("Call{} attend by Supervisor {}",call, this.name);
		super.attendCall(call);
		dispatcher.addSupervisor(this);
	}
}
