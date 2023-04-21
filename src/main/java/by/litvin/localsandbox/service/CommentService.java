package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CommentDto;
import by.litvin.localsandbox.data.CreateCommentRequest;

public interface CommentService {

    by.litvin.localsandbox.data.CommentDto create(CreateCommentRequest comment);

    CommentDto getById(Long id);

    void deleteById(Long id);
}
