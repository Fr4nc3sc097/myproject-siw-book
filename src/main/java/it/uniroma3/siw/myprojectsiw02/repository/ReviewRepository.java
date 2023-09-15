package it.uniroma3.siw.myprojectsiw02.repository;

import it.uniroma3.siw.myprojectsiw02.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
