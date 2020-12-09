package com.takeaway.challenge.controller;

import java.text.ParseException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.challenge.helper.EmployeeRequestBody;
import com.takeaway.challenge.helper.TakeawayResponse;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.service.IEmployeeService;
import com.takeaway.challenge.util.Constants;

@RestController
@RequestMapping(path = Constants.EMPLOYEES_BASE_URL)
public class EmployeeController {

	@Autowired
	IEmployeeService employeeService;

	@PostMapping
	public TakeawayResponse create(@RequestBody EmployeeRequestBody employeeRequest) 
			throws ParseException {
		try {
			Employee employee = employeeService.create(employeeRequest);
			return TakeawayResponse.success("created", HttpStatus.CREATED, employee);
		} catch(Exception e) {
			return TakeawayResponse.failure(Constants.EMPLOYEE_CREATE_BAD_REQUEST_RESPONSE_MESSAGE,
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(Constants.ID_URL)
	public TakeawayResponse getEmployee(@PathVariable("id") UUID id) {
		try {
			Employee employee = employeeService.getById(id);
			return TakeawayResponse.success(Constants.EMPLOYEE_FOUND_MESSAGE, 
					HttpStatus.OK, employee);
		} catch(Exception e) {
			return TakeawayResponse.failure(Constants.EMPLOYEE_NOT_FOUND_MESSAGE, 
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(Constants.ID_URL)
	public TakeawayResponse updateEmployee(@PathVariable("id") UUID id, 
			@RequestBody EmployeeRequestBody employeeRequest) throws ParseException {
		try {
			Employee employee = employeeService.update(id, employeeRequest);
			return TakeawayResponse.success(Constants.UPDATED_RESPONSE_MESSAGE, 
					HttpStatus.OK, employee);
		} catch(Exception e) {
			return TakeawayResponse.failure(Constants.EMPLOYEE_NOT_FOUND_MESSAGE, 
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(Constants.ID_URL)
	public TakeawayResponse deleteEmployee(@PathVariable("id") UUID id) {
		try {
			employeeService.delete(id);
			return TakeawayResponse.success(Constants.DELETED_RESPONSE_MESSAGE,
					HttpStatus.OK, null);
		} catch(Exception e) {
			return TakeawayResponse.failure(Constants.EMPLOYEE_NOT_FOUND_MESSAGE, 
					HttpStatus.BAD_REQUEST); 
		}
	}

}
