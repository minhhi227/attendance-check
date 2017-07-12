package edu.mum.cs.application.model;

import edu.mum.cs.projects.attendance.domain.entity.Student;

import java.util.Date;

/**
 * Created by orifjon9 on 7/5/2017.
 */
public class StudentViewModel {
    private String studentId;
    private String firstName;
    private String lastName;
    private String emailaddress;
    private String visaStatus;
    private String status;
    private Date entryDate;
    private String barcode;

    public StudentViewModel(Student student){
        this.studentId = student.getStudentId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.emailaddress = student.getEmailaddress();
        this.visaStatus = student.getVisaStatus();
        this.status = student.getStatus();
        this.entryDate = student.getEntryDate();
        this.barcode = student.getBarcode();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getVisaStatus() {
        return visaStatus;
    }

    public String getStatus() {
        return status;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getUrl() {
        return "http://localhost:80/students/" + this.getStudentId();
    }
}
