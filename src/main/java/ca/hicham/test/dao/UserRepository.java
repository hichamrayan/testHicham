package ca.hicham.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hicham.test.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
 
    User findByUsername(String username);
}