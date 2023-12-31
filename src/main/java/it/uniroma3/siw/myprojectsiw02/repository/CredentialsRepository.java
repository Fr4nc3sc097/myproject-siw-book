package it.uniroma3.siw.myprojectsiw02.repository;

import it.uniroma3.siw.myprojectsiw02.model.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
    public Optional<Credentials> findByUsername(String username);
    public boolean existsByUsername(String username);
}

