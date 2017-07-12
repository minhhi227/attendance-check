package edu.mum.cs.application.model;

import edu.mum.cs.projects.attendance.domain.entity.Course;
import edu.mum.cs.projects.attendance.domain.entity.Student;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by orifjon9 on 7/5/2017.
 */
public class CourseViewModel {

    private String number;

    private String abbr;

    private String name;

    private String description;

    public CourseViewModel(Course course){
        this.number = course.getNumber();
        this.abbr = course.getAbbr();
        this.name = course.getName();
        this.description = course.getDescription();

    }

    public String getNumber() {
        return number;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return "http://localhost:80/courses/" + this.getNumber();
    }
}
