package com.takeaway.challenge.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequestBody {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Optional<String> name;
    private Optional<String> email;
    private Optional<String> birthday;
    private Optional<String> department;

    public Date getBirthdayDateConverted() throws ParseException {
        return dateFormat.parse(this.birthday.get());
    }

    public void setBirthdayDate(Date date) {
        this.birthday = Optional.of(dateFormat.format(date));
    }
	
}
