package com.takeaway.challenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.takeaway.challenge.model.Department;

@Repository
public interface IDepartmentRepository extends CrudRepository<Department, Long> {
	
	Optional<Department> findDepartmentByName(String name);

}
