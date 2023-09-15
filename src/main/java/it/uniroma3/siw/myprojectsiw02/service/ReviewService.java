package it.uniroma3.siw.myprojectsiw02.service;


import it.uniroma3.siw.myprojectsiw02.model.Review;
import it.uniroma3.siw.myprojectsiw02.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public void save(Review review){
        reviewRepository.save(review);
    }

    @Transactional
    public void remove(Review review){
        reviewRepository.delete(review);
    }

    public Review findById(Long id){
        return reviewRepository.findById(id).orElse(null);
    }
}