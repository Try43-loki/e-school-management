package org.example.e_school_management.security;

import org.example.e_school_management.admin.model.Admin;
import org.example.e_school_management.admin.repository.AdminRepository;
import org.example.e_school_management.student.model.Student;
import org.example.e_school_management.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(admin.getUsername(), admin.getPassword(), authorities);
        }

        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
            return new User(student.getUsername(), student.getPassword(), authorities);
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
