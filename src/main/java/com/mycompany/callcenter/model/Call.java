
package com.mycompany.callcenter.model;

import java.util.Random;

public class Call {

	String number;
	Integer duration;

	public Call(String number) {
		super();
		this.number = number;
		
		// A random duration between 5 and 10 minutes
		this.duration =  new Random().nextInt(5) + 5;
	}

	public String getNumber() {
		return number;
	}

	public Integer getDuration() {
		return duration;
	}

	@Override
	public String toString() {
		return "Call [number=" + number + ", duration=" + duration + "]";
	}

}
