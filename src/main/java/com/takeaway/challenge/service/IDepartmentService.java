package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Department;

public interface IDepartmentService {

	public Department create(Department department) throws Exception;
	
	public Department get(String name) throws Exception;
	
}

