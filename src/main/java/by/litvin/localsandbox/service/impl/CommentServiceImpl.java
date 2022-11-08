package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.model.Comment;
import by.litvin.localsandbox.repository.CommentRepository;
import by.litvin.localsandbox.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
