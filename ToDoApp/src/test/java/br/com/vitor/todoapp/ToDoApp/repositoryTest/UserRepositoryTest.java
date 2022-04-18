package br.com.vitor.todoapp.ToDoApp.repositoryTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import br.com.vitor.todoapp.ToDoApp.model.User;
import br.com.vitor.todoapp.ToDoApp.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		User user =  new User();
		user.setEmail("vinibarino@hotmail.com");
		user.setPassword("vinibarino");
		user.setFirstName("Vinicius");
		user.setLastName("Souza"); 	
		
		User savedUser = userRepository.save(user);
		
		User existUser = entityManager.find(User.class, savedUser.getId());
		
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
	
	}
	
	@Test
	public void testFindUserByEmail() {
		String email = "vtsoliveira2001@gmail.com";
		
		User user = userRepository.findByEmail(email);
		
		assertThat(user).isNotNull();
	}
}
