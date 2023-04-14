package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.data.UploadImageResult;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.service.PostService;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        Post post = postService.getById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<CreatePostResult> create(@RequestBody CreatePostRequest createPostRequest) {
        CreatePostResult createPostResult = postService.create(createPostRequest);
        if (createPostResult.getStatus() == CreatePostResult.Status.USER_NOT_EXISTS) {
            return ResponseEntity.badRequest().body(createPostResult);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createPostResult);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/image")
    public ResponseEntity<UploadImageResult> uploadImage(@RequestBody MultipartFile file) {
        UploadImageResult uploadImageResult = postService.uploadImage(file);
        return new ResponseEntity<>(uploadImageResult, HttpStatus.CREATED);
    }

    @DeleteMapping("/image")
    public ResponseEntity<Void> deleteImage(@RequestBody String imagePath) {
        postService.deleteImage(imagePath);
        return ResponseEntity.noContent().build();
    }
}
