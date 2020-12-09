package com.takeaway.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.challenge.helper.TakeawayResponse;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.service.IDepartmentService;
import com.takeaway.challenge.util.Constants;

@RestController
@RequestMapping(path = Constants.DEPARTMENTS_BASE_URL)
public class DepartmentController {

	@Autowired
	IDepartmentService departmentService;
	
	@PostMapping
	public TakeawayResponse create(@RequestBody Department departmentRequest) {
		try {
			if(StringUtils.isEmpty(departmentRequest.getName())) {
				throw new Exception("BAD_REQUEST");
			}
			Department department = departmentService.create(departmentRequest);
			return TakeawayResponse.success("created", HttpStatus.CREATED, department);
		} catch(Exception e) {
			return TakeawayResponse.failure(Constants.DEPARTMENT_CREATE_BAD_REQUEST_RESPONSE_MESSAGE,
					HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
