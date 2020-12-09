package com.takeaway.challenge.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.takeaway.challenge.model.Employee;

@Repository
public interface IEmployeeRepository extends CrudRepository<Employee, UUID>{

}
