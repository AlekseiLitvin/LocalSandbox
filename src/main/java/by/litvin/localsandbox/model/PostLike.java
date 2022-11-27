package by.litvin.localsandbox.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Post like entity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostLike implements Serializable {

    public PostLike(AppUser appUser, Post post) {
        this.appUser = appUser;
        this.post = post;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime creationTime = LocalDateTime.now();
}
