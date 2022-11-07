package by.litvin.localsandbox;

import by.litvin.localsandbox.data.AppUser;
import by.litvin.localsandbox.data.Comment;
import by.litvin.localsandbox.data.Post;
import by.litvin.localsandbox.data.PostLike;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.CommentRepository;
import by.litvin.localsandbox.repository.LikeRepository;
import by.litvin.localsandbox.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LocalSandboxApplication {

    private static final Logger log = LoggerFactory.getLogger(LocalSandboxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LocalSandboxApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(
            AppUserRepository appUserRepository,
            PostRepository postRepository,
            CommentRepository commentRepository,
            LikeRepository likeRepository
    ) {
        return args -> {
            appUserRepository.deleteAll();
            postRepository.deleteAll();
            commentRepository.deleteAll();
            likeRepository.deleteAll();

            AppUser appUser1 = appUserRepository.save(new AppUser("One", "One1", "111", "222"));
            AppUser appUser2 = appUserRepository.save(new AppUser("Two", "Two", "333", "444"));

            Post post1 = postRepository.save(new Post("Message one", null, appUser1));

            commentRepository.save(new Comment("Looks good", appUser2, post1));
            likeRepository.save(new PostLike(appUser2, post1));
        };

    }

}
