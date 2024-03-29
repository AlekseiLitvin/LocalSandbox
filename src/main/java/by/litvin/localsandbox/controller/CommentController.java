package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.data.CommentDto;
import by.litvin.localsandbox.data.CreateCommentRequest;
import by.litvin.localsandbox.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CommentDto> findById(@PathVariable Long id) {
        CommentDto commentDto = commentService.getById(id);
        if (commentDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentDto);
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody CreateCommentRequest createCommentRequest) {
        CommentDto createCommentResult = commentService.create(createCommentRequest);
        return new ResponseEntity<>(createCommentResult, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
