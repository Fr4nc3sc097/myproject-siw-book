package it.uniroma3.siw.myprojectsiw02.service;


import it.uniroma3.siw.myprojectsiw02.model.Book;
import it.uniroma3.siw.myprojectsiw02.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    protected BookRepository bookRepository;

    @Transactional
    public void createNewBook(Book book){
        this.bookRepository.save(book);
    }
    @Transactional
    public void save(Book book){
        this.bookRepository.save(book);
    }
    public boolean alreadyExists(Book book){
        return this.bookRepository.existsByTitleAndYear(book.getTitle(), book.getYear());
    }
    @Transactional
    public void removeCategory(Book book,String category) {
        book.getCategories().remove(category);
        bookRepository.save(book);
    }
    @Transactional
    public void removeBook(Book book){
        bookRepository.delete(book);
    }
    public Iterable<Book> findAll(){
        return this.bookRepository.findAll();
    }

    public Iterable<Book> findByYear(Integer year){
        return this.bookRepository.findByYear(year);
    }

    public Book findById(Long id){
        return this.bookRepository.findById(id).orElse(null);
    }

}

