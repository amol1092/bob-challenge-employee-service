package com.takeaway.challenge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.takeaway.challenge.model.Department;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDepartmentService {

	@Autowired
	private IDepartmentService departmentService;
	
	private List<Department> createdData;
	
	@Before
	public void setup() {
		createdData = new ArrayList<>();
	}
	
	@After
	public void tearDown() {
		createdData.stream().forEach(department -> departmentService.delete(department));
	}
	
	@Test
	public void create_withNonExistingDepartment_createsDepartment() throws Exception {
		//prepare
		String expectedDepartmentName = "TestDept-1";
		Department departmentToBeCreated = new Department(expectedDepartmentName);
		
		//execute
		Department createdDepartment = departmentService.create(departmentToBeCreated);
		
		//verify
		assertNotNull(createdDepartment);
		
		//clean up
		createdData.add(createdDepartment); // this is needed to clean the test data created
		
		assertEquals(expectedDepartmentName, createdDepartment.getName());
	}
	
	@Test
	public void create_withExistingDepartment_throws() throws Exception {
		//prepare
		String departmentName1 = "TestDept-1";
		String departmentName2 = "TestDept-1";
		Department department1 = new Department(departmentName1);
		Department department2 = new Department(departmentName2);

		//1st execute
		Department createdDepartment1 = departmentService.create(department1);
		//verify
		assertNotNull(createdDepartment1);
		//cleanup
		createdData.add(createdDepartment1);

		//2nd execute
		Department createdDepartment2 = null;
		try {
			createdDepartment2 = departmentService.create(department2);
			fail("Expected an exception to be thrown");
		} catch(DataIntegrityViolationException e) {
			//verify
			assertNull(createdDepartment2);
		}
	}
	
	@Test
	public void get_withExistingDepartmentName_returnsDepartment() throws Exception {
		//prepare
		String expectedDepartmentName = "TestDept-1";
		Department department = new Department(expectedDepartmentName);
		Department expectedDepartment = departmentService.create(department);
		assertNotNull(expectedDepartment);
		
		//cleanup
		createdData.add(expectedDepartment);
		
		//execute
		Department retreivedDepartment = departmentService.get(expectedDepartmentName);
		
		//verify
		assertNotNull(retreivedDepartment);
		assertEquals(expectedDepartment, retreivedDepartment);
		
				
	}

}
