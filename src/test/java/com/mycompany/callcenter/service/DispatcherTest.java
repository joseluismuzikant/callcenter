package com.mycompany.callcenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.callcenter.model.Call;
import com.mycompany.callcenter.model.Director;
import com.mycompany.callcenter.model.Operator;
import com.mycompany.callcenter.model.Supervisor;

public class DispatcherTest {

	private Dispatcher dispatcher = new Dispatcher(null,null,null); ;

	@Before
	public void initDispatcher() {
		
		dispatcher.addOperator(new Operator("operator 1"));
		dispatcher.addOperator(new Operator("operator 2"));
		
		dispatcher.addSupervisor(new Supervisor("supervisor 1"));
		dispatcher.addSupervisor(new Supervisor("supervisor 2"));

		dispatcher.addDirector(new Director("director 1"));
		dispatcher.addDirector(new Director("director 2"));
		
	}
	@Test
	public void testProcessMoreThan10Calls() throws InterruptedException {
		
		for(int i=0; i<100;i++) {
			dispatcher.asynchronousDispatchCall(new Call("call number: "+i));
		}
		
		Thread.sleep(1000);
		
		Assert.assertTrue(dispatcher.getwaitingCalls().size() == 94);
		Assert.assertTrue(dispatcher.getAvailableListeners() == 0);
	}


}
