package com.mycompany.callcenter.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mycompany.callcenter.exception.ListenerNotFoundException;
import com.mycompany.callcenter.model.Call;
import com.mycompany.callcenter.model.CallListener;
import com.mycompany.callcenter.model.Director;
import com.mycompany.callcenter.model.Operator;
import com.mycompany.callcenter.model.Supervisor;
import com.mycompany.callcenter.repository.DirectorRepository;
import com.mycompany.callcenter.repository.OperatorRepository;
import com.mycompany.callcenter.repository.SupervisorRepository;

/**
 * Handles multiples calls among the available employees
 */
@Service
public class Dispatcher {

	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	private static final Integer EMPLOYEES_LIMIT = 1000;
	private static final Integer CALLS_LIMIT = 1000;


	public Dispatcher(@Autowired OperatorRepository operatorRepository,@Autowired SupervisorRepository supervisorRepository,
			@Autowired DirectorRepository directorRepository) {
		super();
		this.operatorRepository = operatorRepository;
		this.supervisorRepository = supervisorRepository;
		this.directorRepository = directorRepository;
	}
	

	private OperatorRepository operatorRepository;
	private SupervisorRepository supervisorRepository;
	private DirectorRepository directorRepository;

	Queue<CallListener> operators = new LinkedList<CallListener>();
	Queue<CallListener> supervisors = new LinkedList<CallListener>();
	Queue<CallListener> directors = new LinkedList<CallListener>();
	Queue<Call> waitingCalls = new LinkedList<Call>();

	ExecutorService executorService = Executors.newFixedThreadPool(10);

	@PostConstruct
	public void loadCallListeners() {
		//TODO:use Mock in the Unit test instead of that
		
		if(operatorRepository!=null) {
			operators.addAll(operatorRepository.findAll());
		}
		
		if(supervisorRepository!=null) {
			supervisors.addAll(supervisorRepository.findAll());
		}
		if(directorRepository!=null) {
			directors.addAll(directorRepository.findAll());
		}
	}
	
	/**
	 * Where there are more than 10 concurrent calls, the call will be put 
	 * in the executorService thread queue
	 * @param call
	 */
	public void asynchronousDispatchCall(Call call) {
		executorService.submit(() -> {
			try {
				dispatchCall(call);
			} catch (ListenerNotFoundException e) {
				logger.error(e.getMessage());
				if (waitingCalls.size() < CALLS_LIMIT) {
					waitingCalls.add(call);
				}
			}
		});
	}
	
	/**
	 * When there arent any employee available the call will put on a waiting queue
	 * 
	 * @param call
	 * @return
	 * @throws ListenerNotFoundException
	 */
	public CallListener dispatchCall(Call call) throws ListenerNotFoundException {

		CallListener callListener = operators.poll();

		if (callListener == null) {
			callListener = supervisors.poll();
		}

		if (callListener == null) {
			callListener = directors.poll();
		}

		if (callListener == null) {
			throw new ListenerNotFoundException("Not employee availalble");
		}

		callListener.attendCall(call,this);

		return callListener;
	}
	
	/**
	 * Each second it verifies if there are waiting calls and dispatches them
	 */
	@Scheduled(fixedDelay = 1000)
	public void processWaitingCalls() {
		if (waitingCalls.size() > 0) {
			asynchronousDispatchCall(waitingCalls.remove());
		}
	}

	public List<Call> getwaitingCalls() {
		return new ArrayList<Call>(this.waitingCalls);
	}
	
	public int getAvailableListeners() {
		return this.operators.size() + this.supervisors.size()+this.directors.size();
	}

	public void addOperator(Operator operator) {
		if (this.operators.size() < EMPLOYEES_LIMIT) {
			this.operators.add(operator);
		}
	}

	public void addSupervisor(Supervisor supervisor) {
		if (this.supervisors.size() < EMPLOYEES_LIMIT) {
			this.supervisors.add(supervisor);
		}
	}

	public void addDirector(Director director) {
		if (this.directors.size() < EMPLOYEES_LIMIT) {
			this.directors.add(director);
		}
	}

}
