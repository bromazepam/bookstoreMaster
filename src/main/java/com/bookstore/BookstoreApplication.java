package com.bookstore;

import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.RoleRepository;
import com.bookstore.service.UserService;
import com.bookstore.utility.SecurityUtility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public BookstoreApplication(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Adams");
        user1.setUsername("user");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("user"));
        user1.setEmail("user@gmail.com");
        Set<UserRole> userRoles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setName("ROLE_USER");
        roleRepository.save(role1);
        userRoles.add(new UserRole(user1, role1));

        userService.createUser(user1, userRoles);
    }
}