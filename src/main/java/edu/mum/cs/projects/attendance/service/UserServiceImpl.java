package edu.mum.cs.projects.attendance.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.mum.cs.projects.attendance.domain.entity.Role;
import edu.mum.cs.projects.attendance.domain.entity.User;
import edu.mum.cs.projects.attendance.repository.RoleRepository;
import edu.mum.cs.projects.attendance.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public List<Role> getRoles(String username) {
		// TODO Auto-generated method stub
		return findByUsername(username).getRoles().stream().collect(Collectors.toList());
	}

//	@Override
//	public String getRole(String username) {
//		// TODO Auto-generated method stub
//		return findByUsername(username).getRoles().stream().limit(1).collect(Collectors.toList()).get(0).getName();
//	}
   
}
