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
import com.takeaway.challenge.helper.TakeawayHttpResponse;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.service.IEmployeeService;
import com.takeaway.challenge.util.Constants;

@RestController
@RequestMapping(path = Constants.EMPLOYEES_BASE_URL)
public class EmployeeController {

	@Autowired
	IEmployeeService employeeService;

	@PostMapping
	public TakeawayHttpResponse create(@RequestBody EmployeeRequestBody employeeRequest) 
			throws ParseException {
		try {
			Employee employee = employeeService.create(employeeRequest);
			return TakeawayHttpResponse.success("created", HttpStatus.CREATED, employee);
		} catch(Exception e) {
			return TakeawayHttpResponse.failure(Constants.EMPLOYEE_CREATE_BAD_REQUEST_RESPONSE_MESSAGE,
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(Constants.ID_URL)
	public TakeawayHttpResponse getEmployee(@PathVariable("id") UUID id) {
		try {
			Employee employee = employeeService.getById(id);
			return TakeawayHttpResponse.success(Constants.EMPLOYEE_FOUND_MESSAGE, 
					HttpStatus.OK, employee);
		} catch(Exception e) {
			return TakeawayHttpResponse.failure(Constants.EMPLOYEE_NOT_FOUND_MESSAGE, 
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(Constants.ID_URL)
	public TakeawayHttpResponse updateEmployee(@PathVariable("id") UUID id, 
			@RequestBody EmployeeRequestBody employeeRequest) throws ParseException {
		try {
			Employee employee = employeeService.update(id, employeeRequest);
			return TakeawayHttpResponse.success(Constants.UPDATED_RESPONSE_MESSAGE, 
					HttpStatus.OK, employee);
		} catch(Exception e) {
			return TakeawayHttpResponse.failure(Constants.EMPLOYEE_NOT_FOUND_MESSAGE, 
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(Constants.ID_URL)
	public TakeawayHttpResponse deleteEmployee(@PathVariable("id") UUID id) {
		try {
			employeeService.delete(id);
			return TakeawayHttpResponse.success(Constants.DELETED_RESPONSE_MESSAGE,
					HttpStatus.OK, null);
		} catch(Exception e) {
			return TakeawayHttpResponse.failure(Constants.EMPLOYEE_NOT_FOUND_MESSAGE, 
					HttpStatus.BAD_REQUEST); 
		}
	}

}
