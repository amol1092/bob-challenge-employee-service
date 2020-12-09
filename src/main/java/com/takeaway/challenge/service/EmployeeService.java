package com.takeaway.challenge.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.helper.EmployeeRequestBody;
import com.takeaway.challenge.kafka.events.EmployeeData;
import com.takeaway.challenge.kafka.events.EmployeeEvent;
import com.takeaway.challenge.kafka.events.EmployeeEvent.EventType;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.IEmployeeRepository;
import com.takeaway.challenge.util.Constants;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
    KafkaTemplate<String, EmployeeEvent> kafkaTemplate;
	
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);
	
	@Override
	public Employee create(EmployeeRequestBody employeeRequest) throws Exception {
		Employee employee = convertEmployeeRequestBodyToEmployeeEntity(employeeRequest);
		Employee createdEmployeeEntity = employeeRepository.save(employee);
		CompletableFuture.runAsync(() -> 
			sendEmployeeEvent(EventType.EMPLOYEE_CREATED, createdEmployeeEntity), EXECUTOR);
		return createdEmployeeEntity;
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
        Employee updatedEmployeeEntity = employeeRepository.save(employee);
        CompletableFuture.runAsync(() -> 
        	sendEmployeeEvent(EventType.EMPLOYEE_UPDATED, updatedEmployeeEntity), EXECUTOR);
        return updatedEmployeeEntity;
	}

	@Override
	public void delete(UUID employeeId) throws Exception {
		Employee employee = getById(employeeId);
		employeeRepository.delete(employee);
		CompletableFuture.runAsync(() -> sendEmployeeEvent(EventType.EMPLOYEE_DELETED, employee), 
				EXECUTOR);
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

	//Kafka Producer method to send employee events
	private void sendEmployeeEvent(EmployeeEvent.EventType eventType, Employee entity) {
        EmployeeData data = new EmployeeData(
                entity.getFullName(),
                entity.getBirthday(),
                entity.getEmail(),
                entity.getDepartment().getName());
        EmployeeEvent event = new EmployeeEvent(eventType, entity.getId().toString(), data);

        kafkaTemplate.send(Constants.EMPLOYEE_EVENTS_TOPIC, event);
    }
}
