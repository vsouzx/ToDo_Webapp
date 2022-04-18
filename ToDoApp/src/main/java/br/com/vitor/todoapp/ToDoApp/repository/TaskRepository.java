package br.com.vitor.todoapp.ToDoApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.vitor.todoapp.ToDoApp.model.Status;
import br.com.vitor.todoapp.ToDoApp.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	@Query("SELECT t from Task t JOIN t.user u where u.email = :username ORDER BY t.id desc")
	List<Task> findbyUsername(@Param("username")String username);

	@Modifying
	@Transactional
	@Query("DELETE FROM Task t WHERE t.user.id = :id")
	void deleteAllByUser(Long id);

	@Query("SELECT t from Task t JOIN t.user u where u.email = :username and t.status =:status  ORDER BY t.id desc")
	List<Task> findbyUsernameAndStatus(@Param("username")String username, @Param("status")Status status);

}
