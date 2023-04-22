package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.data.PostDto;
import by.litvin.localsandbox.data.PostMediaDto;
import by.litvin.localsandbox.mapper.PostMapper;
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
    private final PostMapper postMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Long id) {
        PostDto postDto = postMapper.toPostDto(postService.getById(id));
        if (postDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDto);
    }

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody CreatePostRequest createPostRequest) {
        PostDto postDto = postMapper.toPostDto(postService.create(createPostRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/image")
    public ResponseEntity<PostMediaDto> uploadImage(@RequestBody MultipartFile file) {
        String savedMediaUrl = postService.uploadImage(file);
        return new ResponseEntity<>(new PostMediaDto(savedMediaUrl), HttpStatus.CREATED);
    }

    @DeleteMapping("/image")
    public ResponseEntity<Void> deleteImage(@RequestBody String imagePath) {
        postService.deleteImage(imagePath);
        return ResponseEntity.noContent().build();
    }
}
