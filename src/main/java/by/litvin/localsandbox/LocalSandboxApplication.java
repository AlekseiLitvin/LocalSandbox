package by.litvin.localsandbox;

import by.litvin.localsandbox.data.AppUser;
import by.litvin.localsandbox.repository.CommentRepository;
import by.litvin.localsandbox.repository.PostLikeRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LocalSandboxApplication {

    private static final Logger log = LoggerFactory.getLogger(LocalSandboxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LocalSandboxApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(
            AppUserService appUserService,
            PostRepository postRepository,
            CommentRepository commentRepository,
            PostLikeRepository postLikeRepository
    ) {
        return args -> {
            //            appUserRepository.deleteAll();
            //            postRepository.deleteAll();
            //            commentRepository.deleteAll();
            //            likeRepository.deleteAll();
            //
            //            AppUser appUser1 = appUserService.create(new AppUser("One", "One1", "111", "222"));
            //            AppUser appUser2 = appUserService.create(new AppUser("Two", "Two", "333", "444"));
            //
            //            Post post1 = postRepository.save(new Post("Message one", null, appUser1));
            //
            //            commentRepository.save(new Comment("Looks good", appUser2, post1));
            //            likeRepository.save(new PostLike(appUser2, post1));

            List<AppUser> two = appUserService.findByLastName("Two");
        };

    }

}
