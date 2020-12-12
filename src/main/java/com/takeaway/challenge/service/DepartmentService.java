package com.takeaway.challenge.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.repository.IDepartmentRepository;

@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	IDepartmentRepository departmentRepository;

	@Override
	public Department create(Department department) throws Exception {
		return departmentRepository.save(department);
	}

	@Override
	public Department get(String name) throws Exception {
		Optional<Department> optionalDepartment = departmentRepository.findDepartmentByName(name);

		if(optionalDepartment.isPresent()) {
			return optionalDepartment.get();
		} else {
			Department department = new Department(name);
			return departmentRepository.save(department);
		}
	}

	@Override
	public void delete(Department department) {
		departmentRepository.delete(department);
	}
	
}
