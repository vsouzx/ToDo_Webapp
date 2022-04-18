package br.com.vitor.todoapp.ToDoApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.vitor.todoapp.ToDoApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u.password FROM User u WHERE u.id = ?1")
	Optional<User> getPassword(Long id);

	@Query("SELECT u.id FROM User u WHERE u.email = ?1")
	User findIdByName(String username);

	User findByEmail(String email);
	
}
