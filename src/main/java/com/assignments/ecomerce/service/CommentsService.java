package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Comments;
import com.assignments.ecomerce.repository.CommentsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentsService {
    @Autowired
    CommentsRepository commentsRepository;

    public List<Comments> findByProductId(Integer id) {
        return commentsRepository.findByProductId(id);
    }

    public boolean saveComments(Comments comments) {
        try {
            commentsRepository.save(comments);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
