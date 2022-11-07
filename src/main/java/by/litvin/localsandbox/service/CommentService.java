package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.Comment;

public interface CommentService {

    Comment create(Comment comment);

    Comment getById(Long id);

    void deleteById(Long id);
}
