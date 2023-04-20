package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CommentResponse;
import by.litvin.localsandbox.data.CreateCommentRequest;
import by.litvin.localsandbox.model.Comment;

public interface CommentService {

    CommentResponse create(CreateCommentRequest comment);

    Comment getById(Long id);

    void deleteById(Long id);
}
