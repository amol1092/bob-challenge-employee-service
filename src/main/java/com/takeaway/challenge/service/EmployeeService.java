package com.takeaway.challenge.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.helper.EmployeeRequestBody;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.IEmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Override
	public Employee create(EmployeeRequestBody employeeRequest) throws Exception {
		Employee employee = convertEmployeeRequestBodyToEmployeeEntity(employeeRequest);
		return employeeRepository.save(employee);
	}

	@Override
	public Employee getById(UUID employeeId) throws Exception {
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		if(employeeOptional.isPresent()) {
			return employeeOptional.get();
		} else {
			throw new Exception("Employee not found");
		}
	}

	@Override
	public Employee update(UUID employeeId, EmployeeRequestBody employeeRequest) throws Exception {
		Employee employee = getById(employeeId);
		if(employeeRequest.getName().isPresent()) { 
			employee.setFullName(employeeRequest.getName().get()); 
		}
        if(employeeRequest.getEmail().isPresent()) { 
        	employee.setFullName(employeeRequest.getEmail().get()); 
        }
        if(employeeRequest.getDepartment().isPresent()) { 
        	employee.setDepartment(departmentService.get(employeeRequest.getDepartment().get())); 
        }
        if(employeeRequest.getBirthday().isPresent()) { 
        	employee.setBirthday(employeeRequest.getBirthdayDateConverted()); 
        }
        return employeeRepository.save(employee);
	}

	@Override
	public void delete(UUID employeeId) throws Exception {
		Employee employee = getById(employeeId);
		employeeRepository.delete(employee);
	}
	
	private Employee convertEmployeeRequestBodyToEmployeeEntity(EmployeeRequestBody request) 
			throws Exception {
        Employee employee = new Employee();
        employee.setFullName(request.getName().get());
        employee.setEmail(request.getEmail().get());
        employee.setBirthday(request.getBirthdayDateConverted());
        employee.setDepartment(departmentService.get(request.getDepartment().get()));
        return employee;
    }
}
