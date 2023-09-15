package it.uniroma3.siw.myprojectsiw02.repository;


import it.uniroma3.siw.myprojectsiw02.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    public boolean existsByNameAndSurnameAndEmail(String name, String surname, String email);
}

