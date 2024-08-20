package br.com.webscraping.repositories;

import br.com.webscraping.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
