package com.takeaway.challenge.util;

public class Constants {
	
	public static final String EMPLOYEES_BASE_URL = "/employees";
	
	public static final String DEPARTMENTS_BASE_URL = "/departments";
	
	public static final String ID_URL = "/{id}";
	
	public static final String CREATED_RESPONSE_MESSAGE = "created";
	
	public static final String DEPARTMENT_CREATE_BAD_REQUEST_RESPONSE_MESSAGE = "Department exists with same name."
			+ " Please try another one";
	
	public static final String EMPLOYEE_CREATE_BAD_REQUEST_RESPONSE_MESSAGE = "Employee exists with same email address. "
			+ "Please try another one";
	
	public static final String EMPLOYEE_FOUND_MESSAGE = "Employee found";
	
	public static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee not found";
	
	public static final String UPDATED_RESPONSE_MESSAGE = "updated";
	
	public static final String DELETED_RESPONSE_MESSAGE = "deleted";
	
	public static final String NOT_DELETED_RESPONSE_MESSAGE = "not deleted";
	
}
