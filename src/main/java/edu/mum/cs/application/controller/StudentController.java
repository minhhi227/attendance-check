package edu.mum.cs.application.controller;

import edu.mum.cs.application.model.StudentViewModel;
import edu.mum.cs.projects.attendance.domain.StudentAttendance;
import edu.mum.cs.projects.attendance.domain.entity.CourseOffering;
import edu.mum.cs.projects.attendance.domain.entity.Session;
import edu.mum.cs.projects.attendance.domain.entity.Student;
import edu.mum.cs.projects.attendance.ooxml.SpreadsheetReaderDAO;
import edu.mum.cs.projects.attendance.service.AttendanceService;
import edu.mum.cs.projects.attendance.service.AttendanceServiceImpl;
import edu.mum.cs.projects.attendance.service.CourseServiceImpl;
import edu.mum.cs.projects.attendance.service.StudentServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by orifjon9 on 7/5/2017.
 */
@Controller
public class StudentController {
	
	@Autowired
	private CourseServiceImpl courseServiceImpl;

	@Autowired
	private StudentServiceImpl studentServiceImpl;

	
	@GetMapping("/students")
	public String students(HttpServletRequest request){
		Principal principal = request.getUserPrincipal();
		Date today = new Date();
		request.setAttribute("studentid", principal.getName());
		request.setAttribute("enrolled", studentServiceImpl.getEnrolledByStudentId(principal.getName()));
		request.setAttribute("student",studentServiceImpl.findStudentById(principal.getName()));
		request.setAttribute("today", today);
		request.setAttribute("mode", "MODE_COURSE");
		return "students";
	}
	
	@GetMapping("/all-attendance")
	public String allAttendance(@RequestParam int id,HttpServletRequest request){
		
		Principal principal = request.getUserPrincipal();
		
		CourseOffering courseOffering = courseServiceImpl.getCourseOffering((long) id);
		String msg = "";
		try
        {
			msg = "Detail record";
		    StudentAttendance attendance = studentServiceImpl.getAttendanceByCourseOffering(principal.getName(), courseOffering);
       
			request.setAttribute("meditaionPercentage", (int)attendance.getMeditaionPercentage());
			request.setAttribute("meditationCount", attendance.getMeditationCount());
			request.setAttribute("numberOfRequiredSessions", attendance.getNumberOfRequiredSessions());
			request.setAttribute("extraGrade", attendance.getMeditationExtraGrade());
			
			Map<String,Boolean> map = new LinkedHashMap<String,Boolean>();  

		    for (int i=0; i<attendance.getSessions().size(); i++) {
		      map.put(attendance.getSessions().get(i).getDate().toString(), attendance.getAttendance().get(i));    
		    }
		    
		request.setAttribute("map", map);
        }
		catch(NullPointerException e)
        {
			msg = "No record";
        }
		request.setAttribute("msg",msg);
		request.setAttribute("studentid", principal.getName());
		request.setAttribute("student",studentServiceImpl.findStudentById(principal.getName()));
		request.setAttribute("mode", "MODE_ATTENDANCE");
		return "students";
	}
	

}
