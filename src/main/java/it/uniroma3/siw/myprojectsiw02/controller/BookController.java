package it.uniroma3.siw.myprojectsiw02.controller;


import it.uniroma3.siw.myprojectsiw02.controller.util.FileUploadUtil;
import it.uniroma3.siw.myprojectsiw02.controller.validator.BookValidator;
import it.uniroma3.siw.myprojectsiw02.model.Author;
import it.uniroma3.siw.myprojectsiw02.model.Book;
import it.uniroma3.siw.myprojectsiw02.service.AuthorService;
import it.uniroma3.siw.myprojectsiw02.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    BookValidator bookValidator;

    @GetMapping("/book")
    public String showBooks(Model model){
        model.addAttribute("books", this.bookService.findAll());
        return "books";
    }
    @GetMapping("/book/{id}")
    public String getMovie(@PathVariable("id") Long id, Model model){
        Book book = bookService.findById(id);
        if(book == null) return "/errors/bookNotFoundError";

        model.addAttribute("book", book);
        return "book";
    }
    @GetMapping("/formSearchBooks")
    public String formSearchMovies(Model model){
        return "formSearchBooks";
    }
    @PostMapping("/searchBooks")
    public String searchMovies(Model model, @RequestParam Integer year){
        model.addAttribute("books", this.bookService.findByYear(year));
        return "foundBooks";
    }
    //############### ADMIN #################
    @GetMapping("/admin/book")
    public String showBooksAdmin(Model model){
        model.addAttribute("books", this.bookService.findAll());
        return "admin/booksAdmin";
    }
    @PostMapping("/admin/book")
    public String newMovie(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model){
        bookValidator.validate(book, bindingResult);
        if(!bindingResult.hasErrors()){
            this.bookService.createNewMovie(book);
            model.addAttribute("book", book);
            return "admin/bookAdmin";
        }else{
            return "admin/formNewBook";
        }
    }
    @GetMapping("/admin/book/{id}")
    public String getBookAdmin(@PathVariable("id") Long id, Model model){
        Book book = bookService.findById(id);
        if(book == null) return "errors/bookNotFoundError";

        model.addAttribute("book", book);
        return "admin/bookAdmin";
    }
    @GetMapping("/admin/formNewBook")
    public String formNewBook(Model model){
        Book book =  new Book();
        model.addAttribute("book", book);
        return "admin/formNewBook";
    }
    @GetMapping("/admin/selectAuthorToBook/{id}")
    public String selectAuthorToBook(@PathVariable("id") Long id, Model model){
        Book book = bookService.findById(id);
        if(book == null) return "errors/bookNotFoundError";

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findDirectorsToSel(book));
        return "admin/selectAuthorToBook";
    }
    @GetMapping("/admin/setAuthorToBook/{book_id}/{author_id}")
    public String addAuthorToBook(@PathVariable("book_id") Long book_id,
                                     @PathVariable("author_id") Long author_id,
                                     Model model){
        Author author = authorService.findById(author_id);
        if(author == null) return "errors/authorNotFoundError";

        Book book = bookService.findById(book_id);
        if(book == null) return "errors/bookNotFoundError";
        book.setAuthor(author);
        bookService.save(book);
        model.addAttribute("book", book);
        return "admin/bookAdmin";
    }

    //@GetMapping("/admin/selectActorsToMovie/{id}")
    //public String selectActorsToMovie(@PathVariable("id") Long id, Model model){
      //  Movie movie = bookService.findById(id);
        //if(movie == null) return "errors/movieNotFoundError";

        //model.addAttribute("movie", movie);
        //model.addAttribute("artists", authorService.findActorsToSel(movie));
        //return "admin/selectActorsToMovie";
    //}

    //@GetMapping("/admin/addCategorieToBook/{book_id}/{artist_id}")
    //public String addActorToMovie(@PathVariable("movie_id") Long movie_id,
    //                              @PathVariable("artist_id") Long artist_id,
    //                              Model model){
    //    Artist actor = authorService.findById(artist_id);
    //    if(actor == null) return "errors/artistNotFoundError";

    //    Movie movie = bookService.findById(movie_id);
    //    if(movie == null) return "errors/movieNotFoundError";

    //    movie.getActors().add(actor);
    //    bookService.save(movie);
    //    model.addAttribute("movie", movie);
    //    return selectActorsToMovie(movie_id, model);
    //}

    @GetMapping("/admin/choseCategoryToBook/{book_id}")
    public String choseCategoryFromBookToRemove(@PathVariable("book_id") Long book_id,
                                       Model model) {

        Book book = bookService.findById(book_id);
        if (book == null) return "errors/bookNotFoundError";
        model.addAttribute("inputText", new String());
        model.addAttribute("book", book);
        return "admin/choseCategoryToBook";
    }
    @GetMapping("/admin/addCategoryToBook/{category}/{book_id}")
    public String addCategoryToBook(@PathVariable("book_id") Long book_id,
                                         @PathVariable("category") String category,
                                         Model model) {

        Book book = bookService.findById(book_id);
        if (book == null) return "errors/bookNotFoundError";
        book.getCategories().add(category);
        bookService.save(book);
        model.addAttribute("book", book);
        return "/admin/bookAdmin";
    }
    @GetMapping("/admin/removeCategoryFromBook/{book_id}/{category}")
    public String removeCategoryFromBook(@PathVariable("book_id") Long book_id,
                                                @PathVariable("category") String category,
                                                Model model){

        Book book = bookService.findById(book_id);
        if(book == null) return "errors/bookNotFoundError";
        Set<String> categories = book.getCategories();
        for (String s:
             categories){
                 if(s.equals(category))
                     bookService.removeCategory(book,s);

        }
        model.addAttribute("book", book);
        return "/admin/bookAdmin";
    }
    @GetMapping("/admin/removeBook/{bookId}")
    public String removeBook(@PathVariable("bookId") Long id,
                             Model model){
        Book book = bookService.findById(id);
        if(book==null) return "/errors/bookNotFoundError";

        bookService.removeBook(bookService.findById(id));
        return showBooksAdmin(model);
    }

    @PostMapping("/admin/saveBookImage/{id}")
    public String saveMovieImage(@PathVariable("id") Long id,
                                 @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Book book = bookService.findById(id);
        if(book == null) return "errors/bookNotFoundError";

        book.setPicFilename(fileName);
        bookService.save(book);
        String uploadDir = "src/main/upload/images/book_pics/" + book.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/admin/book/"+ id;
    }
}