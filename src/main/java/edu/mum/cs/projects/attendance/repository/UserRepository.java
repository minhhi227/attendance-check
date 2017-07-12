package edu.mum.cs.projects.attendance.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.cs.projects.attendance.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
