package com.takeaway.challenge.kafka.events;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeData {
    private String name;
    private Date birthday;
    private String email;
    private String departmentName;
}
