
package com.mycompany.callcenter.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.callcenter.service.Dispatcher;

@Entity(name = "Employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol")
public class Employee implements CallListener {

	private static final Logger logger = LoggerFactory.getLogger(Employee.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;


	@Column(name = "name", nullable = false)
	protected String name;

	public void attendCall(Call call) {
		try {

			Thread.sleep(call.duration * 1000*60);

		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void attendCall(Call call, Dispatcher dispatcher) {
		throw new UnsupportedOperationException();
	}

}
