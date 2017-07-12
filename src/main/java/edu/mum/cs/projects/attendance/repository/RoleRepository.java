package edu.mum.cs.projects.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.cs.projects.attendance.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
