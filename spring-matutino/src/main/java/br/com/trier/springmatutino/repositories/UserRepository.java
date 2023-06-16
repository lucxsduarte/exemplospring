package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findByNameIgnoreCase(String name);
	
	List<User> findByNameEndsWith(String name);
	
}
