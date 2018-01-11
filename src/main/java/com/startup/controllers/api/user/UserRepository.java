package com.startup.controllers.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(Long id);

  void deleteById(Long id);
}

