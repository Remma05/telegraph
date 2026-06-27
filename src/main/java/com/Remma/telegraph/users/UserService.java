package com.Remma.telegraph.users;

import com.Remma.telegraph.configs.PasswordConfig;
import com.Remma.telegraph.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final SecurityUtils securityUtils;

    public UserService(
            UserRepository repository,
            UserMapper mapper,
            PasswordEncoder encoder, SecurityUtils securityUtils
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
        this.securityUtils = securityUtils;
    }
    
    public void createUser(UserRequest request) {
        var entityToSave = mapper.toEntity(null, request);
        entityToSave.setPassword(encoder.encode(request.password()));
        repository.save(entityToSave);
    }

    public UserResponse getUserById(Long id) {
        var founded = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + id));
        return mapper.toDomain(founded);
    }

    public UserEntity getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + id));
    }

    public UserResponse getUserByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void updateUser(Long id, UserRequest request) {
        var founded = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + id));
        if (request.username() != null) {
            founded.setUsername(request.username());
        }
        if (request.password() != null) {
            founded.setPassword(encoder.encode(request.password()));
        }
        repository.save(founded);
    }

    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Not found user by id = " + id);
        }
        repository.deleteById(id);
    }

    public UserEntity getCurrentUserEntity() {
        return securityUtils.getCurrentUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
