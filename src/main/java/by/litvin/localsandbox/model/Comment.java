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

/**
 * Post comment entity
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment implements Serializable {

    public Comment(String text, AppUser appUser, Post post) {
        this.text = text;
        this.appUser = appUser;
        this.post = post;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
