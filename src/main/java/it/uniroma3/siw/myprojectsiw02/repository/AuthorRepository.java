package it.uniroma3.siw.myprojectsiw02.repository;

import it.uniroma3.siw.myprojectsiw02.model.Author;
import it.uniroma3.siw.myprojectsiw02.model.Book;
import org.springframework.data.repository.CrudRepository;

import javax.imageio.ImageTranscoder;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    public boolean existsByNameAndSurname(String name, String surname);
    public Iterable<Author> findAllByWrittenBooksIsNotContaining(Book book);

}