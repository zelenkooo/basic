package com.startup.server;

import com.startup.jpa.UserRepository;
import com.startup.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements GenericService<User, String> {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CrudRepository<User, String> getRepository() {
        return this.userRepository;
    }

    @Override
    public String getId(User entity) {
        return entity.getUserId();
    }

    @Override
    public User save(User entity) {
        entity.setUserId(UUID.randomUUID().toString());
        return GenericService.super.save(entity);
    }


}
