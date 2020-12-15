package com.takeaway.challenge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.takeaway.challenge.helper.EmployeeRequestBody;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEmployeeService {

	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private IDepartmentService departmentService;
	
	private List<Employee> createdEmployeeData;
	
	private List<Department> createdDepartmentData;
	
	@Before
	public void setup() {
		createdEmployeeData = new ArrayList<>();
		createdDepartmentData = new ArrayList<>();
	}
	
	@After
	public void tearDown() {
		createdEmployeeData.stream().forEach(employee -> {
			try {
				employeeService.delete(employee.getId());
			} catch (Exception e) {
			}
		});
		createdDepartmentData.stream().forEach(department -> departmentService.delete(department));
	}
	
	@Test
	public void create_withNonExistingEmail_createsEmployee() throws Exception {
		//create temporary department to be used while creating employee
		String expectedDepartmentName = "TestDept-1";
		Department expectedDepartment = departmentService.create(new Department(expectedDepartmentName));
		assertNotNull(expectedDepartment);
		createdDepartmentData.add(expectedDepartment);
		
		//prepare
		String expectedEmail = "Test@Email.com";
		String expectedFullName = "Test Name";
		String expectedBirthday = "1992-10-10";
		EmployeeRequestBody request = new EmployeeRequestBody(Optional.of(expectedFullName),
				Optional.of(expectedEmail), Optional.of(expectedBirthday), 
				Optional.of(expectedDepartmentName));
		
		//execute
		Employee createdEmployee = employeeService.create(request);
		
		//verify
		assertNotNull(createdEmployee);
		
		//clean up
		createdEmployeeData.add(createdEmployee); // this is needed to clean the test data created
		
		assertEquals(expectedEmail, createdEmployee.getEmail());
		assertEquals(expectedFullName, createdEmployee.getFullName());
		assertDateEquals(expectedBirthday, createdEmployee.getBirthday());
		assertEquals(expectedDepartment, createdEmployee.getDepartment());
	}
	
	@Test
	public void create_withExistingEmail_throws() throws Exception {
		//create temporary department to be used while creating employee
		String expectedDepartmentName = "TestDept-1";
		Department expectedDepartment = departmentService.create(new Department(expectedDepartmentName));
		assertNotNull(expectedDepartment);
		createdDepartmentData.add(expectedDepartment);

		//prepare
		String expectedEmail = "Test@Email.com";
		String expectedFullName = "Test Name";
		String expectedBirthday = "1992-10-10";
		EmployeeRequestBody request = new EmployeeRequestBody(Optional.of(expectedFullName),
				Optional.of(expectedEmail), Optional.of(expectedBirthday), 
				Optional.of(expectedDepartmentName));
		
		//1st execution
		Employee createdEmployee1 = employeeService.create(request);
		
		//verify
		assertNotNull(createdEmployee1);
		
		createdEmployeeData.add(createdEmployee1);
		
		//2nd execution with same request
		Employee createdEmployee2 = null;
		try {
			createdEmployee2 = employeeService.create(request);
			fail("Expected an exception to be thrown");
		} catch(DataIntegrityViolationException e) {
			//verify
			assertNull(createdEmployee2);
		}
	}
	
	@Test
	public void create_withNonExistingDepartment_createsEmployeeWithTheGivenDeparmtnet() throws Exception {
		//prepare
		String expectedEmail = "Test@Email.com";
		String expectedFullName = "Test Name";
		String expectedBirthday = "1992-10-10";
		String expectedDepartmentName = "TestDept-1";
		EmployeeRequestBody request = new EmployeeRequestBody(Optional.of(expectedFullName),
				Optional.of(expectedEmail), Optional.of(expectedBirthday), 
				Optional.of(expectedDepartmentName));
		
		//execute
		Employee createdEmployee = employeeService.create(request);
		
		//verify
		assertNotNull(createdEmployee);
		
		createdEmployeeData.add(createdEmployee);
		createdDepartmentData.add(createdEmployee.getDepartment());
		
		assertEquals(expectedEmail, createdEmployee.getEmail());
		assertEquals(expectedFullName, createdEmployee.getFullName());
		assertDateEquals(expectedBirthday, createdEmployee.getBirthday());
		assertEquals(expectedDepartmentName, createdEmployee.getDepartment().getName());
	}
	
	@Test
	public void getById_withExistingEmployeeId_returnsEmployeeData() throws Exception {
		//prepare
		String expectedEmail = "Test@Email.com";
		String expectedFullName = "Test Name";
		String expectedBirthday = "1992-10-10";
		String expectedDepartmentName = "TestDept-1";
		EmployeeRequestBody request = new EmployeeRequestBody(Optional.of(expectedFullName),
				Optional.of(expectedEmail), Optional.of(expectedBirthday), 
				Optional.of(expectedDepartmentName));
		Employee createdEmployee = employeeService.create(request);
		assertNotNull(createdEmployee);
		createdEmployeeData.add(createdEmployee);
		createdDepartmentData.add(createdEmployee.getDepartment());
		
		//execute
		Employee employeeRetreivedById = employeeService.getById(createdEmployee.getId());
		
		//verify
		assertNotNull(employeeRetreivedById);
		assertEquals(expectedEmail, createdEmployee.getEmail());
		assertEquals(expectedFullName, createdEmployee.getFullName());
		assertDateEquals(expectedBirthday, createdEmployee.getBirthday());
		assertEquals(expectedDepartmentName, createdEmployee.getDepartment().getName());
	}
	
	@Test
	public void getById_withNonExistingEmployeeId_throws() throws Exception {
		Employee employee = null;
		try {
			employee = employeeService.getById(UUID.randomUUID());
			fail("Expected an exception to be thrown");
		} catch(Exception e) {
			//verify
			assertNull(employee);
			assertEquals("Employee not found", e.getMessage());
		}
	}
	
	@Test
	public void delete_withExistingEmployeeId_deletesEmployeeData() throws Exception {
		//prepare
		String expectedEmail = "Test@Email.com";
		String expectedFullName = "Test Name";
		String expectedBirthday = "1992-10-10";
		String expectedDepartmentName = "TestDept-1";
		EmployeeRequestBody request = new EmployeeRequestBody(Optional.of(expectedFullName),
				Optional.of(expectedEmail), Optional.of(expectedBirthday), 
				Optional.of(expectedDepartmentName));
		Employee createdEmployee = employeeService.create(request);
		assertNotNull(createdEmployee);
		createdEmployeeData.add(createdEmployee);
		createdDepartmentData.add(createdEmployee.getDepartment());
		
		//execute
		employeeService.delete(createdEmployee.getId());
		
		//verify
		Employee employee = null;
		try {
			employee = employeeService.getById(createdEmployee.getId());
			fail("Expected an exception to be thrown");
		} catch(Exception e) {
			//verify
			assertNull(employee);
			assertEquals("Employee not found", e.getMessage());
		}
	}
	
	@Test
	public void update_withExistingEmployee_updatesEmployeeData() throws Exception {
		//prepare
		String expectedEmail = "Test@Email.com";
		String expectedFullName = "Test Name";
		String expectedBirthday = "1992-10-10";
		String expectedDepartmentName = "TestDept-1";
		EmployeeRequestBody request = new EmployeeRequestBody(Optional.of(expectedFullName),
				Optional.of(expectedEmail), Optional.of(expectedBirthday), 
				Optional.of(expectedDepartmentName));
		Employee createdEmployee = employeeService.create(request);
		assertNotNull(createdEmployee);
		createdEmployeeData.add(createdEmployee);
		createdDepartmentData.add(createdEmployee.getDepartment());
		
		String expectedFullNameToBeUpdated = "Test Name 123";
		request = new EmployeeRequestBody(Optional.of(expectedFullNameToBeUpdated),
				Optional.empty(), Optional.empty(),Optional.empty());
		
		//execute
		Employee updatedEmployee = employeeService.update(createdEmployee.getId(), request);
		
		//verify
		assertNotNull(updatedEmployee);
		assertEquals(expectedEmail, updatedEmployee.getEmail());
		assertEquals(expectedFullNameToBeUpdated, updatedEmployee.getFullName());
		assertDateEquals(expectedBirthday, updatedEmployee.getBirthday());
		assertEquals(expectedDepartmentName, updatedEmployee.getDepartment().getName());
	}
	
	@Test
	public void update_withNonExistingEmployee_throws() {
		//prepare
		String expectedEmail = "Test@Email.com";
		String expectedFullName = "Test Name";
		String expectedBirthday = "1992-10-10";
		String expectedDepartmentName = "TestDept-1";
		EmployeeRequestBody request = new EmployeeRequestBody(Optional.of(expectedFullName),
				Optional.of(expectedEmail), Optional.of(expectedBirthday), 
				Optional.of(expectedDepartmentName));
		Employee employee = null;
		try {
			employee = employeeService.update(UUID.randomUUID(), request);
			fail("Expected an exception to be thrown");
		} catch(Exception e) {
			//verify
			assertNull(employee);
			assertEquals("Employee not found", e.getMessage());
		}
	}
	
	private void assertDateEquals(String expectedDateString, Date actualDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(expectedDateString, dateFormat.format(actualDate));
	}
	
}
