package edu.mum.cs.projects.attendance.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.mum.cs.projects.attendance.domain.entity.Enrollment;
import edu.mum.cs.projects.attendance.domain.entity.Student;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, String>{
	List<Enrollment> findByStudent(Student student);
}
