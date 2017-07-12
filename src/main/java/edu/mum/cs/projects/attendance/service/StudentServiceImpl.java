package edu.mum.cs.projects.attendance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.cs.projects.attendance.domain.StudentAttendance;
import edu.mum.cs.projects.attendance.domain.entity.AcademicBlock;
import edu.mum.cs.projects.attendance.domain.entity.Barcode;
import edu.mum.cs.projects.attendance.domain.entity.BarcodeRecord;
import edu.mum.cs.projects.attendance.domain.entity.CourseOffering;
import edu.mum.cs.projects.attendance.domain.entity.Enrollment;
import edu.mum.cs.projects.attendance.domain.entity.Session;
import edu.mum.cs.projects.attendance.domain.entity.Student;
import edu.mum.cs.projects.attendance.ooxml.SpreadsheetReaderDAO;
import edu.mum.cs.projects.attendance.repository.BarcodeRecordRepository;
import edu.mum.cs.projects.attendance.repository.EnrollmentRepository;
import edu.mum.cs.projects.attendance.repository.StudentRepository;
import edu.mum.cs.projects.attendance.util.DateUtil;

/**
 * <h1>Maharishi University of Management<br/>Computer Science Department</h1>
 * 
 * <p>Service layer facade, hides away details of dataaccess layer from client.</p>
 *
 * @see edu.mum.cs.projects.attendance.service.StudentService
 *
 * @author Payman Salek
 * 
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private CourseService courseService;
	
	@Autowired
	BarcodeRecordRepository barcodeRecordRepository;
	
	private Map<String, String> barcodeMap; 
	
	{

	}
	
	private Map<String, String> getBarcodeMap() {
		if(null == barcodeMap) {
			barcodeMap = new HashMap<>();
			List<Barcode> barcodeList = SpreadsheetReaderDAO.loadBarcodeList();
			for(Barcode barcode : barcodeList) {
				barcodeMap.put(barcode.getStudentId(), barcode.getBarcodeId());
			}			
		}
		
		return barcodeMap;
	}

	@Override
	public String getBarcodeId(String studentId) {
		return getBarcodeMap().get(studentId);
	}

	@Override
	public List<Student> getStudentsByEntry(String entryDate) {
		return studentRepository.findByEntryDate(DateUtil.convertStringToDate(entryDate));
	}

	@Override
	public List<Enrollment> getEnrolledByStudentId(String studentId) {
		
		return enrollmentRepository.findByStudent(findStudentById(studentId)).stream()
				.filter(o -> Enrollment.Status.SIGNEDUP.toString().equalsIgnoreCase(o.getStatus()))
				.collect(Collectors.toList());
	}

	@Override
	public Student findStudentById(String studentId) {
		
		return studentRepository.findByStudentId(studentId);
	}

	@Override
	public StudentAttendance getAttendanceByCourseOffering(String studentId, CourseOffering courseOffering) {

		AcademicBlock block = courseService
				.getAcademicBlock(DateUtil.convertDateToString(courseOffering.getStartDate()));
		courseOffering.setBlock(block);
		
		
		Date beginDate = DateUtil.convertLocalDateToDate(block.getBeginDate());
		Date endDate = DateUtil.convertLocalDateToDate(block.getEndDate());
		List<BarcodeRecord> barcodeRecords = barcodeRecordRepository.findByDateBetween(beginDate, endDate);

		StudentAttendance studentAttendance = new StudentAttendance(findStudentById(studentId),courseOffering);
		
		System.out.println("\nCreating attendance report for: " + courseOffering.getCourse() + " by "
				+ courseOffering.getFaculty());
		
		List<Boolean> attendance = new ArrayList<Boolean>(block.getSessions().size());
		
		
		studentAttendance.setAttendance(attendance);
        
		boolean noBarcode = (null == studentAttendance.getStudent().getBarcode());

		for (Session session : block.getSessions()) {
			if (noBarcode) {
				attendance.add(false);
			} else {
				attendance.add(
						barcodeRecords.stream().filter(br -> br.getBarcode().equals(studentAttendance.getStudent().getBarcode()))
								.filter(br -> br.getDate().equals(session.getDate()))
								.filter(br -> br.getTimeslot().equals(session.getTimeslot()))
								.findFirst()
								.isPresent());
			}
		}

		return studentAttendance;
	}

}
