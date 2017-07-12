package edu.mum.cs.application.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FacultyController {

	@GetMapping("/faculty")
	public String faculty(HttpServletRequest request){
		return "faculty";
	}
	
	@GetMapping("/faculty-students")
	public String facultyStudent(HttpServletRequest request){
		request.setAttribute("mode", "MODE_COURSE");
		return "faculty";
	}
}
