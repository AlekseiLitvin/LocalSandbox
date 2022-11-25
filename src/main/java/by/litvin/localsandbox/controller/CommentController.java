package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.data.CreateCommentRequest;
import by.litvin.localsandbox.data.CreateCommentResult;
import by.litvin.localsandbox.model.Comment;
import by.litvin.localsandbox.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findById(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<CreateCommentResult> create(@RequestBody CreateCommentRequest createCommentRequest) {
        CreateCommentResult createCommentResult = commentService.create(createCommentRequest);
        if (createCommentResult.getStatus() == CreateCommentResult.Status.USER_NOT_EXISTS ||
                createCommentResult.getStatus() == CreateCommentResult.Status.POST_NOT_EXISTS) {
            return ResponseEntity.badRequest().body(createCommentResult);
        } else {
            return ResponseEntity.ok(createCommentResult);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
