package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.data.CreatePostData;
import by.litvin.localsandbox.data.CreatePostResponse;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.mapper.PostMapper;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        Post post = postService.getById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Object> create(@ModelAttribute CreatePostData createPostData) {
        CreatePostResult result = postService.create(createPostData);
        if (result.getStatus() == CreatePostResult.Status.USER_NOT_EXISTS) {
            return ResponseEntity.badRequest().body("User not found");
        } else {
            CreatePostResponse response = postMapper.toCreatePostResponse(result.getPost());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
