package com.mycompany.callcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.callcenter.exception.EmployeeNotFoundException;
import com.mycompany.callcenter.exception.ListenerNotFoundException;
import com.mycompany.callcenter.model.Call;
import com.mycompany.callcenter.model.Director;
import com.mycompany.callcenter.model.Employee;
import com.mycompany.callcenter.model.Operator;
import com.mycompany.callcenter.model.Supervisor;
import com.mycompany.callcenter.repository.DirectorRepository;
import com.mycompany.callcenter.repository.EmployeeRepository;
import com.mycompany.callcenter.repository.OperatorRepository;
import com.mycompany.callcenter.repository.SupervisorRepository;
import com.mycompany.callcenter.service.Dispatcher;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The type CallCenter controller.
 *
 */
@Api(value = "/api/v1", description = "Api for employees and calls")
@RestController()
@RequestMapping("/api/v1")
public class CallCenterController {

	@Autowired
	private Dispatcher dispatcher;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private OperatorRepository operatorRepository;

	@Autowired
	private SupervisorRepository supervisorRepository;

	@Autowired
	private DirectorRepository directorRepository;
	
	
	/**
	 * Post a call into the call center
	 */
	@ApiOperation(value = "Post a call into the call center.", responseContainer = "Call")
	@PostMapping("/incomingcall/{number}")
	public void incomingCall(@PathVariable(value = "number") String number) {
		dispatcher.asynchronousDispatchCall(new Call(number));

	}

	/**
	 * Get all waiting calls.
	 */
	@ApiOperation(value = "Get all waiting calls", responseContainer = "List<Call>")
	@GetMapping("/waitingcalls")
	public List<Call> getAllWitingCalls() {
		return dispatcher.getwaitingCalls();
	}

	/**
	 * Get all employees list.
	 */
	@ApiOperation(value = "Get all employees list.", responseContainer = "List<Employee>")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	/**
	 * Gets employees by id.
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the employees by id
	 * @throws ListenerNotFoundException
	 *             the resource not found exception
	 */
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeesById(@PathVariable(value = "id") Long employeeId) throws EmployeeNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found on :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	/**
	 * Create operator.
	 */
	@PostMapping("/operator")
	public Employee createOperator(@Valid @RequestBody Operator operator) {
		Operator newOperator = operatorRepository.save(operator);
		dispatcher.addOperator(newOperator);
		return newOperator;
		
	}
	
	/**
	 * Create supervisor.
	 */
	@PostMapping("/supervisor")
	public Employee createSupervisor(@Valid @RequestBody Supervisor supervisor) {
		Supervisor newSupervisor = supervisorRepository.save(supervisor);
		dispatcher.addSupervisor(newSupervisor);
		return newSupervisor;
		
	}
	
	/**
	 * Create director.
	 */
	@PostMapping("/director")
	public Employee createDirector(@Valid @RequestBody Director director) {
		Director newDirector = directorRepository.save(director);
		dispatcher.addDirector(newDirector);
		return newDirector;
		
	}
	
	/**
	 * Update employee response entity.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param employeeDetails
	 *            the employee details
	 * @return the response entity
	 * @throws ListenerNotFoundException
	 *             the resource not found exception
	 */
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws EmployeeNotFoundException {

		Employee employee  = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found on :: " + employeeId));
		
		employee.setName(employeeDetails.getName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	/**
	 * Delete employee
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the map
	 * @throws Exception
	 *             the exception
	 */
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws Exception {
		Employee employee  = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found on :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
