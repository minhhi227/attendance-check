package edu.mum.cs.application.controller;

import edu.mum.cs.application.model.CourseViewModel;
import edu.mum.cs.projects.attendance.domain.entity.Course;
import edu.mum.cs.projects.attendance.ooxml.SpreadsheetReaderDAO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by orifjon9 on 7/6/2017.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    @RequestMapping(method = RequestMethod.GET)
    public List<CourseViewModel> courses() {
        return SpreadsheetReaderDAO.loadCourseList().stream()
                .map(m->new CourseViewModel(m))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{courseNumber}", method = RequestMethod.GET)
    public Course course(@PathVariable String courseNumber) {
        return SpreadsheetReaderDAO.findCourse(courseNumber);
    }
}
