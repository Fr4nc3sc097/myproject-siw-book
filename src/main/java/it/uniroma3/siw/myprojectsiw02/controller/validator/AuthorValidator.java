package it.uniroma3.siw.myprojectsiw02.controller.validator;


import it.uniroma3.siw.myprojectsiw02.model.Author;
import it.uniroma3.siw.myprojectsiw02.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class AuthorValidator implements Validator {
    @Autowired
    private AuthorService authorService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;
        if(authorService.alreadyExists(author))
            errors.reject("author.duplicate");
    }
}
