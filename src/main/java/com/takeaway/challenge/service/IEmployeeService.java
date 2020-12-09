package com.takeaway.challenge.service;

import java.util.UUID;

import com.takeaway.challenge.helper.EmployeeRequestBody;
import com.takeaway.challenge.model.Employee;

public interface IEmployeeService {

	public Employee create(EmployeeRequestBody employeeRequest) throws Exception;
	
	public Employee getById(UUID employeeId) throws Exception;
	
	public Employee update(UUID employeeId, EmployeeRequestBody employeeRequest) throws Exception;
	
	public void delete(UUID employeeId) throws Exception;
	
}
