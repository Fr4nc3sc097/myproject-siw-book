package it.uniroma3.siw.myprojectsiw02.repository;

import it.uniroma3.siw.myprojectsiw02.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    public List<Book> findByYear(Integer year);
    public boolean existsByTitleAndYear(String title, Integer year);
}
