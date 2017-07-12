package edu.mum.cs.projects.attendance.service;

import java.util.List;

import edu.mum.cs.projects.attendance.domain.entity.Role;
import edu.mum.cs.projects.attendance.domain.entity.User;

public interface UserService {

	void save(User user);
	User findByUsername(String username);
	List<Role> getRoles(String username);
}
