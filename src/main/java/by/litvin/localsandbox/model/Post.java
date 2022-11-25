package by.litvin.localsandbox.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User post entity
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {

    public Post(String message, String mediaUrl, AppUser appUser) {
        this.message = message;
        this.mediaUrl = mediaUrl;
        this.appUser = appUser;
    }

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime creationTime = LocalDateTime.now();

    private String message;

    private String mediaUrl;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();
}
